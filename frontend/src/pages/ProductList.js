import { useState } from 'react';
import { Link } from 'react-router-dom';
import ProductCard from '../components/ProductCard';
import Pagination from '../components/Pagination';
import useFetch from '../hooks/useFetch';
import useAuth from '../hooks/useAuth';
import { listProducts } from '../services/productService';
import '../styles/ProductList.css';

const PAGE_SIZE = 12;

export default function ProductList() {
  const [page, setPage] = useState(1);
  const { isAuthenticated } = useAuth();

  const { data, loading, error } = useFetch(
    () => listProducts({ page, pageSize: PAGE_SIZE }),
    [page]
  );

  // Spinner mientras carga
  if (loading) {
    return (
      <div className='product-list__feedback d-flex justify-content-center align-items-center'>
        <div className='spinner-border text-primary' role='status'>
          <span className='visually-hidden'>Cargando...</span>
        </div>
      </div>
    );
  }

  // Mensaje de error de red o del servicio
  if (error) {
    return (
      <div className='container py-4'>
        <div className='alert alert-danger' role='alert'>
          No se pudieron cargar los productos: {error}
        </div>
      </div>
    );
  }

  const { items = [], total = 0 } = data ?? {};
  const totalPages = Math.ceil(total / PAGE_SIZE);

  return (
    <section className='container py-4'>
      <div className='d-flex justify-content-between align-items-center mb-4'>
        <h1 className='product-list__title mb-0'>Productos en venta</h1>
        {isAuthenticated && (
          <Link to='/productos/nuevo' className='btn btn-primary'>
            + Publicar producto
          </Link>
        )}
      </div>

      {/* Lista vacia */}
      {items.length === 0 ? (
        <p className='text-muted'>No hay productos disponibles.</p>
      ) : (
        <div className='row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-3'>
          {items.map(producto => (
            <div className='col' key={producto.id}>
              <ProductCard producto={producto} />
            </div>
          ))}
        </div>
      )}

      <div className='mt-4'>
        <Pagination
          page={page}
          pageSize={PAGE_SIZE}
          total={total}
          onChange={setPage}
        />
      </div>
    </section>
  );
}
