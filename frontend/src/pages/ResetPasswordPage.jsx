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
      setFieldErrors({ password: 'Password must be at least 8 characters' });
      return;
    }
    if (form.password !== form.confirm) {
      setFieldErrors({ confirm: 'Passwords do not match' });
      return;
    }
    setSubmitting(true);
    try {
      await userApi.resetPassword(token, form.password);
      navigate('/login', { replace: true, state: { message: 'Password changed. Log in with your new password.' } });
    } catch (err) {
      setError(err.message);
    } finally {
      setSubmitting(false);
    }
  };

  if (!token) {
    return (
      <AuthCard title="Invalid link" footer={<Link to="/forgot-password">Request a new link</Link>}>
        <Alert type="error">This reset link is missing a token.</Alert>
      </AuthCard>
    );
  }

  return (
    <AuthCard
      title="Set a new password"
      footer={<Link to="/forgot-password">Need a new link?</Link>}
    >
      <Alert type="error">{error}</Alert>
      <form onSubmit={onSubmit} noValidate>
        <FormField label="New password" name="password" type="password" autoComplete="new-password" required
                   hint="At least 8 characters"
                   value={form.password} onChange={onChange} error={fieldErrors.password} />
        <FormField label="Repeat new password" name="confirm" type="password" autoComplete="new-password" required
                   value={form.confirm} onChange={onChange} error={fieldErrors.confirm} />
        <button className="btn btn-primary btn-block" disabled={submitting}>
          {submitting ? 'Saving…' : 'Change password'}
        </button>
      </form>
    </AuthCard>
  );
}
