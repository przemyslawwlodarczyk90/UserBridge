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
      title="Forgot your password?"
      subtitle="Enter your email and we will send you a link to set a new one."
      footer={<Link to="/login">Back to log in</Link>}
    >
      {sent ? (
        <Alert type="success">
          If an account exists for <strong>{email}</strong>, a reset link is on its way. Check your inbox (and spam).
        </Alert>
      ) : (
        <>
          <Alert type="error">{error}</Alert>
          <form onSubmit={onSubmit} noValidate>
            <FormField label="Email" name="email" type="email" autoComplete="email" required
                       value={email} onChange={(e) => setEmail(e.target.value)} />
            <button className="btn btn-primary btn-block" disabled={submitting || !email}>
              {submitting ? 'Sending…' : 'Send reset link'}
            </button>
          </form>
        </>
      )}
    </AuthCard>
  );
}
