import { useParams, useNavigate, Link } from 'react-router-dom';
import useAuth from '../hooks/useAuth';
import useFetch from '../hooks/useFetch';
import ProductForm from '../components/ProductForm';
import { getProduct, updateProduct } from '../services/productService';

export default function ProductEdit() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user, isAdmin } = useAuth();

  const { data: producto, loading, error } = useFetch(
    () => getProduct(id),
    [id]
  );

  // Spinner mientras carga el producto
  if (loading) {
    return (
      <div className='d-flex justify-content-center align-items-center' style={{ minHeight: '60vh' }}>
        <div className='spinner-border text-primary' role='status'>
          <span className='visually-hidden'>Cargando...</span>
        </div>
      </div>
    );
  }

  // Error de carga o producto no encontrado
  if (error || !producto) {
    return (
      <div className='container py-4'>
        <div className='alert alert-danger' role='alert'>
          {error ?? 'Producto no encontrado.'}
        </div>
        <Link to='/productos' className='btn btn-secondary'>Volver</Link>
      </div>
    );
  }

  // Solo el vendedor o el admin pueden editar
  if (user.id !== producto.vendedor_id && !isAdmin) {
    return (
      <div className='container py-4'>
        <div className='alert alert-warning' role='alert'>
          No tienes permiso para editar este producto.
        </div>
        <Link to={`/productos/${id}`} className='btn btn-secondary'>Ver producto</Link>
      </div>
    );
  }

  // Actualiza el producto y redirige al detalle
  async function handleSubmit(values) {
    await updateProduct(Number(id), values);
    navigate(`/productos/${id}`);
  }

  return (
    <section className='container py-4'>
      <h1 className='mb-4'>Editar producto</h1>
      <ProductForm
        initialValues={producto}
        onSubmit={handleSubmit}
        onCancel={() => navigate(`/productos/${id}`)}
        submitLabel='Guardar cambios'
      />
    </section>
  );
}
