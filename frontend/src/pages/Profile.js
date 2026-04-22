import { useState } from 'react';
import useAuth from '../hooks/useAuth';
import { updateProfile } from '../services/userService';
import { changePassword } from '../services/authService';
import '../styles/Profile.css';

// Regex para validar el formato de email
const EMAIL_REGEX = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
// Regex para validar contrasena: minimo 6 caracteres con letras y numeros
const PASSWORD_REGEX = /^(?=.*[a-zA-Z])(?=.*\d).{6,}$/;

export default function Profile() {
  const { user, refreshUser } = useAuth();

  // --- Estado del formulario de datos personales ---
  const [profileValues, setProfileValues] = useState({
    nombre: user.nombre,
    apellidos: user.apellidos,
    email: user.email,
  });
  const [profileErrors, setProfileErrors] = useState({});
  const [profileSaving, setProfileSaving] = useState(false);
  const [profileSuccess, setProfileSuccess] = useState(false);

  // --- Estado del sub-formulario de contrasena ---
  const [pwdValues, setPwdValues] = useState({
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
  });
  const [pwdErrors, setPwdErrors] = useState({});
  const [pwdSaving, setPwdSaving] = useState(false);
  const [pwdSuccess, setPwdSuccess] = useState(false);
  const [pwdServerError, setPwdServerError] = useState(null);

  // --- Handlers del formulario de datos personales ---

  function handleProfileChange(e) {
    const { name, value } = e.target;
    setProfileValues(prev => ({ ...prev, [name]: value }));
    setProfileErrors(prev => ({ ...prev, [name]: undefined }));
    setProfileSuccess(false);
  }

  function validateProfile(values) {
    const errors = {};
    if (!values.nombre.trim()) errors.nombre = 'El nombre es obligatorio.';
    if (!values.apellidos.trim()) errors.apellidos = 'Los apellidos son obligatorios.';
    if (!EMAIL_REGEX.test(values.email)) errors.email = 'El email no tiene un formato valido.';
    return errors;
  }

  async function handleProfileSubmit(e) {
    e.preventDefault();
    const errors = validateProfile(profileValues);
    if (Object.keys(errors).length > 0) { setProfileErrors(errors); return; }

    setProfileSaving(true);
    try {
      await updateProfile(user.id, profileValues);
      await refreshUser();
      setProfileSuccess(true);
    } catch (err) {
      setProfileErrors({ general: err.message });
    } finally {
      setProfileSaving(false);
    }
  }

  // --- Handlers del sub-formulario de contrasena ---

  function handlePwdChange(e) {
    const { name, value } = e.target;
    setPwdValues(prev => ({ ...prev, [name]: value }));
    setPwdErrors(prev => ({ ...prev, [name]: undefined }));
    setPwdSuccess(false);
    setPwdServerError(null);
  }

  function validatePwd(values) {
    const errors = {};
    if (!values.currentPassword) errors.currentPassword = 'Introduce tu contrasena actual.';
    if (!PASSWORD_REGEX.test(values.newPassword)) {
      errors.newPassword = 'Minimo 6 caracteres con letras y numeros.';
    }
    if (values.newPassword !== values.confirmPassword) {
      errors.confirmPassword = 'Las contrasenas no coinciden.';
    }
    return errors;
  }

  async function handlePwdSubmit(e) {
    e.preventDefault();
    setPwdServerError(null);
    const errors = validatePwd(pwdValues);
    if (Object.keys(errors).length > 0) { setPwdErrors(errors); return; }

    setPwdSaving(true);
    try {
      await changePassword(user.id, pwdValues.currentPassword, pwdValues.newPassword);
      setPwdSuccess(true);
      setPwdValues({ currentPassword: '', newPassword: '', confirmPassword: '' });
    } catch (err) {
      setPwdServerError(err.message);
    } finally {
      setPwdSaving(false);
    }
  }

  return (
    <section className='container py-4'>
      <h1 className='mb-4'>Mi perfil</h1>

      <div className='row g-4'>

        {/* Columna izquierda: datos personales */}
        <div className='col-12 col-lg-6'>
          <div className='card shadow-sm h-100'>
            <div className='card-header fw-semibold'>Datos personales</div>
            <div className='card-body'>

              {profileErrors.general && (
                <div className='alert alert-danger'>{profileErrors.general}</div>
              )}
              {profileSuccess && (
                <div className='alert alert-success'>Perfil actualizado correctamente.</div>
              )}

              <form onSubmit={handleProfileSubmit} noValidate>

                <div className='mb-3'>
                  <label htmlFor='nombre' className='form-label'>Nombre <span className='text-danger'>*</span></label>
                  <input
                    type='text'
                    id='nombre'
                    name='nombre'
                    className={`form-control ${profileErrors.nombre ? 'is-invalid' : ''}`}
                    value={profileValues.nombre}
                    onChange={handleProfileChange}
                    required
                  />
                  {profileErrors.nombre && (
                    <span className='invalid-feedback'>{profileErrors.nombre}</span>
                  )}
                </div>

                <div className='mb-3'>
                  <label htmlFor='apellidos' className='form-label'>Apellidos <span className='text-danger'>*</span></label>
                  <input
                    type='text'
                    id='apellidos'
                    name='apellidos'
                    className={`form-control ${profileErrors.apellidos ? 'is-invalid' : ''}`}
                    value={profileValues.apellidos}
                    onChange={handleProfileChange}
                    required
                  />
                  {profileErrors.apellidos && (
                    <span className='invalid-feedback'>{profileErrors.apellidos}</span>
                  )}
                </div>

                <div className='mb-3'>
                  <label htmlFor='email' className='form-label'>Email <span className='text-danger'>*</span></label>
                  <input
                    type='email'
                    id='email'
                    name='email'
                    className={`form-control ${profileErrors.email ? 'is-invalid' : ''}`}
                    value={profileValues.email}
                    onChange={handleProfileChange}
                    required
                  />
                  {profileErrors.email && (
                    <span className='invalid-feedback'>{profileErrors.email}</span>
                  )}
                </div>

                <div className='mb-3'>
                  <label className='form-label'>Rol</label>
                  <input
                    type='text'
                    className='form-control'
                    value={user.rol}
                    readOnly
                    disabled
                  />
                </div>

                <div className='mb-3'>
                  <label className='form-label'>Miembro desde</label>
                  <input
                    type='text'
                    className='form-control'
                    value={new Date(user.fechaRegistro).toLocaleDateString('es-ES', {
                      day: '2-digit', month: 'long', year: 'numeric',
                    })}
                    readOnly
                    disabled
                  />
                </div>

                <button type='submit' className='btn btn-primary' disabled={profileSaving}>
                  {profileSaving ? 'Guardando...' : 'Guardar cambios'}
                </button>

              </form>
            </div>
          </div>
        </div>

        {/* Columna derecha: cambio de contrasena */}
        <div className='col-12 col-lg-6'>
          <div className='card shadow-sm h-100'>
            <div className='card-header fw-semibold'>Cambiar contraseña</div>
            <div className='card-body'>

              {pwdServerError && (
                <div className='alert alert-danger'>{pwdServerError}</div>
              )}
              {pwdSuccess && (
                <div className='alert alert-success'>Contrasena actualizada correctamente.</div>
              )}

              <form onSubmit={handlePwdSubmit} noValidate>

                <div className='mb-3'>
                  <label htmlFor='currentPassword' className='form-label'>
                    Contraseña actual <span className='text-danger'>*</span>
                  </label>
                  <input
                    type='password'
                    id='currentPassword'
                    name='currentPassword'
                    className={`form-control ${pwdErrors.currentPassword ? 'is-invalid' : ''}`}
                    value={pwdValues.currentPassword}
                    onChange={handlePwdChange}
                    required
                  />
                  {pwdErrors.currentPassword && (
                    <span className='invalid-feedback'>{pwdErrors.currentPassword}</span>
                  )}
                </div>

                <div className='mb-3'>
                  <label htmlFor='newPassword' className='form-label'>
                    Nueva contraseña <span className='text-danger'>*</span>
                  </label>
                  <input
                    type='password'
                    id='newPassword'
                    name='newPassword'
                    className={`form-control ${pwdErrors.newPassword ? 'is-invalid' : ''}`}
                    value={pwdValues.newPassword}
                    onChange={handlePwdChange}
                    required
                  />
                  {pwdErrors.newPassword && (
                    <span className='invalid-feedback'>{pwdErrors.newPassword}</span>
                  )}
                  <small className='text-muted'>Minimo 6 caracteres con letras y numeros.</small>
                </div>

                <div className='mb-3'>
                  <label htmlFor='confirmPassword' className='form-label'>
                    Confirmar contraseña <span className='text-danger'>*</span>
                  </label>
                  <input
                    type='password'
                    id='confirmPassword'
                    name='confirmPassword'
                    className={`form-control ${pwdErrors.confirmPassword ? 'is-invalid' : ''}`}
                    value={pwdValues.confirmPassword}
                    onChange={handlePwdChange}
                    required
                  />
                  {pwdErrors.confirmPassword && (
                    <span className='invalid-feedback'>{pwdErrors.confirmPassword}</span>
                  )}
                </div>

                <button type='submit' className='btn btn-warning' disabled={pwdSaving}>
                  {pwdSaving ? 'Guardando...' : 'Cambiar contraseña'}
                </button>

              </form>
            </div>
          </div>
        </div>

      </div>
    </section>
  );
}
