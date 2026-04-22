import { useState } from 'react';
import { Link } from 'react-router-dom';
import useAuth from '../hooks/useAuth';
import useFetch from '../hooks/useFetch';
import Pagination from '../components/Pagination';
import { listMyPurchases } from '../services/saleService';
import { PRODUCTOS } from '../services/mockData';
import { MOCK_USERS as AUTH_USERS } from '../services/authService';
import '../styles/MyPurchases.css';

const PAGE_SIZE = 10;

// Etiquetas y colores de badge para cada estado de venta
const ESTADO_BADGE = {
  solicitada: { label: 'Solicitada', color: 'warning' },
  aceptada: { label: 'Aceptada', color: 'info' },
  rechazada: { label: 'Rechazada', color: 'danger' },
  completada: { label: 'Completada', color: 'success' },
};

export default function MyPurchases() {
  const { user } = useAuth();
  const [page, setPage] = useState(1);

  const { data, loading, error } = useFetch(
    () => listMyPurchases(user.id, { page, pageSize: PAGE_SIZE }),
    [page]
  );

  const { items = [], total = 0 } = data ?? {};

  return (
    <section className='container py-4'>
      <h1 className='mb-4'>Mis compras</h1>

      {/* Spinner */}
      {loading && (
        <div className='d-flex justify-content-center py-5'>
          <div className='spinner-border text-primary' role='status'>
            <span className='visually-hidden'>Cargando...</span>
          </div>
        </div>
      )}

      {/* Error */}
      {!loading && error && (
        <div className='alert alert-danger' role='alert'>{error}</div>
      )}

      {/* Lista vacia */}
      {!loading && !error && items.length === 0 && (
        <div className='text-center py-5'>
          <p className='text-muted'>Todavía no has realizado ninguna compra.</p>
          <Link to='/productos' className='btn btn-primary'>Ver productos</Link>
        </div>
      )}

      {/* Tabla de compras */}
      {!loading && !error && items.length > 0 && (
        <>
          <div className='table-responsive'>
            <table className='table table-hover align-middle'>
              <thead className='table-light'>
                <tr>
                  <th scope='col'>Producto</th>
                  <th scope='col'>Vendedor</th>
                  <th scope='col'>Fecha solicitud</th>
                  <th scope='col'>Estado</th>
                </tr>
              </thead>
              <tbody>
                {items.map(venta => {
                  const producto = PRODUCTOS.find(p => p.id === venta.producto_id);
                  const vendedor = AUTH_USERS.find(u => u.id === venta.vendedor_id);
                  const badge = ESTADO_BADGE[venta.estado] ?? { label: venta.estado, color: 'secondary' };
                  const fecha = new Date(venta.fecha_solicitud).toLocaleDateString('es-ES', {
                    day: '2-digit', month: 'short', year: 'numeric',
                  });

                  return (
                    <tr key={venta.id}>
                      <td>
                        {producto ? (
                          <Link to={`/productos/${producto.id}`} className='my-purchases__product-link'>
                            <img
                              src={producto.imagen}
                              alt={producto.titulo}
                              className='my-purchases__thumb me-2'
                            />
                            {producto.titulo}
                          </Link>
                        ) : '—'}
                      </td>
                      <td>{vendedor ? `${vendedor.nombre} ${vendedor.apellidos}` : '—'}</td>
                      <td>{fecha}</td>
                      <td>
                        <span className={`badge bg-${badge.color}`}>{badge.label}</span>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>

          <Pagination
            page={page}
            pageSize={PAGE_SIZE}
            total={total}
            onChange={setPage}
          />
        </>
      )}
    </section>
  );
}
