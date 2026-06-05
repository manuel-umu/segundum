import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

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
        body: JSON.stringify({
          username: email,
          password: password,
        }),
      });

      if (res.ok) {
        const data = await res.json();
        localStorage.setItem("usuario", JSON.stringify(data));
        navigate("/");
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
    <div className="col-12 col-md-6 col-lg-4 mx-auto mt-5">
      <h2 className="text-center">Iniciar sesion</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      {/*Formulario */}
      <form onSubmit={iniciarSesion}>
        <div className="mb-3">
          <label htmlFor="email" className="form-label">
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
        </div>

        <div className="mb-3">
          <label htmlFor="password" className="form-label">
            Contraseña
          </label>
          <input
            type="password"
            id="password"
            className="form-control"
            value={password}
            onChange={function (e) {
              setPassword(e.target.value);
            }}
          />
        </div>

        <button type="submit" className="btn btn-primary w-100">
          Entrar
        </button>
      </form>
      <p className="text-center mt-3">
        ¿No tienes cuenta? <a href="/register">Registrate</a>
      </p>
    </div>
  );
}
