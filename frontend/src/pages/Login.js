import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Login() {
  // Estado para los campos del formulario
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

    console.log("Login con:", email, password);

    try {
      const res = await fetch("/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: email,
          password,
        }),
      });

      const data = await res.json();

      if (res.ok) {
        localStorage.setItem("usuario", JSON.stringify(data));
        navigate("/");
      } else {
        setError("Error iniciando sesión: " + data.mensaje);
      }
    } catch (error) {
      console.log("STATUS:", error.response?.status);
      console.log("DATA:", error.response?.data);
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
