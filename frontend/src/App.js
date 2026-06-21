import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import MainLayout from './components/MainLayout';

import Login from './pages/Login';
import Registro from './pages/Registro';
import ListaProductos from './pages/ListaProductos';
import MisCompras from './pages/MisCompras';
import Perfil from './pages/Perfil';
import AdminUsuarios from './pages/AdminUsuarios';
import AdminCompraventas from './pages/AdminCompraventas';
import MisProductos from './pages/MisProductos';

// Función auxiliar que devuelve el usuario
function getUsuario() {
  return JSON.parse(localStorage.getItem("usuario"));
}

// Función auxiliar que comprueba autenticación
function RequireAuth({ children }) {
  const usuario = getUsuario();
  if (!usuario) {
    return <Navigate to="/login" />;
  }
  return children;
}

// Función auxiliar que comprueba autorización
function RequireAdmin({ children }) {
  const usuario = getUsuario();
  if (!usuario) {
    return <Navigate to="/login" />;
  }
  if (!usuario.roles || usuario.roles.indexOf("ADMINISTRADOR") === -1) {
    return <Navigate to="/" />;
  }
  return children;
}

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Login sin layout: sin header ni footer */}
        <Route element={<MainLayout />}>
          <Route index element={<ListaProductos />} />
          <Route path="/login" element={<Login />} />
          <Route path="/registro" element={<Registro />} />
          <Route path="/misproductos" element={
            <RequireAuth><MisProductos /></RequireAuth>
          } />
          <Route path="/miscompras" element={
            <RequireAuth><MisCompras /></RequireAuth>
          } />
          <Route path="/perfil" element={
            <RequireAuth><Perfil /></RequireAuth>
          } />
          <Route path="/admin/usuarios" element={
            <RequireAdmin><AdminUsuarios /></RequireAdmin>
          } />
          <Route path="/admin/compraventas" element={
            <RequireAdmin><AdminCompraventas /></RequireAdmin>
          } />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}