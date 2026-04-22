import { useState } from 'react';
import { Link } from 'react-router-dom';
import useFetch from '../hooks/useFetch';
import Pagination from '../components/Pagination';
import { listAllSales } from '../services/saleService';
import { PRODUCTOS } from '../services/mockData';
import { MOCK_USERS } from '../services/authService';
import '../styles/AdminSales.css';

const PAGE_SIZE = 10;

const ESTADOS = ['solicitada', 'aceptada', 'rechazada', 'completada'];

const ESTADO_BADGE = {
  solicitada: { label: 'Solicitada', color: 'warning' },
  aceptada: { label: 'Aceptada', color: 'info' },
  rechazada: { label: 'Rechazada', color: 'danger' },
  completada: { label: 'Completada', color: 'success' },
};

export default function AdminSales() {
  const [page, setPage] = useState(1);
  const [filtroEstado, setFiltroEstado] = useState('');

  const { data, loading, error } = useFetch(
    () => listAllSales({ page, pageSize: PAGE_SIZE }),
    [page]
  );

  // Filtra en cliente por estado si hay filtro activo
  const todasVentas = data?.items ?? [];
  const ventasFiltradas = filtroEstado
    ? todasVentas.filter(v => v.estado === filtroEstado)
    : todasVentas;
  const total = data?.total ?? 0;

  // Cambia el filtro de estado y resetea la pagina
  function handleEstadoChange(e) {
    setFiltroEstado(e.target.value);
    setPage(1);
  }

  return (
    <section className='container py-4'>
      <div className='d-flex justify-content-between align-items-center mb-4'>
        <h1 className='mb-0'>Compraventas</h1>
        <span className='badge bg-primary fs-6'>{total} registros</span>
      </div>

      {/* Filtro por estado */}
      <div className='admin-sales__filter mb-3 d-flex align-items-center gap-2'>
        <label htmlFor='filtro-estado-venta' className='form-label mb-0 fw-semibold'>
          Estado:
        </label>
        <select
          id='filtro-estado-venta'
          className='form-select form-select-sm w-auto'
          value={filtroEstado}
          onChange={handleEstadoChange}
        >
          <option value=''>Todos</option>
          {ESTADOS.map(e => (
            <option key={e} value={e}>{ESTADO_BADGE[e]?.label ?? e}</option>
          ))}
        </select>
      </div>

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

      {/* Tabla */}
      {!loading && !error && (
        <>
          <div className='table-responsive'>
            <table className='table table-hover align-middle admin-sales__table'>
              <thead className='table-dark'>
                <tr>
                  <th scope='col'>#</th>
                  <th scope='col'>Producto</th>
                  <th scope='col'>Precio</th>
                  <th scope='col'>Comprador</th>
                  <th scope='col'>Vendedor</th>
                  <th scope='col'>Fecha</th>
                  <th scope='col'>Estado</th>
                </tr>
              </thead>
              <tbody>
                {ventasFiltradas.length === 0 ? (
                  <tr>
                    <td colSpan={7} className='text-center text-muted py-4'>
                      No hay compraventas con ese estado.
                    </td>
                  </tr>
                ) : (
                  ventasFiltradas.map(venta => {
                    const producto = PRODUCTOS.find(p => p.id === venta.producto_id);
                    const comprador = MOCK_USERS.find(u => u.id === venta.comprador_id);
                    const vendedor = MOCK_USERS.find(u => u.id === venta.vendedor_id);
                    const badge = ESTADO_BADGE[venta.estado] ?? { label: venta.estado, color: 'secondary' };
                    const fecha = new Date(venta.fecha_solicitud).toLocaleDateString('es-ES', {
                      day: '2-digit', month: 'short', year: 'numeric',
                    });

                    return (
                      <tr key={venta.id}>
                        <td className='text-muted'>{venta.id}</td>
                        <td>
                          {producto ? (
                            <Link
                              to={`/productos/${producto.id}`}
                              className='admin-sales__product-link'
                            >
                              <img
                                src={producto.imagen}
                                alt={producto.titulo}
                                className='admin-sales__thumb me-2'
                              />
                              {producto.titulo}
                            </Link>
                          ) : '—'}
                        </td>
                        <td>
                          {producto
                            ? producto.precio.toLocaleString('es-ES', { style: 'currency', currency: 'EUR' })
                            : '—'}
                        </td>
                        <td>{comprador ? `${comprador.nombre} ${comprador.apellidos}` : '—'}</td>
                        <td>{vendedor ? `${vendedor.nombre} ${vendedor.apellidos}` : '—'}</td>
                        <td>{fecha}</td>
                        <td>
                          <span className={`badge bg-${badge.color}`}>{badge.label}</span>
                        </td>
                      </tr>
                    );
                  })
                )}
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
