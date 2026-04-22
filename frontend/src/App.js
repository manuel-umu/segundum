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
        <Route element={<MainLayout />}>
          {/* Rutas públicas */}
          <Route index element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/productos" element={<ProductList />} />
          <Route path="/productos/:id" element={<ProductDetail />} />

          {/* Rutas privadas — cualquier usuario autenticado */}
          <Route path="/productos/nuevo" element={
            <RequireAuth><ProductNew /></RequireAuth>
          } />
          <Route path="/productos/:id/editar" element={
            <RequireAuth><ProductEdit /></RequireAuth>
          } />
          <Route path="/mis-ventas" element={
            <RequireAuth><MySales /></RequireAuth>
          } />
          <Route path="/mis-compras" element={
            <RequireAuth><MyPurchases /></RequireAuth>
          } />
          <Route path="/perfil" element={
            <RequireAuth><Profile /></RequireAuth>
          } />

          {/* Rutas privadas — solo admin */}
          <Route path="/admin/usuarios" element={
            <RequireAuth><RequireRole role="admin"><AdminUsers /></RequireRole></RequireAuth>
          } />
          <Route path="/admin/ventas" element={
            <RequireAuth><RequireRole role="admin"><AdminSales /></RequireRole></RequireAuth>
          } />

          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
