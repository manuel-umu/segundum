import { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import useAuth from '../hooks/useAuth';
import '../styles/Login.css';

const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

// Función auxiliar para control de errores
function validate(email, password) {
  const errors = {};
  if (!emailRegex.test(email)) errors.email = 'Introduce un email válido.';
  if (password.length < 6) errors.password = 'La contraseña debe tener al menos 6 caracteres.';
  return errors;
}

export default function Login() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const destino = location.state?.from?.pathname ?? '/';

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [fieldErrors, setFieldErrors] = useState({});
  const [apiError, setApiError] = useState('');
  const [loading, setLoading] = useState(false);

  // Comprobación de errores antes de enviar
  async function handleSubmit(e) {
    e.preventDefault();
    setApiError('');

    const errors = validate(email, password);
    if (Object.keys(errors).length > 0) {
      setFieldErrors(errors);
      return;
    }
    setFieldErrors({});

    setLoading(true);
    try {
      await login(email, password);
      navigate(destino, { replace: true });
    } catch (err) {
      setApiError(err.message ?? 'Error al iniciar sesión.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <section className="container py-5">
      <div className="row justify-content-center">
        <div className="col-12 col-md-6 col-lg-4">
          <div className="card login-card">
            <div className="card-header text-center py-3">
              <h1 className="h4 mb-0">Iniciar sesión</h1>
            </div>
            <div className="card-body p-4">
              {apiError && (
                <div className="alert alert-danger" role="alert">
                  {apiError}
                </div>
              )}

              {/* Formulario de login */}
              <form onSubmit={handleSubmit} noValidate>
                
                {/* Email */}
                <div className="mb-3">
                  <label htmlFor="email" className="form-label">
                    Correo electrónico
                  </label>
                  <input
                    type="email"
                    id="email"
                    className={`form-control ${fieldErrors.email ? 'is-invalid' : ''}`}
                    value={email}
                    onChange={e => {
                      setEmail(e.target.value);
                      setFieldErrors(prev => ({ ...prev, email: '' }));
                    }}
                    autoFocus
                    required
                  />
                  {fieldErrors.email && (
                    <span className="invalid-feedback">{fieldErrors.email}</span>
                  )}
                </div>

                {/* Contraseña */}
                <div className="mb-4">
                  <label htmlFor="password" className="form-label">
                    Contraseña
                  </label>
                  <input
                    type="password"
                    id="password"
                    className={`form-control ${fieldErrors.password ? 'is-invalid' : ''}`}
                    value={password}
                    onChange={e => {
                      setPassword(e.target.value);
                      setFieldErrors(prev => ({ ...prev, password: '' }));
                    }}
                    required
                  />
                  {fieldErrors.password && (
                    <span className="invalid-feedback">{fieldErrors.password}</span>
                  )}
                </div>
                
                {/* Botón */}
                <button
                  type="submit"
                  className="btn btn-dark w-100"
                  disabled={loading}
                >
                  {loading ? (
                    <>
                      <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true" />
                      Entrando...
                    </>
                  ) : 'Entrar'}
                </button>
              </form>
            </div>

            {/* Footer del container */}
            <div className="card-footer text-center py-3">
              <small>
                ¿No tienes cuenta?{' '}
                <Link to="/register">Regístrate</Link>
              </small>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
