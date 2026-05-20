import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MainLayout from './pages/MainLayout';

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
          <Route index element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/productos" element={<ProductList />} />
          {/* /nuevo debe ir antes que /:id para no ser capturado como id */}
          <Route path="/productos/nuevo" element={<ProductNew />} />
          <Route path="/productos/:id" element={<ProductDetail />} />
          <Route path="/productos/:id/editar" element={<ProductEdit />} />
          <Route path="/mis-ventas" element={<MySales />} />
          <Route path="/mis-compras" element={<MyPurchases />} />
          <Route path="/perfil" element={<Profile />} />
          <Route path="/admin/usuarios" element={<AdminUsers />} />
          <Route path="/admin/ventas" element={<AdminSales />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
