import { NavLink, useNavigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth';
import '../styles/Navbar.css';

function navLinkClass({ isActive }) {
  return 'nav-link' + (isActive ? ' active' : '');
}

export default function Navbar() {
  const { isAuthenticated, isAdmin, user, logout } = useAuth();
  const navigate = useNavigate();

  async function handleLogout() {
    await logout();
    navigate('/');
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
              <NavLink className={navLinkClass} to="/" end>Inicio</NavLink>
            </li>
            <li className="nav-item">
              <NavLink className={navLinkClass} to="/productos">Productos</NavLink>
            </li>

            {isAuthenticated && (
              <>
                <li className="nav-item">
                  <NavLink className={navLinkClass} to="/mis-ventas">Mis Ventas</NavLink>
                </li>
                <li className="nav-item">
                  <NavLink className={navLinkClass} to="/mis-compras">Mis Compras</NavLink>
                </li>
              </>
            )}

            {isAdmin && (
              <>
                <li className="nav-item">
                  <NavLink className={navLinkClass} to="/admin/usuarios">Admin Usuarios</NavLink>
                </li>
                <li className="nav-item">
                  <NavLink className={navLinkClass} to="/admin/ventas">Admin Ventas</NavLink>
                </li>
              </>
            )}
          </ul>

          {/* Sesión — derecha */}
          <ul className="navbar-nav ms-auto align-items-lg-center">
            {isAuthenticated ? (
              <>
                <li className="nav-item">
                  <NavLink className={navLinkClass} to="/perfil">
                    {user.nombre}
                  </NavLink>
                </li>
                <li className="nav-item">
                  <button
                    className="btn btn-outline-light ms-2"
                    onClick={handleLogout}
                  >
                    Cerrar sesión
                  </button>
                </li>
              </>
            ) : (
              <li className="nav-item">
                <NavLink className="btn btn-outline-light ms-2" to="/login">
                  Login
                </NavLink>
              </li>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
}
