import { useState } from 'react';
import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import { userApi } from '../api/userApi.js';
import AuthCard from '../components/AuthCard.jsx';
import FormField from '../components/FormField.jsx';
import Alert from '../components/Alert.jsx';

/** Landing page for the link from the password reset e-mail. */
export default function ResetPasswordPage() {
  const [params] = useSearchParams();
  const token = params.get('token');
  const navigate = useNavigate();
  const [form, setForm] = useState({ password: '', confirm: '' });
  const [fieldErrors, setFieldErrors] = useState({});
  const [error, setError] = useState('');
  const [submitting, setSubmitting] = useState(false);

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const onSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setFieldErrors({});
    if (form.password.length < 8) {
      setFieldErrors({ password: 'Hasło musi mieć co najmniej 8 znaków' });
      return;
    }
    if (form.password !== form.confirm) {
      setFieldErrors({ confirm: 'Hasła nie są takie same' });
      return;
    }
    setSubmitting(true);
    try {
      await userApi.resetPassword(token, form.password);
      navigate('/login', { replace: true, state: { message: 'Hasło zostało zmienione. Zaloguj się nowym hasłem.' } });
    } catch (err) {
      setError(err.message);
    } finally {
      setSubmitting(false);
    }
  };

  if (!token) {
    return (
      <AuthCard title="Nieprawidłowy link" footer={<Link to="/forgot-password">Poproś o nowy link</Link>}>
        <Alert type="error">Ten link jest niekompletny (brak tokenu).</Alert>
      </AuthCard>
    );
  }

  return (
    <AuthCard
      title="Ustaw nowe hasło"
      footer={<Link to="/forgot-password">Potrzebujesz nowego linku?</Link>}
    >
      <Alert type="error">{error}</Alert>
      <form onSubmit={onSubmit} noValidate>
        <FormField label="Nowe hasło" name="password" type="password" autoComplete="new-password" required
                   hint="Co najmniej 8 znaków"
                   value={form.password} onChange={onChange} error={fieldErrors.password} />
        <FormField label="Powtórz nowe hasło" name="confirm" type="password" autoComplete="new-password" required
                   value={form.confirm} onChange={onChange} error={fieldErrors.confirm} />
        <button className="btn btn-primary btn-block" disabled={submitting}>
          {submitting ? 'Zapisywanie…' : 'Zmień hasło'}
        </button>
      </form>
    </AuthCard>
  );
}
