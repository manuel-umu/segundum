import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MainLayout from './layouts/MainLayout';
import RequireAuth from './components/RequireAuth';
import RequireRole from './components/RequireRole';

import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import ProductList from './pages/ProductList';
import ProductNew from './pages/ProductNew';
import ProductDetail from './pages/ProductDetail';
import ProductEdit from './pages/ProductEdit';
import MySales from './pages/MySales';
import MyPurchases from './pages/MyPurchases';
import Profile from './pages/Profile';
import AdminUsers from './pages/AdminUsers';
import AdminSales from './pages/AdminSales';
import NotFound from './pages/NotFound';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Todas nuestras páginas serán de forma MainLayout */}
        <Route element={<MainLayout />}>
          {/* Rutas públicas */}
          <Route index element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/productos" element={<ProductList />} />
          <Route path="/productos/:id" element={<ProductDetail />} />

          {/* Rutas privadas — cualquier usuario autenticado */}
          
          {/* Publicar un producto */}
          <Route path="/productos/nuevo" element={
            <RequireAuth><ProductNew /></RequireAuth>
          } />
          {/* Modificar un producto */}
          <Route path="/productos/:id/editar" element={
            <RequireAuth><ProductEdit /></RequireAuth>
          } />
          {/* Comprobar mis ventas */}
          <Route path="/mis-ventas" element={
            <RequireAuth><MySales /></RequireAuth>
          } />
          {/* Comprobar mis compras */}
          <Route path="/mis-compras" element={
            <RequireAuth><MyPurchases /></RequireAuth>
          } />
          {/* Entrar en mi perfil*/}
          <Route path="/perfil" element={
            <RequireAuth><Profile /></RequireAuth>
          } />

          {/* Rutas privadas — solo admin */}

          {/* Administrar usuarios */}
          <Route path="/admin/usuarios" element={
            <RequireAuth><RequireRole role="admin"><AdminUsers /></RequireRole></RequireAuth>
          } />

          {/* Adiministrar ventas */}
          <Route path="/admin/ventas" element={
            <RequireAuth><RequireRole role="admin"><AdminSales /></RequireRole></RequireAuth>
          } />

          {/* Página no encontrada */}
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
