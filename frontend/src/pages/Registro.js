import { useState } from "react";

export default function Registro() {
  const [email, setEmail] = useState("");
  const [nombre, setNombre] = useState("");
  const [apellidos, setApellidos] = useState("");
  const [clave, setClave] = useState("");
  const [confirmarClave, setConfirmarClave] = useState("");
  const [fechaNacimiento, setFechaNacimiento] = useState("");
  const [telefono, setTelefono] = useState("");
  const [error, setError] = useState("");

  const hoy = new Date().toISOString().split(".")[0];

  async function registrar(e) {
    e.preventDefault();
    setError("");

    // Validacion basica
    if (
      nombre === "" ||
      apellidos === "" ||
      email === "" ||
      clave === "" ||
      confirmarClave === "" ||
      fechaNacimiento === "" ||
      telefono === ""
    ) {
      setError("Por favor, rellena todos los campos.");
      return;
    }
    if (clave !== confirmarClave) {
      setError("Las contraseñas no coinciden.");
      return;
    }

    if (fechaNacimiento > hoy) {
      setError("La fecha de nacimiento no puede ser posterior a hoy.");
      return;
    }

    try {
      const res = await fetch("/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          nombre: nombre,
          apellidos: apellidos,
          email: email,
          fechaNac: fechaNacimiento,
          clave: clave,
          telefono: telefono,
          admin: false
        }),
      });

      if (res.ok) {
        window.location.href = "/login?registered=true";
        return;
      }
      if (res.status === 409) {
        setError("El email ya está registrado.");
      } else if (res.status === 400) {
        setError("Datos del formulario no validos.");
      } else {
        setError(
          "No se pudo registrar. Intentalo de nuevo más tarde. Error " +
            res.status,
        );
      }
    } catch (err) {
      setError("No se pudo conectar con el servidor.");
    }
  }

  return (
    <div className="col-12 col-md-8 col-lg-6 mx-auto mb-5">
      <div className="card shadow border-1 rounded-4">
        <div className="card-body p-4 p-md-5">
          <h2 className="text-center">Registro</h2>
          {error && <div className="alert alert-danger">{error}</div>}
          {/*Formulario */}
          <form onSubmit={registrar}>
            {/*Nombre */}
            <div className="mb-3">
              <label htmlFor="nombre" className="form-label fw-medium">
                Nombre:
              </label>
              <input
                type="text"
                id="nombre"
                className="form-control"
                value={nombre}
                onChange={function (e) {
                  setNombre(e.target.value);
                }}
                required
                placeholder="Introduce tu nombre"
              />
            </div>
            {/*Apellidos */}
            <div className="mb-3">
              <label htmlFor="apellido" className="form-label fw-medium">
                Apellidos:
              </label>
              <input
                type="text"
                id="apellidos"
                className="form-control"
                value={apellidos}
                onChange={function (e) {
                  setApellidos(e.target.value);
                }}
                required
                placeholder="Introduce tus apellidos"
              />
            </div>
            {/* Email */}
            <div className="mb-3">
              <label htmlFor="email" className="form-label fw-medium">
                Email:
              </label>
              <input
                type="email"
                id="email"
                className="form-control"
                value={email}
                onChange={function (e) {
                  setEmail(e.target.value);
                }}
                required
                placeholder="Introduce tu email"
              />
            </div>

            {/* Contraseña */}
            <div className="row mb-3">
              <div className="col-12 col-md-6 mb-3 mb-md-0">
                <label htmlFor="password" className="form-label fw-medium">
                  Contraseña:
                </label>
                <input
                  type="password"
                  id="password"
                  className="form-control"
                  value={clave}
                  onChange={function (e) {
                    setClave(e.target.value);
                  }}
                  required
                  placeholder="Introduce una contraseña segura"
                />
              </div>
              <div className="col-12 col-md-6">
                <label
                  htmlFor="confirmPassword"
                  className="form-label fw-medium"
                >
                  Confirmar Contraseña:
                </label>
                <input
                  type="password"
                  id="confirmPassword"
                  className="form-control"
                  value={confirmarClave}
                  onChange={function (e) {
                    setConfirmarClave(e.target.value);
                  }}
                  required
                  placeholder="Repite tu contraseña"
                />
              </div>
            </div>

            {/* Fecha de Nacimiento */}
            <div className="mb-3">
              <label htmlFor="fechaNac" className="form-label fw-medium">
                Fecha de Nacimiento:
              </label>
              <input
                type="date"
                id="fechaNac"
                className="form-control"
                value={fechaNacimiento}
                onChange={function (e) {
                  setFechaNacimiento(e.target.value);
                }}
                required
              />
            </div>
            {/* Telefono */}
            <div className="mb-3">
              <label htmlFor="telefono" className="form-label fw-medium">
                Teléfono:
              </label>
              <input
                type="tel"
                id="telefono"
                className="form-control"
                value={telefono}
                onChange={function (e) {
                  setTelefono(e.target.value);
                }}
                required
                placeholder="Introduce tu teléfono"
              />
            </div>

            <button type="submit" className="btn btn-primary w-100 fw-medium">
              Registrarse
            </button>
          </form>
        </div>
      </div>
      <p className="text-center mt-3">
        ¿Ya tienes cuenta? <a href="/login">Inicia sesión</a>
      </p>
    </div>
  );
}
