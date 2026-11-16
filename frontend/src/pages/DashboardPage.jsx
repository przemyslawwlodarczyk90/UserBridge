import { Link } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext.jsx';

const features = [
  { title: '🔐 JWT authentication', text: 'Stateless login, token stored client-side and sent on every request.' },
  { title: '📧 E-mail activation', text: 'New accounts are activated with a one-time link sent by SMTP.' },
  { title: '🔁 Password reset', text: 'Time-limited, single-use reset links - no user enumeration.' },
  { title: '🛡️ Roles & ownership', text: 'ROLE_USER / ROLE_ADMIN, users can only edit or delete themselves.' },
];

/**
 * The home page of the logged-in area.
 * 👉 This is where your next application starts - replace the content below.
 */
export default function DashboardPage() {
  const { user, roles } = useAuth();

  return (
    <div className="stack">
      <div className="card">
        <h1>Hi, {user.firstName} 👋</h1>
        <p className="muted">
          You are logged in as <strong>{user.email}</strong>{' '}
          {roles.map((r) => <span key={r} className="badge">{r.replace('ROLE_', '')}</span>)}
        </p>
        <p>
          This dashboard is a placeholder for your next app. Authentication, activation, password reset
          and profile management already work - just build your features here.
        </p>
        <Link to="/profile" className="btn btn-primary">Manage profile</Link>
      </div>

      <div className="grid">
        {features.map((f) => (
          <div key={f.title} className="card feature">
            <h3>{f.title}</h3>
            <p>{f.text}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
