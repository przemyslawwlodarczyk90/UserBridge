import { Link } from 'react-router-dom';

export default function NotFoundPage() {
  return (
    <div className="card center">
      <h1>404</h1>
      <p className="muted">Taka strona nie istnieje.</p>
      <Link to="/" className="btn btn-primary">Wróć na stronę główną</Link>
    </div>
  );
}
