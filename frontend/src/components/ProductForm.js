import { useState, useEffect } from 'react';
import { listCategories } from '../services/categoryService';
import '../styles/ProductForm.css';

const ESTADOS = [
  { value: 'nuevo', label: 'Nuevo' },
  { value: 'como_nuevo', label: 'Como nuevo' },
  { value: 'buen_estado', label: 'Buen estado' },
  { value: 'aceptable', label: 'Aceptable' },
  { value: 'para_piezas_o_reparar', label: 'Para piezas o reparar' },
];

const INITIAL_VALUES = {
  titulo: '',
  descripcion: '',
  precio: '',
  estado: 'nuevo',
  categoria_id: '',
  envio_disponible: false,
  lugar_recogida: '',
};

// Valida cada campo y devuelve un objeto con los errores encontrados
function validate(values) {
  const errors = {};
  if (!values.titulo.trim()) {
    errors.titulo = 'El titulo es obligatorio.';
  } else if (values.titulo.length > 100) {
    errors.titulo = 'El titulo no puede superar los 100 caracteres.';
  }
  if (!values.descripcion.trim()) {
    errors.descripcion = 'La descripcion es obligatoria.';
  }
  if (!values.precio) {
    errors.precio = 'El precio es obligatorio.';
  } else if (Number(values.precio) <= 0) {
    errors.precio = 'El precio debe ser mayor que 0.';
  }
  if (!values.estado) {
    errors.estado = 'Selecciona un estado.';
  }
  if (!values.categoria_id) {
    errors.categoria_id = 'Selecciona una categoria.';
  }
  if (!values.lugar_recogida.trim()) {
    errors.lugar_recogida = 'El lugar de recogida es obligatorio.';
  }
  return errors;
}

// Formulario reutilizable tanto para crear como para editar un producto
export default function ProductForm({ initialValues = {}, onSubmit, onCancel, submitLabel = 'Guardar' }) {
  const [values, setValues] = useState({ ...INITIAL_VALUES, ...initialValues });
  const [errors, setErrors] = useState({});
  const [categorias, setCategorias] = useState([]);
  const [submitting, setSubmitting] = useState(false);
  const [serverError, setServerError] = useState(null);

  // Carga las categorias disponibles al montar el formulario
  useEffect(() => {
    listCategories().then(setCategorias).catch(() => setCategorias([]));
  }, []);

  // Sincroniza los valores iniciales cuando llegan (caso edicion)
  useEffect(() => {
    if (Object.keys(initialValues).length > 0) {
      setValues(prev => ({ ...prev, ...initialValues }));
    }
  // Solo cuando cambian los valores iniciales (id del producto)
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [initialValues.id]);

  function handleChange(e) {
    const { name, value, type, checked } = e.target;
    setValues(prev => ({ ...prev, [name]: type === 'checkbox' ? checked : value }));
    // Limpia el error del campo cuando el usuario lo corrige
    setErrors(prev => ({ ...prev, [name]: undefined }));
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setServerError(null);

    const validationErrors = validate(values);
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }

    setSubmitting(true);
    try {
      await onSubmit({
        ...values,
        precio: Number(values.precio),
        categoria_id: Number(values.categoria_id),
      });
    } catch (err) {
      setServerError(err.message ?? 'Error al guardar el producto.');
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <form className='product-form' onSubmit={handleSubmit} noValidate>

      {serverError && (
        <div className='alert alert-danger' role='alert'>{serverError}</div>
      )}

      {/* Titulo */}
      <div className='mb-3'>
        <label htmlFor='titulo' className='form-label'>Título <span className='text-danger'>*</span></label>
        <input
          type='text'
          id='titulo'
          name='titulo'
          className={`form-control ${errors.titulo ? 'is-invalid' : ''}`}
          value={values.titulo}
          onChange={handleChange}
          maxLength={100}
          required
          autoFocus
        />
        {errors.titulo && <span className='invalid-feedback'>{errors.titulo}</span>}
        <small className='text-muted'>{values.titulo.length}/100</small>
      </div>

      {/* Descripcion */}
      <div className='mb-3'>
        <label htmlFor='descripcion' className='form-label'>Descripción <span className='text-danger'>*</span></label>
        <textarea
          id='descripcion'
          name='descripcion'
          className={`form-control ${errors.descripcion ? 'is-invalid' : ''}`}
          rows={4}
          value={values.descripcion}
          onChange={handleChange}
          required
        />
        {errors.descripcion && <span className='invalid-feedback'>{errors.descripcion}</span>}
      </div>

      {/* Precio y estado en la misma fila en pantallas medianas */}
      <div className='row'>
        <div className='col-12 col-md-6 mb-3'>
          <label htmlFor='precio' className='form-label'>Precio (€) <span className='text-danger'>*</span></label>
          <input
            type='number'
            id='precio'
            name='precio'
            className={`form-control ${errors.precio ? 'is-invalid' : ''}`}
            value={values.precio}
            onChange={handleChange}
            min='0.01'
            step='0.01'
            required
          />
          {errors.precio && <span className='invalid-feedback'>{errors.precio}</span>}
        </div>

        <div className='col-12 col-md-6 mb-3'>
          <label htmlFor='estado' className='form-label'>Estado <span className='text-danger'>*</span></label>
          <select
            id='estado'
            name='estado'
            className={`form-select ${errors.estado ? 'is-invalid' : ''}`}
            value={values.estado}
            onChange={handleChange}
            required
          >
            <option value=''>Selecciona un estado</option>
            {ESTADOS.map(e => (
              <option key={e.value} value={e.value}>{e.label}</option>
            ))}
          </select>
          {errors.estado && <span className='invalid-feedback'>{errors.estado}</span>}
        </div>
      </div>

      {/* Categoria */}
      <div className='mb-3'>
        <label htmlFor='categoria_id' className='form-label'>Categoría <span className='text-danger'>*</span></label>
        <select
          id='categoria_id'
          name='categoria_id'
          className={`form-select ${errors.categoria_id ? 'is-invalid' : ''}`}
          value={values.categoria_id}
          onChange={handleChange}
          required
        >
          <option value=''>Selecciona una categoría</option>
          {categorias.map(c => (
            <option key={c.id} value={c.id}>{c.nombre}</option>
          ))}
        </select>
        {errors.categoria_id && <span className='invalid-feedback'>{errors.categoria_id}</span>}
      </div>

      {/* Lugar de recogida y envio en la misma fila */}
      <div className='row'>
        <div className='col-12 col-md-8 mb-3'>
          <label htmlFor='lugar_recogida' className='form-label'>Lugar de recogida <span className='text-danger'>*</span></label>
          <input
            type='text'
            id='lugar_recogida'
            name='lugar_recogida'
            className={`form-control ${errors.lugar_recogida ? 'is-invalid' : ''}`}
            value={values.lugar_recogida}
            onChange={handleChange}
            required
          />
          {errors.lugar_recogida && <span className='invalid-feedback'>{errors.lugar_recogida}</span>}
        </div>

        <div className='col-12 col-md-4 mb-3 d-flex align-items-end'>
          <div className='form-check'>
            <input
              type='checkbox'
              id='envio_disponible'
              name='envio_disponible'
              className='form-check-input'
              checked={values.envio_disponible}
              onChange={handleChange}
            />
            <label htmlFor='envio_disponible' className='form-check-label'>
              Envío disponible
            </label>
          </div>
        </div>
      </div>

      <div className='d-flex gap-2'>
        <button
          type='submit'
          className='btn btn-primary'
          disabled={submitting}
        >
          {submitting ? 'Guardando...' : submitLabel}
        </button>

        {onCancel && (
          <button
            type='button'
            className='btn btn-outline-secondary'
            onClick={onCancel}
            disabled={submitting}
          >
            Cancelar
          </button>
        )}
      </div>

    </form>
  );
}
