import { useState, useEffect } from "react";
import { useSearchParams } from "react-router-dom";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [searchParams] = useSearchParams();
  
  useEffect(() => {
    const id = searchParams.get("id");
    const token = searchParams.get("token");
    
    if (id && token) {
      const nombre = searchParams.get("nombre");
      const roles = searchParams.get("roles");
      localStorage.setItem("usuario", JSON.stringify({ id, nombre, roles, token }));
      window.location.href = "/";
    } else if (localStorage.getItem("usuario")) {
      window.location.href = "/";
    }
  }, [searchParams]);

  async function iniciarSesion(e) {
    e.preventDefault();
    setError("");

    // Validacion basica
    if (email === "" || password === "") {
      setError("Por favor, rellena todos los campos.");
      return;
    }
    try {
      const res = await fetch("/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
        body: JSON.stringify({
          username: email,
          password: password,
        }),
      });
      if (res.ok) {
        const data = await res.json();
        localStorage.setItem("usuario", JSON.stringify(data));
        window.location.href = "/";
        return;
      }
      if (res.status === 401) {
        setError("Email o contraseña incorrectos.");
      } else if (res.status === 400) {
        setError("Datos del formulario no validos.");
      } else {
        setError("No se pudo iniciar sesion. Intentalo de nuevo más tarde.");
      }
    } catch (err) {
      setError("No se pudo conectar con el servidor.");
    }
  }

  return (
    <div className="col-12 col-md-8 col-lg-6 mx-auto mb-5">
      <div className="card shadow border-1 rounded-4">
        <div className="card-body p-4 p-md-5">
          <h2 className="text-center mb-5">Iniciar sesion</h2>
          {error && <div className="alert alert-danger">{error}</div>}
          {searchParams.get("registered") && (
            <div className="alert alert-success">
              Usuario creado correctamente. Inicia sesión para continuar.
            </div>
          )}
          {/*Formulario */}
          <form onSubmit={iniciarSesion}>
            <div className="mb-4">
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
                placeholder="Introduce tu email"
              />
            </div>

            <div className="mb-5">
              <label htmlFor="password" className="form-label fw-medium">
                Contraseña:
              </label>
              <input
                type="password"
                id="password"
                className="form-control"
                value={password}
                onChange={function (e) {
                  setPassword(e.target.value);
                }}
                placeholder="Introduce tu contraseña"
              />
            </div>

            <button type="submit" className="btn btn-primary w-100">
              Entrar
            </button>
            {/* Separador */}
            <div className="text-center my-3 text-muted">——</div>

            {/* OAuth2 GitHub */}
            <button
              type="button"
              className="btn btn-dark w-100"
              onClick={function() {
                window.location.href = "http://localhost:8090/oauth2/authorization/github"
              }}
            >
              Entrar con GitHub
            </button>
          </form>
        </div>
      </div>
      <p className="text-center mt-3">
        ¿No tienes cuenta? <a href="/registro">Registrate</a>
      </p>
    </div>
  );
}
