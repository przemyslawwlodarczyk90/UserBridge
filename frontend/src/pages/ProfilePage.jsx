import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext.jsx';
import { userApi } from '../api/userApi.js';
import FormField from '../components/FormField.jsx';
import Alert from '../components/Alert.jsx';
import { emptyToNull, optionalFields } from './profileFields.js';

const toForm = (user) => ({
  firstName: user.firstName ?? '',
  lastName: user.lastName ?? '',
  email: user.email ?? '',
  phoneNumber: user.phoneNumber ?? '',
  street: user.street ?? '',
  postalCode: user.postalCode ?? '',
  city: user.city ?? '',
});

export default function ProfilePage() {
  const { user, refreshUser, logout } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState(() => toForm(user));
  const [fieldErrors, setFieldErrors] = useState({});
  const [message, setMessage] = useState({ type: '', text: '' });
  const [saving, setSaving] = useState(false);
  const [deleting, setDeleting] = useState(false);

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const onSave = async (e) => {
    e.preventDefault();
    setMessage({ type: '', text: '' });
    setFieldErrors({});
    setSaving(true);
    try {
      await userApi.update(user.id, emptyToNull({ id: user.id, ...form }));
      const me = await refreshUser();
      setForm(toForm(me.user));
      setMessage({ type: 'success', text: 'Profile saved.' });
    } catch (err) {
      if (err.fieldErrors) setFieldErrors(err.fieldErrors);
      setMessage({ type: 'error', text: err.message });
    } finally {
      setSaving(false);
    }
  };

  const onDelete = async () => {
    if (!window.confirm('Delete your account permanently? This cannot be undone.')) return;
    setDeleting(true);
    try {
      await userApi.remove(user.id);
      logout();
      navigate('/login', { replace: true, state: { message: 'Your account has been deleted.' } });
    } catch (err) {
      setMessage({ type: 'error', text: err.message });
      setDeleting(false);
    }
  };

  return (
    <div className="stack">
      <div className="card">
        <h2>Your profile</h2>
        <Alert type={message.type}>{message.text}</Alert>
        <form onSubmit={onSave} noValidate>
          <div className="row">
            <FormField label="First name" name="firstName" required
                       value={form.firstName} onChange={onChange} error={fieldErrors.firstName} />
            <FormField label="Last name" name="lastName" required
                       value={form.lastName} onChange={onChange} error={fieldErrors.lastName} />
          </div>
          <FormField label="Email" name="email" type="email" value={form.email} disabled
                     hint="Email cannot be changed" />
          <div className="row">
            {optionalFields.map((f) => (
              <FormField key={f.name} {...f} value={form[f.name]} onChange={onChange} error={fieldErrors[f.name]} />
            ))}
          </div>
          <button className="btn btn-primary" disabled={saving}>{saving ? 'Saving…' : 'Save changes'}</button>
        </form>
      </div>

      <div className="card danger-zone">
        <h2>Danger zone</h2>
        <p className="muted">Deleting your account removes all your data. This action is irreversible.</p>
        <button className="btn btn-danger" onClick={onDelete} disabled={deleting}>
          {deleting ? 'Deleting…' : 'Delete account'}
        </button>
      </div>
    </div>
  );
}
