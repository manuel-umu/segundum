import { useState } from 'react';
import { Link } from 'react-router-dom';
import useAuth from '../hooks/useAuth';
import useFetch from '../hooks/useFetch';
import Pagination from '../components/Pagination';
import ProductCard from '../components/ProductCard';
import { listMyProducts } from '../services/productService';
import '../styles/MySales.css';

const PAGE_SIZE = 8;
const TABS = [
  { key: 'en_venta', label: 'En venta' },
  { key: 'vendidos', label: 'Vendidos' },
];

export default function MySales() {
  const { user } = useAuth();
  const [tab, setTab] = useState('en_venta');
  const [page, setPage] = useState(1);

  const { data, loading, error } = useFetch(
    () => listMyProducts(user.id, { page, pageSize: PAGE_SIZE, filter: tab }),
    [tab, page]
  );

  // Cambiar de pestana resetea la pagina
  function handleTabChange(key) {
    setTab(key);
    setPage(1);
  }

  const { items = [], total = 0 } = data ?? {};

  return (
    <section className='container py-4'>
      <h1 className='mb-4'>Mis productos</h1>

      {/* Pestanas Bootstrap */}
      <ul className='nav nav-tabs mb-4' role='tablist'>
        {TABS.map(t => (
          <li className='nav-item' key={t.key} role='presentation'>
            <button
              className={`nav-link ${tab === t.key ? 'active' : ''}`}
              onClick={() => handleTabChange(t.key)}
              role='tab'
              aria-selected={tab === t.key}
            >
              {t.label}
            </button>
          </li>
        ))}
      </ul>

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
        <div className='my-sales__empty text-center py-5'>
          <p className='text-muted'>
            {tab === 'en_venta'
              ? 'No tienes productos en venta.'
              : 'Aún no has vendido ningún producto.'}
          </p>
          {tab === 'en_venta' && (
            <Link to='/productos/nuevo' className='btn btn-primary'>
              + Publicar producto
            </Link>
          )}
        </div>
      )}

      {/* Grid de productos */}
      {!loading && !error && items.length > 0 && (
        <>
          <div className='row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-3'>
            {items.map(producto => (
              <div className='col' key={producto.id}>
                <ProductCard producto={producto} />
              </div>
            ))}
          </div>

          <div className='mt-4'>
            <Pagination
              page={page}
              pageSize={PAGE_SIZE}
              total={total}
              onChange={setPage}
            />
          </div>
        </>
      )}
    </section>
  );
}
