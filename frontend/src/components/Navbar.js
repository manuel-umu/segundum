import { useState, useEffect } from "react";
import { NavLink } from "react-router-dom";
import "../App.css";

function navLinkClass({ isActive }) {
  return "nav-link" + (isActive ? " active" : "");
}

// Lee el usuario guardado en localStorage (o null si no hay sesion)
function leerUsuario() {
  return JSON.parse(localStorage.getItem("usuario"));
}

export default function Navbar() {
  const [usuario, setUsuario] = useState(leerUsuario());

  useEffect(function () {
    function alCambiarStorage() {
      setUsuario(leerUsuario());
    }
    window.addEventListener("storage", alCambiarStorage);
    return function () {
      window.removeEventListener("storage", alCambiarStorage);
    };
  }, []);

  const isAuthenticated = usuario !== null;
  const isAdmin =
    isAuthenticated &&
    usuario.roles &&
    usuario.roles.indexOf("ADMINISTRADOR") !== -1;

  async function logout() {
    await fetch("/auth/logout", {
      method: "POST",
      credentials: "include",
    });
    localStorage.removeItem("usuario");
    setUsuario(null);
    window.location.href = "/";
  }

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container">
        <NavLink className="navbar-brand" to="/">
          SegundUM
        </NavLink>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarMain"
          aria-controls="navbarMain"
          aria-expanded="false"
          aria-label="Abrir menú de navegación"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarMain">
          {/* Enlaces principales — izquierda */}
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <NavLink className={navLinkClass} to="/">
                Productos
              </NavLink>
            </li>

            {isAuthenticated && (
              <>
                <li className="nav-item">
                  <NavLink className={navLinkClass} to="/misproductos">
                    Mis Productos
                  </NavLink>
                </li>
                <li className="nav-item">
                  <NavLink className={navLinkClass} to="/miscompras">
                    Mis Compras
                  </NavLink>
                </li>
              </>
            )}

            {isAdmin && (
              <>
                <li className="nav-item">
                  <NavLink className={navLinkClass} to="/admin/usuarios">
                    Admin Usuarios
                  </NavLink>
                </li>
                <li className="nav-item">
                  <NavLink className={navLinkClass} to="/admin/compraventas">
                    Admin Compraventas
                  </NavLink>
                </li>
              </>
            )}
          </ul>

          {/* Sesion — derecha */}
          <ul className="navbar-nav ms-auto">
            {isAuthenticated && (
              <li className="nav-item">
                <NavLink className={navLinkClass} to="/perfil">
                  Perfil
                </NavLink>
              </li>
            )}
            <li className="nav-item">
              {usuario ? (
                <button className="btn btn-outline-light ms-2" onClick={logout}>
                  Logout
                </button>
              ) : (
                <NavLink className="btn btn-outline-light ms-2" to="/login">
                  Login
                </NavLink>
              )}
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}
