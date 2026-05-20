import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MainLayout from './components/MainLayout';

import Login from './pages/Login';
import Registro from './pages/Registro';
import ListaProductos from './pages/ListaProductos';
import MisVentas from './pages/MisVentas';
import MisCompras from './pages/MisCompras';
import Perfil from './pages/Perfil';
import AdminUsuarios from './pages/AdminUsuarios';
import AdminCompraventas from './pages/AdminCompraventas';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Login sin layout: sin header ni footer */}
        <Route index element={<Login />} />

        <Route element={<MainLayout />}>
          <Route path="/registro" element={<Registro />} />
          <Route path="/productos" element={<ListaProductos />} />/*
          <Route path="/ventas" element={<MisVentas />} />
          <Route path="/compras" element={<MisCompras />} />
          <Route path="/perfil" element={<Perfil />} />
          <Route path="/admin/usuarios" element={<AdminUsuarios />} />
          <Route path="/admin/compraventas" element={<AdminCompraventas />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}