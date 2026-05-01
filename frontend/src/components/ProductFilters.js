import { useState, useEffect } from 'react';
import { listCategories } from '../services/categoryService';
import '../styles/ProductFilters.css';

const ESTADOS = [
  { value: 'nuevo', label: 'Nuevo' },
  { value: 'como_nuevo', label: 'Como nuevo' },
  { value: 'buen_estado', label: 'Buen estado' },
  { value: 'aceptable', label: 'Aceptable' },
  { value: 'para_piezas_o_reparar', label: 'Para piezas o reparar' },
];

const ORDEN_OPTIONS = [
  { value: 'fecha_publicacion:desc', label: 'Más recientes primero' },
  { value: 'fecha_publicacion:asc', label: 'Más antiguos primero' },
  { value: 'precio:asc', label: 'Precio: menor a mayor' },
  { value: 'precio:desc', label: 'Precio: mayor a menor' },
];

// Componente de filtros; recibe los valores actuales y notifica cambios al padre
export default function ProductFilters({ filters, onChange }) {
  const [local, setLocal] = useState(filters);
  const [categorias, setCategorias] = useState([]);

  // Carga las categorias disponibles al montar
  useEffect(() => {
    listCategories().then(setCategorias).catch(() => setCategorias([]));
  }, []);

  // Sincroniza el estado local cuando el padre resetea los filtros
  useEffect(() => {
    setLocal(filters);
  }, [filters]);

  function handleChange(e) {
    const { name, value } = e.target;
    setLocal(prev => ({ ...prev, [name]: value }));
  }

  function handleApply(e) {
    e.preventDefault();
    onChange(local);
  }

  function handleClear() {
    const empty = { descripcion: '', categoria: '', estado: '', precioMax: '', sort: 'fecha_publicacion:desc' };
    setLocal(empty);
    onChange(empty);
  }

  const formContent = (
    <form onSubmit={handleApply}>
      <div className='row g-2'>

        {/* Búsqueda por texto */}
        <div className='col-12 col-md-6 col-lg-3'>
          <label htmlFor='filtro-descripcion' className='form-label'>Descripción</label>
          <input
            type='text'
            id='filtro-descripcion'
            name='descripcion'
            className='form-control form-control-sm'
            placeholder='Buscar...'
            value={local.descripcion}
            onChange={handleChange}
          />
        </div>

        {/* Filtro por categoría */}
        <div className='col-12 col-md-6 col-lg-3'>
          <label htmlFor='filtro-categoria' className='form-label'>Categoría</label>
          <select
            id='filtro-categoria'
            name='categoria'
            className='form-select form-select-sm'
            value={local.categoria}
            onChange={handleChange}
          >
            <option value=''>Todas</option>
            {categorias.map(c => (
              <option key={c.id} value={c.id}>{c.nombre}</option>
            ))}
          </select>
        </div>

        {/* Filtro por estado */}
        <div className='col-12 col-md-6 col-lg-3'>
          <label htmlFor='filtro-estado' className='form-label'>Estado</label>
          <select
            id='filtro-estado'
            name='estado'
            className='form-select form-select-sm'
            value={local.estado}
            onChange={handleChange}
          >
            <option value=''>Todos</option>
            {ESTADOS.map(e => (
              <option key={e.value} value={e.value}>{e.label}</option>
            ))}
          </select>
        </div>

        {/* Filtro por precio máximo */}
        <div className='col-12 col-md-6 col-lg-3'>
          <label htmlFor='filtro-precio' className='form-label'>Precio máximo (€)</label>
          <input
            type='number'
            id='filtro-precio'
            name='precioMax'
            className='form-control form-control-sm'
            placeholder='Sin límite'
            min='0'
            value={local.precioMax}
            onChange={handleChange}
          />
        </div>

        {/* Ordenación */}
        <div className='col-12 col-md-6 col-lg-3'>
          <label htmlFor='filtro-sort' className='form-label'>Ordenar por</label>
          <select
            id='filtro-sort'
            name='sort'
            className='form-select form-select-sm'
            value={local.sort}
            onChange={handleChange}
          >
            {ORDEN_OPTIONS.map(o => (
              <option key={o.value} value={o.value}>{o.label}</option>
            ))}
          </select>
        </div>

        {/* Botones de acción */}
        <div className='col-12 d-flex gap-2 align-items-end'>
          <button type='submit' className='btn btn-primary btn-sm'>Aplicar</button>
          <button type='button' className='btn btn-outline-secondary btn-sm' onClick={handleClear}>
            Limpiar
          </button>
        </div>

      </div>
    </form>
  );

  return (
    <div className='product-filters mb-4'>
      {/* En móvil se colapsa dentro de un <details> */}
      <details className='product-filters__collapse d-md-none'>
        <summary className='product-filters__summary'>Filtros y ordenación</summary>
        <div className='pt-3'>{formContent}</div>
      </details>

      {/* En tablet y escritorio se muestra siempre */}
      <div className='d-none d-md-block'>{formContent}</div>
    </div>
  );
}
