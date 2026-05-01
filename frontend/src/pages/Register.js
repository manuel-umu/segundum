import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth';
import '../styles/Login.css';

const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d).{6,}$/;

// Función auxiliar para control de errores
function validate({ nombre, apellidos, email, password, confirmar }) {
  const errors = {};
  if (!nombre.trim()) errors.nombre = 'El nombre es obligatorio.';
  if (!apellidos.trim()) errors.apellidos = 'Los apellidos son obligatorios.';
  if (!emailRegex.test(email)) errors.email = 'Introduce un email válido.';
  if (!passwordRegex.test(password))
    errors.password = 'Mínimo 6 caracteres con letras y números.';
  if (password !== confirmar) errors.confirmar = 'Las contraseñas no coinciden.';
  return errors;
}

export default function Register() {
  const { register } = useAuth();
  const navigate = useNavigate();

  const [form, setForm] = useState({
    nombre: '', apellidos: '', email: '', password: '', confirmar: '',
  });
  const [fieldErrors, setFieldErrors] = useState({});
  const [apiError, setApiError] = useState('');
  const [loading, setLoading] = useState(false);

  function handleChange(e) {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
    setFieldErrors(prev => ({ ...prev, [name]: '' }));
  }

  // Función de registro
  async function handleSubmit(e) {
    e.preventDefault();
    setApiError('');

    const errors = validate(form);
    if (Object.keys(errors).length > 0) {
      setFieldErrors(errors);
      return;
    }

    setLoading(true);
    try {
      await register({
        nombre: form.nombre,
        apellidos: form.apellidos,
        email: form.email,
        password: form.password,
      });
      navigate('/');
    } catch (err) {
      setApiError(err.message ?? 'Error al crear la cuenta.');
    } finally {
      setLoading(false);
    }
  }

  // Función auxiliar para los 5 campos del formulario de registro
  function field(name, label, type = 'text', extra = {}) {
    return (
      <div className="mb-3">
        <label htmlFor={name} className="form-label">{label}</label>
        <input
          type={type}
          id={name}
          name={name}
          className={`form-control ${fieldErrors[name] ? 'is-invalid' : ''}`}
          value={form[name]}
          onChange={handleChange}
          {...extra}
        />
        {fieldErrors[name] && (
          <span className="invalid-feedback">{fieldErrors[name]}</span>
        )}
      </div>
    );
  }

  return (
    <section className="container py-5">
      <div className="row justify-content-center">
        <div className="col-12 col-md-8 col-lg-5">
          <div className="card login-card">
            <div className="card-header text-center py-3">
              <h1 className="h4 mb-0">Crear cuenta</h1>
            </div>
            <div className="card-body p-4">
              {apiError && (
                <div className="alert alert-danger" role="alert">{apiError}</div>
              )}

              {/* Comprobación de errores y tratamiento de datos al registrarse*/}
              <form onSubmit={handleSubmit} noValidate>
                {field('nombre', 'Nombre', 'text', { autoFocus: true, required: true })}
                {field('apellidos', 'Apellidos', 'text', { required: true })}
                {field('email', 'Correo electrónico', 'email', { required: true })}
                {field('password', 'Contraseña', 'password', { required: true })}
                {field('confirmar', 'Confirmar contraseña', 'password', { required: true })}

                <button
                  type="submit"
                  className="btn btn-dark w-100 mt-1"
                  disabled={loading}
                >
                  {loading ? (
                    <>
                      <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true" />
                      Creando cuenta...
                    </>
                  ) : 'Registrarse'}
                </button>
              </form>
            </div>

            {/* Footer para iniciar sesión */}
            <div className="card-footer text-center py-3">
              <small>
                ¿Ya tienes cuenta?{' '}
                <Link to="/login">Inicia sesión</Link>
              </small>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
