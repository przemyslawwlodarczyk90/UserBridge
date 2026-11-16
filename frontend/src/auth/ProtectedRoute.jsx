import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { useAuth } from './AuthContext.jsx';
import Spinner from '../components/Spinner.jsx';

/**
 * Guards nested routes.
 *   <Route element={<ProtectedRoute />}>               -> any logged-in user
 *   <Route element={<ProtectedRoute role="ROLE_ADMIN" />}> -> only admins
 */
export default function ProtectedRoute({ role }) {
  const { isAuthenticated, loading, hasRole } = useAuth();
  const location = useLocation();

  if (loading) return <Spinner />;
  if (!isAuthenticated) return <Navigate to="/login" replace state={{ from: location }} />;
  if (role && !hasRole(role)) return <Navigate to="/" replace />;
  return <Outlet />;
}
