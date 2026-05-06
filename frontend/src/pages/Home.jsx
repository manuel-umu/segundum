import { Link } from 'react-router-dom';
import { PRODUCTOS, CATEGORIAS } from '../services/mockData';
import '../styles/Home.css';

// Los 4 productos con mas visualizaciones van al carrusel de Bootstrap
const CAROUSEL_ITEMS = [...PRODUCTOS]
  .sort((a, b) => b.num_visualizaciones - a.num_visualizaciones)
  .slice(0, 4);

// Los 8 productos mas recientes son los "destacados"
const DESTACADOS = [...PRODUCTOS]
  .sort((a, b) => new Date(b.fecha_publicacion) - new Date(a.fecha_publicacion))
  .slice(0, 8);

export default function Home() {
  return (
    <div>

      {/* Carrusel de Bootstrap */}
      <div id='carruselHome' className='carousel slide' data-bs-ride='carousel'>
        <div className='carousel-inner'>
          {CAROUSEL_ITEMS.map((producto, index) => (
            <div className={`carousel-item ${index === 0 ? 'active' : ''}`} key={producto.id}>
              <img
                src={producto.imagen}
                className='d-block w-100 home__carousel-img'
                alt={producto.titulo}
              />
              <div className='carousel-caption d-none d-md-block'>
                <h2>{producto.titulo}</h2>
                <p>
                  {producto.precio.toLocaleString('es-ES', { style: 'currency', currency: 'EUR' })}
                </p>
                <Link to={`/productos/${producto.id}`} className='btn btn-light'>
                  Ver producto
                </Link>
              </div>
            </div>
          ))}
        </div>
        <button className='carousel-control-prev' type='button' data-bs-target='#carruselHome' data-bs-slide='prev'>
          <span className='carousel-control-prev-icon' aria-hidden='true'></span>
          <span className='visually-hidden'>Anterior</span>
        </button>
        <button className='carousel-control-next' type='button' data-bs-target='#carruselHome' data-bs-slide='next'>
          <span className='carousel-control-next-icon' aria-hidden='true'></span>
          <span className='visually-hidden'>Siguiente</span>
        </button>
      </div>

      <div className='container py-4'>

        {/* Categorias */}
        <section className='mb-5'>
          <h2 className='mb-3'>Explorar por categoría</h2>
          <div className='home__categories-grid'>
            {CATEGORIAS.map(cat => (
              <Link
                key={cat.id}
                to={`/productos?categoria=${cat.id}`}
                className='btn btn-outline-secondary'
              >
                {cat.nombre}
              </Link>
            ))}
          </div>
        </section>

        {/* Ultimas publicaciones */}
        <section>
          <div className='d-flex justify-content-between align-items-center mb-3'>
            <h2>Últimas publicaciones</h2>
            <Link to='/productos'>Ver todos</Link>
          </div>

          {/* CSS Grid con media queries */}
          <div className='home__destacados-grid'>
            {DESTACADOS.map(producto => {
              const categoria = CATEGORIAS.find(c => c.id === producto.categoria_id);
              return (
                <article key={producto.id} className='card home__destacado-card'>
                  <img
                    src={producto.imagen}
                    alt={producto.titulo}
                    className='card-img-top home__destacado-img'
                  />
                  <div className='card-body'>
                    {categoria && (
                      <p className='text-muted small mb-1'>{categoria.nombre}</p>
                    )}
                    <h3 className='card-title h6'>{producto.titulo}</h3>
                    <p className='fw-bold mb-2'>
                      {producto.precio.toLocaleString('es-ES', { style: 'currency', currency: 'EUR' })}
                    </p>
                    <Link to={`/productos/${producto.id}`} className='btn btn-primary btn-sm'>
                      Ver producto
                    </Link>
                  </div>
                </article>
              );
            })}
          </div>
        </section>

      </div>
    </div>
  );
}
