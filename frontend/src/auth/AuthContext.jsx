import { createContext, useCallback, useContext, useEffect, useMemo, useState } from 'react';
import { setUnauthorizedHandler, tokenStorage } from '../api/client.js';
import { userApi } from '../api/userApi.js';

/**
 * Global authentication state.
 *
 *   const { user, roles, isAuthenticated, isAdmin, login, logout, refreshUser } = useAuth();
 *
 * The JWT is kept in localStorage, the profile is fetched from /api/users/me
 * on start-up and after login.
 */
const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => tokenStorage.get());
  const [user, setUser] = useState(null);
  const [roles, setRoles] = useState([]);
  const [loading, setLoading] = useState(Boolean(tokenStorage.get()));

  const logout = useCallback(() => {
    tokenStorage.clear();
    setToken(null);
    setUser(null);
    setRoles([]);
  }, []);

  const refreshUser = useCallback(async () => {
    const me = await userApi.me();
    setUser(me.user);
    setRoles(me.roles || []);
    return me;
  }, []);

  // Expired / invalid token anywhere in the app -> log out
  useEffect(() => setUnauthorizedHandler(logout), [logout]);

  // Restore the session on page reload
  useEffect(() => {
    if (!token) {
      setLoading(false);
      return;
    }
    let cancelled = false;
    setLoading(true);
    refreshUser()
      .catch(() => !cancelled && logout())
      .finally(() => !cancelled && setLoading(false));
    return () => {
      cancelled = true;
    };
  }, [token, refreshUser, logout]);

  const login = useCallback(async (email, password) => {
    const jwt = await userApi.login(email, password);
    tokenStorage.set(jwt);
    setToken(jwt); // triggers refreshUser via the effect above
  }, []);

  const value = useMemo(
    () => ({
      token,
      user,
      roles,
      loading,
      isAuthenticated: Boolean(token && user),
      isAdmin: roles.includes('ROLE_ADMIN'),
      hasRole: (role) => roles.includes(role),
      login,
      logout,
      refreshUser,
    }),
    [token, user, roles, loading, login, logout, refreshUser]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used inside <AuthProvider>');
  return ctx;
}
