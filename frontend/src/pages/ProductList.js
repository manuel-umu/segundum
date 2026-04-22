import { useSearchParams, Link } from 'react-router-dom';
import ProductCard from '../components/ProductCard';
import Pagination from '../components/Pagination';
import ProductFilters from '../components/ProductFilters';
import useFetch from '../hooks/useFetch';
import useAuth from '../hooks/useAuth';
import { listProducts } from '../services/productService';
import '../styles/ProductList.css';

const PAGE_SIZE = 12;

// Extrae los filtros activos de los query params de la URL
function filtersFromParams(params) {
  return {
    descripcion: params.get('descripcion') ?? '',
    categoria: params.get('categoria') ?? '',
    estado: params.get('estado') ?? '',
    precioMax: params.get('precioMax') ?? '',
    sort: params.get('sort') ?? 'fecha_publicacion:desc',
  };
}

export default function ProductList() {
  const [searchParams, setSearchParams] = useSearchParams();
  const { isAuthenticated } = useAuth();

  const page = Number(searchParams.get('page') ?? 1);
  const filters = filtersFromParams(searchParams);

  const { data, loading, error } = useFetch(
    () => listProducts({ ...filters, page, pageSize: PAGE_SIZE }),
    [searchParams.toString()]
  );

  // Actualiza la URL con los nuevos filtros y resetea la pagina a 1
  function handleFiltersChange(newFilters) {
    const next = { ...newFilters, page: '1' };
    // Elimina los parametros vacios para mantener la URL limpia
    Object.keys(next).forEach(k => { if (!next[k]) delete next[k]; });
    setSearchParams(next);
  }

  function handlePageChange(newPage) {
    setSearchParams(prev => {
      const next = new URLSearchParams(prev);
      next.set('page', String(newPage));
      return next;
    });
  }

  const { items = [], total = 0 } = data ?? {};

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

      <ProductFilters filters={filters} onChange={handleFiltersChange} />

      {/* Spinner mientras carga */}
      {loading && (
        <div className='product-list__feedback d-flex justify-content-center align-items-center'>
          <div className='spinner-border text-primary' role='status'>
            <span className='visually-hidden'>Cargando...</span>
          </div>
        </div>
      )}

      {/* Error de red o del servicio */}
      {!loading && error && (
        <div className='alert alert-danger' role='alert'>
          No se pudieron cargar los productos: {error}
        </div>
      )}

      {/* Lista de productos o mensaje de vacio */}
      {!loading && !error && (
        items.length === 0 ? (
          <p className='text-muted'>No se encontraron productos con los filtros aplicados.</p>
        ) : (
          <div className='row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-3'>
            {items.map(producto => (
              <div className='col' key={producto.id}>
                <ProductCard producto={producto} />
              </div>
            ))}
          </div>
        )
      )}

      <div className='mt-4'>
        <Pagination
          page={page}
          pageSize={PAGE_SIZE}
          total={total}
          onChange={handlePageChange}
        />
      </div>

    </section>
  );
}
