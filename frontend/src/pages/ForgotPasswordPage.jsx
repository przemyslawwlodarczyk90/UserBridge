import { useState } from 'react';
import { Link } from 'react-router-dom';
import { userApi } from '../api/userApi.js';
import AuthCard from '../components/AuthCard.jsx';
import FormField from '../components/FormField.jsx';
import Alert from '../components/Alert.jsx';

export default function ForgotPasswordPage() {
  const [email, setEmail] = useState('');
  const [error, setError] = useState('');
  const [sent, setSent] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  const onSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSubmitting(true);
    try {
      await userApi.forgotPassword(email);
      setSent(true);
    } catch (err) {
      setError(err.message);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <AuthCard
      title="Nie pamiętasz hasła?"
      subtitle="Podaj swój e-mail, a wyślemy Ci link do ustawienia nowego hasła."
      footer={<Link to="/login">Wróć do logowania</Link>}
    >
      {sent ? (
        <Alert type="success">
          Jeśli istnieje konto dla <strong>{email}</strong>, link do resetu hasła jest już w drodze. Sprawdź skrzynkę (także folder spam).
        </Alert>
      ) : (
        <>
          <Alert type="error">{error}</Alert>
          <form onSubmit={onSubmit} noValidate>
            <FormField label="E-mail" name="email" type="email" autoComplete="email" required
                       value={email} onChange={(e) => setEmail(e.target.value)} />
            <button className="btn btn-primary btn-block" disabled={submitting || !email}>
              {submitting ? 'Wysyłanie…' : 'Wyślij link'}
            </button>
          </form>
        </>
      )}
    </AuthCard>
  );
}
