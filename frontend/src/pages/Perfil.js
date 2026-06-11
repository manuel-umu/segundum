import { useEffect, useState } from "react";

const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
// Minimo 6 caracteres con letras y números
const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d).{6,}$/;

const usuario = JSON.parse(localStorage.getItem("usuario"));

export default function Profile() {

  // Datos personales
  const [nombre, setNombre] = useState("");
  const [apellidos, setApellidos] = useState("");
  const [email, setEmail] = useState("");
  const [telefono, setTelefono] = useState("");
  const [fechaNac, setFechaNac] = useState("");

  // Errores del formulario de datos personales
  const [errorNombre, setErrorNombre] = useState("");
  const [errorApellidos, setErrorApellidos] = useState("");
  const [errorEmail, setErrorEmail] = useState("");
  const [perfilGuardado, setPerfilGuardado] = useState(false);
  const [errorCarga, setErrorCarga] = useState("");

  // Campos de contraseña
  const [passwordActual, setPasswordActual] = useState("");
  const [passwordNueva, setPasswordNueva] = useState("");
  const [passwordConfirmar, setPasswordConfirmar] = useState("");

  // Errores del formulario de contraseña
  const [errorPasswordActual, setErrorPasswordActual] = useState("");
  const [errorPasswordNueva, setErrorPasswordNueva] = useState("");
  const [errorPasswordConfirmar, setErrorPasswordConfirmar] = useState("");
  const [passwordGuardada, setPasswordGuardada] = useState(false);

  useEffect(function () {
    if (usuario === null) {
      setErrorCarga("No hay sesion iniciada.");
      return;
    }

    async function cargarDatos() {
      try {
        const res = await fetch("/usuarios/" + usuario.id);
        if (!res.ok) {
          setErrorCarga("No se pudieron cargar los datos del usuario.");
          return;
        }
        const data = await res.json();
        setNombre(data.nombre === null ? "" : data.nombre);
        setApellidos(data.apellidos === null ? "" : data.apellidos);
        setEmail(data.email === null ? "" : data.email);
        setTelefono(data.telefono === null ? "" : data.telefono);
        setFechaNac(data.fechaNac === null ? "" : data.fechaNac);
      } catch (err) {
        setErrorCarga("Error de red al cargar el usuario.");
      }
    }

    cargarDatos();
  }, []);

  async function guardarPerfil(e) {
    e.preventDefault();

    setErrorNombre("");
    setErrorApellidos("");
    setErrorEmail("");
    setPerfilGuardado(false);

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

    try {
      const res = await fetch("/usuarios/" + usuario.id, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          nombre: nombre,
          apellidos: apellidos,
          telefono: telefono,
          fechaNac: fechaNac === "" ? null : fechaNac,
        }),
      });

      if (res.ok) {
        setPerfilGuardado(true);
      } else {
        setErrorNombre("Error al guardar los cambios.");
      }
    } catch (err) {
      setErrorNombre("Error de red al guardar.");
    }
  }

  async function cambiarPassword(e) {
    e.preventDefault();

    setErrorPasswordActual("");
    setErrorPasswordNueva("");
    setErrorPasswordConfirmar("");
    setPasswordGuardada(false);

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

    // Comprobar contraseña actual
    try {
      const resCheck = await fetch("/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username: email, password: passwordActual }),
      });
      if (!resCheck.ok) {
        setErrorPasswordActual("La contraseña actual no es correcta.");
        return;
      }

      // Si la actual era valida, actualizamos con la nueva
      const res = await fetch("/usuarios/" + usuario.id, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          nombre: nombre,
          apellidos: apellidos,
          clave: passwordNueva,
          telefono: telefono,
          fechaNac: fechaNac === "" ? null : fechaNac,
        }),
      });

      if (res.ok) {
        setPasswordGuardada(true);
        setPasswordActual("");
        setPasswordNueva("");
        setPasswordConfirmar("");
      } else {
        setErrorPasswordNueva("Error al actualizar la contraseña.");
      }
    } catch (err) {
      setErrorPasswordNueva("Error de red al cambiar la contraseña.");
    }
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
              {errorCarga && (
                <div className="alert alert-danger">{errorCarga}</div>
              )}
              {perfilGuardado && (
                <div className="alert alert-success">
                  Perfil actualizado correctamente.
                </div>
              )}

              <form onSubmit={guardarPerfil}>
                <div className="mb-3">
                  <label className="form-label">Nombre</label>
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
                  <label className="form-label">Apellidos</label>
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
                  <label className="form-label">Email</label>
                  <input
                    type="email"
                    id="email"
                    className="form-control"
                    value={email}
                    readOnly
                  />
                  <small className="text-muted">
                    El email no se puede modificar.
                  </small>
                  {errorEmail && <p className="text-danger">{errorEmail}</p>}
                </div>

                <div className="mb-3">
                  <label className="form-label">Telefono</label>
                  <input
                    type="tel"
                    id="telefono"
                    className="form-control"
                    value={telefono}
                    onChange={function (e) {
                      setTelefono(e.target.value);
                    }}
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label">Fecha de nacimiento</label>
                  <input
                    type="date"
                    id="fechaNac"
                    className="form-control"
                    value={fechaNac}
                    onChange={function (e) {
                      setFechaNac(e.target.value);
                    }}
                  />
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
                  <label className="form-label">Contraseña actual</label>
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
                  <label className="form-label">Nueva contraseña</label>
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
                  <label className="form-label">Confirmar contraseña</label>
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
