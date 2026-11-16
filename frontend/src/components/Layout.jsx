import { Link, NavLink, Outlet, useNavigate } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext.jsx';

const APP_NAME = import.meta.env.VITE_APP_NAME || 'UserBridge';

export default function Layout() {
  const { isAuthenticated, isAdmin, user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="app">
      <header className="navbar">
        <Link to="/" className="brand">
          <img src="/favicon.svg" alt="" width="28" height="28" />
          {APP_NAME}
        </Link>
        <nav>
          {isAuthenticated ? (
            <>
              <NavLink to="/" end>Pulpit</NavLink>
              <NavLink to="/profile">Profil</NavLink>
              {isAdmin && <NavLink to="/admin/users">Użytkownicy</NavLink>}
              <span className="nav-user">{user?.firstName}</span>
              <button className="btn btn-ghost" onClick={handleLogout}>Wyloguj</button>
            </>
          ) : (
            <>
              <NavLink to="/login">Zaloguj się</NavLink>
              <NavLink to="/register" className="btn btn-primary btn-sm">Załóż konto</NavLink>
            </>
          )}
        </nav>
      </header>
      <main className="container">
        <Outlet />
      </main>
      <footer className="footer muted">Zbudowane na UserBridge &middot; starter Spring Boot + React</footer>
    </div>
  );
}
