import { useEffect, useState } from 'react';
import { userApi } from '../api/userApi.js';
import Alert from '../components/Alert.jsx';
import Spinner from '../components/Spinner.jsx';

/** Visible only for ROLE_ADMIN (guarded in App.jsx and on the backend). */
export default function AdminUsersPage() {
  const [users, setUsers] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    userApi.getAll().then(setUsers).catch((err) => setError(err.message));
  }, []);

  return (
    <div className="card">
      <h2>Wszyscy użytkownicy</h2>
      <Alert type="error">{error}</Alert>
      {!users && !error && <Spinner />}
      {users && (
        <div className="table-wrap">
          <table>
            <thead>
              <tr><th>ID</th><th>Imię i nazwisko</th><th>E-mail</th><th>Telefon</th><th>Miasto</th></tr>
            </thead>
            <tbody>
              {users.map((u) => (
                <tr key={u.id}>
                  <td>{u.id}</td>
                  <td>{u.firstName} {u.lastName}</td>
                  <td>{u.email}</td>
                  <td>{u.phoneNumber || '—'}</td>
                  <td>{u.city || '—'}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
