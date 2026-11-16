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
      setFieldErrors({ password: 'Hasło musi mieć co najmniej 8 znaków' });
      return;
    }
    if (form.password !== form.confirmPassword) {
      setFieldErrors({ confirmPassword: 'Hasła nie są takie same' });
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
      <AuthCard title="Sprawdź skrzynkę 📬" footer={<Link to="/login">Wróć do logowania</Link>}>
        <Alert type="success">
          Wysłaliśmy link aktywacyjny na adres <strong>{form.email}</strong>. Kliknij go, aby aktywować konto.
        </Alert>
      </AuthCard>
    );
  }

  return (
    <AuthCard
      title="Załóż konto"
      subtitle="To zajmie mniej niż minutę"
      footer={<>Masz już konto? <Link to="/login">Zaloguj się</Link></>}
    >
      <Alert type="error">{error}</Alert>
      <form onSubmit={onSubmit} noValidate>
        <div className="row">
          <FormField label="Imię" name="firstName" required autoComplete="given-name"
                     value={form.firstName} onChange={onChange} error={fieldErrors.firstName} />
          <FormField label="Nazwisko" name="lastName" required autoComplete="family-name"
                     value={form.lastName} onChange={onChange} error={fieldErrors.lastName} />
        </div>
        <FormField label="E-mail" name="email" type="email" required autoComplete="email"
                   value={form.email} onChange={onChange} error={fieldErrors.email} />
        <div className="row">
          <FormField label="Hasło" name="password" type="password" required autoComplete="new-password"
                     hint="Co najmniej 8 znaków"
                     value={form.password} onChange={onChange} error={fieldErrors.password} />
          <FormField label="Powtórz hasło" name="confirmPassword" type="password" required autoComplete="new-password"
                     value={form.confirmPassword} onChange={onChange} error={fieldErrors.confirmPassword} />
        </div>
        <details>
          <summary className="muted" style={{ cursor: 'pointer', marginBottom: '1rem' }}>Dane dodatkowe (opcjonalnie)</summary>
          {optionalFields.map((f) => (
            <FormField key={f.name} {...f} value={form[f.name]} onChange={onChange} error={fieldErrors[f.name]} />
          ))}
        </details>
        <button className="btn btn-primary btn-block" disabled={submitting}>
          {submitting ? 'Zakładanie konta…' : 'Załóż konto'}
        </button>
      </form>
    </AuthCard>
  );
}
