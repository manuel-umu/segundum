import { NavLink, useLocation, useNavigate } from "react-router-dom";
import "../App.css";

const isAuthenticated = true;
const isAdmin = true;

function navLinkClass({ isActive }) {
  return "nav-link" + (isActive ? " active" : "");
}

export default function Navbar() {
  const navigate = useNavigate();
  
  const usuario = JSON.parse(localStorage.getItem("usuario"));

  const logout = () => {
    localStorage.removeItem("usuario");
    navigate("/");
  };

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
              <NavLink className={navLinkClass} to="/productos">
                Productos
              </NavLink>
            </li>

            {isAuthenticated && (
              <>
                <li className="nav-item">
                  <NavLink className={navLinkClass} to="/ventas">
                    Mis Ventas
                  </NavLink>
                </li>
                <li className="nav-item">
                  <NavLink className={navLinkClass} to="/compras">
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
            <li className="nav-item">
              <NavLink className={navLinkClass} to="/perfil">
                Perfil
              </NavLink>
            </li>
            <li className="nav-item">
              <button
                className="btn btn-outline-light ms-2"
                onClick={logout}
              >
                Logout
              </button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}
