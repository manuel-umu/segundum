import { useNavigate } from 'react-router-dom';
import { CATEGORIAS } from '../services/mockData';
import '../styles/ProductCard.css';

// Etiquetas legibles para cada valor del enum de estado
const ESTADO_LABELS = {
  nuevo: 'Nuevo',
  como_nuevo: 'Como nuevo',
  buen_estado: 'Buen estado',
  aceptable: 'Aceptable',
  para_piezas_o_reparar: 'Para piezas',
};

export default function ProductCard({ producto }) {
  const navigate = useNavigate();

  const categoria = CATEGORIAS.find(c => c.id === producto.categoria_id);
  const estadoLabel = ESTADO_LABELS[producto.estado] ?? producto.estado;

  function handleClick() {
    navigate(`/productos/${producto.id}`);
  }

  return (
    <article className='card product-card h-100' onClick={handleClick}>
      <img
        src={producto.imagen}
        alt={producto.titulo}
        className='card-img-top product-card__img'
      />
      <div className='card-body'>
        <span className='badge bg-secondary mb-1'>{estadoLabel}</span>
        <h2 className='card-title product-card__title'>{producto.titulo}</h2>
        {categoria && (
          <p className='text-muted small mb-1'>{categoria.nombre}</p>
        )}
        <p className='fw-bold mb-1'>
          {producto.precio.toLocaleString('es-ES', { style: 'currency', currency: 'EUR' })}
        </p>
        {producto.envio_disponible && (
          <small className='text-success'>&#10003; Envío disponible</small>
        )}
      </div>
    </article>
  );
}
