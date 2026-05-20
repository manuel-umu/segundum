import { useState } from "react";

// Regex para validar email
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
// Regex para validar contrasena: minimo 6 caracteres con letras y numeros
const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d).{6,}$/;

export default function Profile() {
  // Datos personales (TODO: cargar del contexto cuando este listo)
  const [nombre, setNombre] = useState("Manuel");
  const [apellidos, setApellidos] = useState("Chica");
  const [email, setEmail] = useState("manuel@ejemplo.com");

  // Errores del formulario de datos personales
  const [errorNombre, setErrorNombre] = useState("");
  const [errorApellidos, setErrorApellidos] = useState("");
  const [errorEmail, setErrorEmail] = useState("");
  const [perfilGuardado, setPerfilGuardado] = useState(false);

  // Campos de contrasena
  const [passwordActual, setPasswordActual] = useState("");
  const [passwordNueva, setPasswordNueva] = useState("");
  const [passwordConfirmar, setPasswordConfirmar] = useState("");

  // Errores del formulario de contrasena
  const [errorPasswordActual, setErrorPasswordActual] = useState("");
  const [errorPasswordNueva, setErrorPasswordNueva] = useState("");
  const [errorPasswordConfirmar, setErrorPasswordConfirmar] = useState("");
  const [passwordGuardada, setPasswordGuardada] = useState(false);

  // Guarda los datos personales
  function guardarPerfil(e) {
    e.preventDefault();

    // Limpiar errores anteriores
    setErrorNombre("");
    setErrorApellidos("");
    setErrorEmail("");
    setPerfilGuardado(false);

    // Validacion
    var hayError = false;

    if (nombre.trim() === "") {
      setErrorNombre("El nombre es obligatorio.");
      hayError = true;
    }
    if (apellidos.trim() === "") {
      setErrorApellidos("Los apellidos son obligatorios.");
      hayError = true;
    }
    if (!emailRegex.test(email)) {
      setErrorEmail("El email no tiene un formato valido.");
      hayError = true;
    }

    if (hayError) return;

    // TODO: conectar con el backend
    console.log("Guardando perfil:", nombre, apellidos, email);
    setPerfilGuardado(true);
  }

  // Cambiar la contraseña
  function cambiarPassword(e) {
    e.preventDefault();

    // Limpiar errores anteriores
    setErrorPasswordActual("");
    setErrorPasswordNueva("");
    setErrorPasswordConfirmar("");
    setPasswordGuardada(false);

    // Validacion
    var hayError = false;

    if (passwordActual === "") {
      setErrorPasswordActual("Introduce tu contraseña actual.");
      hayError = true;
    }
    if (!passwordRegex.test(passwordNueva)) {
      setErrorPasswordNueva("Minimo 6 caracteres con letras y números.");
      hayError = true;
    }
    if (passwordNueva !== passwordConfirmar) {
      setErrorPasswordConfirmar("Las contraseñas no coinciden.");
      hayError = true;
    }

    if (hayError) return;

    // TODO: conectar con el backend
    console.log("Cambiando contraseña");
    setPasswordGuardada(true);
    setPasswordActual("");
    setPasswordNueva("");
    setPasswordConfirmar("");
  }

  return (
    <div className="container">
      <h1 className="mb-4">Mi perfil</h1>

      <div className="row">

        {/* Columna izquierda: datos personales */}
        <div className="col-12 col-lg-6">
          <div className="card">
            <div className="card-header">Datos personales</div>
            <div className="card-body">
              {perfilGuardado && (
                <div className="alert alert-success">
                  Perfil actualizado correctamente.
                </div>
              )}

              <form onSubmit={guardarPerfil}>
                <div className="mb-3">
                  <label className="form-label">
                    Nombre
                  </label>
                  <input
                    type="text"
                    id="nombre"
                    className="form-control"
                    value={nombre}
                    onChange={function (e) {
                      setNombre(e.target.value);
                    }}
                  />
                  {errorNombre && <p className="text-danger">{errorNombre}</p>}
                </div>

                <div className="mb-3">
                  <label className="form-label">
                    Apellidos
                  </label>
                  <input
                    type="text"
                    id="apellidos"
                    className="form-control"
                    value={apellidos}
                    onChange={function (e) {
                      setApellidos(e.target.value);
                    }}
                  />
                  {errorApellidos && (
                    <p className="text-danger">{errorApellidos}</p>
                  )}
                </div>

                <div className="mb-3">
                  <label className="form-label">
                    Email
                  </label>
                  <input
                    type="email"
                    id="email"
                    className="form-control"
                    value={email}
                    onChange={function (e) {
                      setEmail(e.target.value);
                    }}
                  />
                  {errorEmail && <p className="text-danger">{errorEmail}</p>}
                </div>

                <button type="submit" className="btn btn-primary">
                  Guardar cambios
                </button>
              </form>
            </div>
          </div>
        </div>

        {/* Columna derecha: cambio de contrasena */}
        <div className="col-12 col-lg-6">
          <div className="card">
            <div className="card-header">Cambiar contraseña</div>
            <div className="card-body">
              {passwordGuardada && (
                <div className="alert alert-success">
                  Contraseña actualizada correctamente.
                </div>
              )}

              <form onSubmit={cambiarPassword}>
                <div className="mb-3">
                  <label className="form-label">
                    Contraseña actual
                  </label>
                  <input
                    type="password"
                    id="passwordActual"
                    className="form-control"
                    value={passwordActual}
                    onChange={function (e) {
                      setPasswordActual(e.target.value);
                    }}
                  />
                  {errorPasswordActual && (
                    <p className="text-danger">{errorPasswordActual}</p>
                  )}
                </div>

                <div className="mb-3">
                  <label className="form-label">
                    Nueva contraseña
                  </label>
                  <input
                    type="password"
                    id="passwordNueva"
                    className="form-control"
                    value={passwordNueva}
                    onChange={function (e) {
                      setPasswordNueva(e.target.value);
                    }}
                  />
                  {errorPasswordNueva && (
                    <p className="text-danger">{errorPasswordNueva}</p>
                  )}
                  <small className="text-muted">
                    Minimo 6 caracteres con letras y numeros.
                  </small>
                </div>

                <div className="mb-3">
                  <label className="form-label">
                    Confirmar contraseña
                  </label>
                  <input
                    type="password"
                    id="passwordConfirmar"
                    className="form-control"
                    value={passwordConfirmar}
                    onChange={function (e) {
                      setPasswordConfirmar(e.target.value);
                    }}
                  />
                  {errorPasswordConfirmar && (
                    <p className="text-danger">{errorPasswordConfirmar}</p>
                  )}
                </div>

                <button type="submit" className="btn btn-warning">
                  Cambiar contraseña
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
