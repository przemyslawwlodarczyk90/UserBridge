import { useEffect, useRef, useState } from 'react';
import { Link, useSearchParams } from 'react-router-dom';
import { userApi } from '../api/userApi.js';
import AuthCard from '../components/AuthCard.jsx';
import Alert from '../components/Alert.jsx';
import Spinner from '../components/Spinner.jsx';

/** Landing page for the activation link from the registration e-mail. */
export default function ConfirmPage() {
  const [params] = useSearchParams();
  const token = params.get('token');
  const [state, setState] = useState({ status: 'loading', message: '' });
  const sent = useRef(false); // token is single-use - avoid double call in StrictMode

  useEffect(() => {
    if (sent.current) return;
    sent.current = true;
    if (!token) {
      setState({ status: 'error', message: 'Activation link is missing a token.' });
      return;
    }
    userApi
      .confirm(token)
      .then(() => setState({ status: 'success', message: 'Your account is active. You can log in now.' }))
      .catch((err) => setState({ status: 'error', message: err.message }));
  }, [token]);

  return (
    <AuthCard title="Account activation" footer={<Link to="/login">Go to log in</Link>}>
      {state.status === 'loading' ? <Spinner /> : <Alert type={state.status}>{state.message}</Alert>}
    </AuthCard>
  );
}
