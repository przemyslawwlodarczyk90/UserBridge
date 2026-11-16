import { Link } from 'react-router-dom';

export default function NotFoundPage() {
  return (
    <div className="card center">
      <h1>404</h1>
      <p className="muted">This page does not exist.</p>
      <Link to="/" className="btn btn-primary">Go home</Link>
    </div>
  );
}
