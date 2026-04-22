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

// Colores de badge segun el estado del producto
const ESTADO_COLORS = {
  nuevo: 'success',
  como_nuevo: 'primary',
  buen_estado: 'info',
  aceptable: 'warning',
  para_piezas_o_reparar: 'danger',
};

export default function ProductCard({ producto }) {
  const navigate = useNavigate();

  const categoria = CATEGORIAS.find(c => c.id === producto.categoria_id);
  const badgeColor = ESTADO_COLORS[producto.estado] ?? 'secondary';
  const estadoLabel = ESTADO_LABELS[producto.estado] ?? producto.estado;

  function handleClick() {
    navigate(`/productos/${producto.id}`);
  }

  return (
    <article className='card product-card h-100 shadow-sm' onClick={handleClick}>
      <div className='product-card__img-wrapper'>
        <img
          src={producto.imagen}
          alt={producto.titulo}
          className='card-img-top product-card__img'
        />
        <span className={`badge bg-${badgeColor} product-card__badge`}>
          {estadoLabel}
        </span>
      </div>

      <div className='card-body d-flex flex-column'>
        <h2 className='card-title product-card__title'>{producto.titulo}</h2>

        {categoria && (
          <p className='product-card__categoria text-muted small mb-1'>
            {categoria.nombre}
          </p>
        )}

        <p className='product-card__precio mt-auto'>
          {producto.precio.toLocaleString('es-ES', {
            style: 'currency',
            currency: 'EUR',
          })}
        </p>

        {producto.envio_disponible && (
          <span className='product-card__envio text-success small'>
            &#10003; Envío disponible
          </span>
        )}
      </div>
    </article>
  );
}
