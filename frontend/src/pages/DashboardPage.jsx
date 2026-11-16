import { Link } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext.jsx';

const features = [
  { title: '🔐 Logowanie JWT', text: 'Bezstanowe sesje: token przechowywany w przeglądarce i wysyłany z każdym żądaniem.' },
  { title: '📧 Aktywacja mailem', text: 'Nowe konta aktywowane jednorazowym linkiem wysyłanym przez SMTP.' },
  { title: '🔁 Reset hasła', text: 'Jednorazowe linki z ograniczonym czasem ważności, bez ujawniania, czy konto istnieje.' },
  { title: '🛡️ Role i uprawnienia', text: 'ROLE_USER / ROLE_ADMIN, użytkownik może edytować i usuwać tylko własne konto.' },
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
        <h1>Cześć, {user.firstName} 👋</h1>
        <p className="muted">
          Jesteś zalogowany jako <strong>{user.email}</strong>{' '}
          {roles.map((r) => <span key={r} className="badge">{r.replace('ROLE_', '')}</span>)}
        </p>
        <p>
          Ten pulpit to miejsce na Twoją nową aplikację. Logowanie, aktywacja konta, reset hasła
          i zarządzanie profilem już działają, więc możesz od razu budować tu własne funkcje.
        </p>
        <Link to="/profile" className="btn btn-primary">Zarządzaj profilem</Link>
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
