import { useNavigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth';
import ProductForm from '../components/ProductForm';
import { createProduct } from '../services/productService';

export default function ProductNew() {
  const navigate = useNavigate();
  const { user } = useAuth();

  // Función que crea el producto y redirige al detalle del nuevo producto
  async function handleSubmit(values) {
    const nuevo = await createProduct({ ...values, vendedor_id: user.id });
    navigate(`/productos/${nuevo.id}`);
  }

  return (
    <section className='container py-4'>
      <h1 className='mb-4'>Publicar producto</h1>
      <ProductForm
        onSubmit={handleSubmit}
        onCancel={() => navigate('/productos')}
        submitLabel='Publicar'
      />
    </section>
  );
}
