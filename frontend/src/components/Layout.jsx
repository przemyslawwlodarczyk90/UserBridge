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
              <NavLink to="/" end>Dashboard</NavLink>
              <NavLink to="/profile">Profile</NavLink>
              {isAdmin && <NavLink to="/admin/users">Users</NavLink>}
              <span className="nav-user">{user?.firstName}</span>
              <button className="btn btn-ghost" onClick={handleLogout}>Log out</button>
            </>
          ) : (
            <>
              <NavLink to="/login">Log in</NavLink>
              <NavLink to="/register" className="btn btn-primary btn-sm">Sign up</NavLink>
            </>
          )}
        </nav>
      </header>
      <main className="container">
        <Outlet />
      </main>
      <footer className="footer muted">Built on UserBridge &middot; Spring Boot + React starter</footer>
    </div>
  );
}
