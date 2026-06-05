import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MainLayout from './components/MainLayout';

import Login from './pages/Login';
import Registro from './pages/Registro';
import ListaProductos from './pages/ListaProductos';
import MisCompras from './pages/MisCompras';
import Perfil from './pages/Perfil';
import AdminUsuarios from './pages/AdminUsuarios';
import AdminCompraventas from './pages/AdminCompraventas';
import MisProductos from './pages/MisProductos';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Login sin layout: sin header ni footer */}

        <Route element={<MainLayout />}>
          <Route index element={<ListaProductos />} />
          <Route path="/login" element={<Login />} />
          <Route path="/registro" element={<Registro />} />
          <Route path="/misproductos" element={<MisProductos />} />
          <Route path="/miscompras" element={<MisCompras />} />
          <Route path="/perfil" element={<Perfil />} />
          <Route path="/admin/usuarios" element={<AdminUsuarios />} />
          <Route path="/admin/compraventas" element={<AdminCompraventas />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}