import { useState } from 'react';
import { Link } from 'react-router-dom';
import { userApi } from '../api/userApi.js';
import AuthCard from '../components/AuthCard.jsx';
import FormField from '../components/FormField.jsx';
import Alert from '../components/Alert.jsx';
import { emptyToNull, optionalFields } from './profileFields.js';

const initial = {
  firstName: '', lastName: '', email: '', password: '', confirmPassword: '',
  phoneNumber: '', street: '', postalCode: '', city: '',
};

export default function RegisterPage() {
  const [form, setForm] = useState(initial);
  const [fieldErrors, setFieldErrors] = useState({});
  const [error, setError] = useState('');
  const [done, setDone] = useState(false);
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
    if (form.password !== form.confirmPassword) {
      setFieldErrors({ confirmPassword: 'Passwords do not match' });
      return;
    }
    setSubmitting(true);
    try {
      const { confirmPassword, ...payload } = form;
      await userApi.register(emptyToNull(payload));
      setDone(true);
    } catch (err) {
      // backend returns e.g. { "userDto.postalCode": "Postal code is invalid" }
      if (err.fieldErrors) {
        setFieldErrors(Object.fromEntries(
          Object.entries(err.fieldErrors).map(([k, v]) => [k.replace('userDto.', ''), v])
        ));
      }
      setError(err.message);
    } finally {
      setSubmitting(false);
    }
  };

  if (done) {
    return (
      <AuthCard title="Check your inbox 📬" footer={<Link to="/login">Back to log in</Link>}>
        <Alert type="success">
          We sent an activation link to <strong>{form.email}</strong>. Click it to activate your account.
        </Alert>
      </AuthCard>
    );
  }

  return (
    <AuthCard
      title="Create an account"
      subtitle="It takes less than a minute"
      footer={<>Already registered? <Link to="/login">Log in</Link></>}
    >
      <Alert type="error">{error}</Alert>
      <form onSubmit={onSubmit} noValidate>
        <div className="row">
          <FormField label="First name" name="firstName" required autoComplete="given-name"
                     value={form.firstName} onChange={onChange} error={fieldErrors.firstName} />
          <FormField label="Last name" name="lastName" required autoComplete="family-name"
                     value={form.lastName} onChange={onChange} error={fieldErrors.lastName} />
        </div>
        <FormField label="Email" name="email" type="email" required autoComplete="email"
                   value={form.email} onChange={onChange} error={fieldErrors.email} />
        <div className="row">
          <FormField label="Password" name="password" type="password" required autoComplete="new-password"
                     hint="At least 8 characters"
                     value={form.password} onChange={onChange} error={fieldErrors.password} />
          <FormField label="Repeat password" name="confirmPassword" type="password" required autoComplete="new-password"
                     value={form.confirmPassword} onChange={onChange} error={fieldErrors.confirmPassword} />
        </div>
        <details>
          <summary className="muted" style={{ cursor: 'pointer', marginBottom: '1rem' }}>Optional details</summary>
          {optionalFields.map((f) => (
            <FormField key={f.name} {...f} value={form[f.name]} onChange={onChange} error={fieldErrors[f.name]} />
          ))}
        </details>
        <button className="btn btn-primary btn-block" disabled={submitting}>
          {submitting ? 'Creating account…' : 'Sign up'}
        </button>
      </form>
    </AuthCard>
  );
}
