import { useState } from 'react';
import { Link, Navigate, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext.jsx';
import AuthCard from '../components/AuthCard.jsx';
import FormField from '../components/FormField.jsx';
import Alert from '../components/Alert.jsx';

export default function LoginPage() {
  const { login, isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [form, setForm] = useState({ email: '', password: '' });
  const [error, setError] = useState('');
  const [submitting, setSubmitting] = useState(false);

  const redirectTo = location.state?.from?.pathname || '/';
  if (isAuthenticated) return <Navigate to={redirectTo} replace />;

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const onSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSubmitting(true);
    try {
      await login(form.email, form.password);
      navigate(redirectTo, { replace: true });
    } catch (err) {
      setError(err.message);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <AuthCard
      title="Witaj ponownie"
      subtitle="Zaloguj się na swoje konto"
      footer={<>Nie masz konta? <Link to="/register">Zarejestruj się</Link></>}
    >
      <Alert type="success">{location.state?.message}</Alert>
      <Alert type="error">{error}</Alert>
      <form onSubmit={onSubmit} noValidate>
        <FormField label="E-mail" name="email" type="email" autoComplete="email" required
                   value={form.email} onChange={onChange} />
        <FormField label="Hasło" name="password" type="password" autoComplete="current-password" required
                   value={form.password} onChange={onChange} />
        <div className="form-links">
          <Link to="/forgot-password">Nie pamiętasz hasła?</Link>
        </div>
        <button className="btn btn-primary btn-block" disabled={submitting}>
          {submitting ? 'Logowanie…' : 'Zaloguj się'}
        </button>
      </form>
    </AuthCard>
  );
}
