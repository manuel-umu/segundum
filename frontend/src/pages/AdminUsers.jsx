import { useState } from 'react';
import useFetch from '../hooks/useFetch';
import Pagination from '../components/Pagination';
import { listAllUsers } from '../services/userService';
import '../styles/AdminUsers.css';

const PAGE_SIZE = 10;

// Colores de badge por rol
const ROL_BADGE = {
  admin: 'danger',
  user: 'secondary',
};

export default function AdminUsers() {
  const [page, setPage] = useState(1);

  const { data, loading, error } = useFetch(
    () => listAllUsers({ page, pageSize: PAGE_SIZE }),
    [page]
  );

  const { items = [], total = 0 } = data ?? {};

  return (
    <section className='container py-4'>
      <div className='d-flex justify-content-between align-items-center mb-4'>
        <h1 className='mb-0'>Usuarios registrados</h1>
        <span className='badge bg-primary fs-6'>{total} usuarios</span>
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
            <table className='table table-hover align-middle admin-users__table'>
              <thead className='table-dark'>
                <tr>
                  <th scope='col'>#</th>
                  <th scope='col'>Nombre</th>
                  <th scope='col'>Email</th>
                  <th scope='col'>Rol</th>
                  <th scope='col'>Fecha registro</th>
                </tr>
              </thead>
              <tbody>
                {items.length === 0 ? (
                  <tr>
                    <td colSpan={6} className='text-center text-muted py-4'>
                      No hay usuarios registrados.
                    </td>
                  </tr>
                ) : (
                  items.map(u => {
                    const fecha = new Date(u.fechaRegistro).toLocaleDateString('es-ES', {
                      day: '2-digit', month: 'short', year: 'numeric',
                    });
                    return (
                      <tr key={u.id}>
                        <td className='text-muted'>{u.id}</td>
                        <td>{u.nombre} {u.apellidos}</td>
                        <td>{u.email}</td>
                        <td>
                          <span className={`badge bg-${ROL_BADGE[u.rol] ?? 'secondary'}`}>
                            {u.rol}
                          </span>
                        </td>
                        <td>{fecha}</td>
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
