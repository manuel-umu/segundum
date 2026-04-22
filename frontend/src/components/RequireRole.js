import { Navigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth';

export default function RequireRole({ role, children }) {
  const { user } = useAuth();

  if (user?.rol !== role) {
    return <Navigate to="/" replace />;
  }

  return children;
}
