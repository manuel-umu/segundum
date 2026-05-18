import { useEffect, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { PRODUCTOS, CATEGORIAS } from '../services/mockData';
import '../styles/Home.css';

// Los 4 productos con más visualizaciones van al carrusel
const CAROUSEL_ITEMS = [...PRODUCTOS]
  .sort((a, b) => b.num_visualizaciones - a.num_visualizaciones)
  .slice(0, 4);

// Los 8 productos más recientes son los "destacados"
const DESTACADOS = [...PRODUCTOS]
  .sort((a, b) => new Date(b.fecha_publicacion) - new Date(a.fecha_publicacion))
  .slice(0, 8);

// Icono SVG sencillo para cada categoría
const CATEGORIA_ICONOS = {
  'Electrónica':      '📱',
  'Ropa y Moda':      '👕',
  'Hogar y Jardín':   '🏡',
  'Deportes':         '⚽',
  'Libros y Música':  '📚',
  'Juguetes y Bebé':  '🧸',
  'Vehículos':        '🚗',
  'Informática':      '💻',
};

export default function Home() {
  const navigate = useNavigate();
  const [activeIndex, setActiveIndex] = useState(0);

  // Avance automático del carrusel cada 4 segundos
  useEffect(() => {
    const timer = setInterval(() => {
      setActiveIndex(i => (i + 1) % CAROUSEL_ITEMS.length);
    }, 4000);
    return () => clearInterval(timer);  // limpieza al desmontar
  }, []);

  function goTo(index) {
    setActiveIndex(index);
  }

  function prev() {
    setActiveIndex(i => (i - 1 + CAROUSEL_ITEMS.length) % CAROUSEL_ITEMS.length);
  }

  function next() {
    setActiveIndex(i => (i + 1) % CAROUSEL_ITEMS.length);
  }

  return (
    <div className="home">

      {/* Parte 1: Carrusel */}
      <section className="home__carousel-section" aria-label="Productos más vistos">
        <div className="home__carousel">

          {/* Diapositivas  */}
          <div className="home__carousel-track">
            {CAROUSEL_ITEMS.map((producto, index) => {
              const categoria = CATEGORIAS.find(c => c.id === producto.categoria_id);
              return (
                <div
                  key={producto.id}
                  className={`home__carousel-slide ${index === activeIndex ? 'home__carousel-slide--active' : ''}`}
                  aria-hidden={index !== activeIndex}
                >
                  <img
                    src={producto.imagen}
                    alt={producto.titulo}
                    className="home__carousel-img"
                  />
                  {/* Capa oscura sobre la imagen */}
                  <div className="home__carousel-overlay" />
                  <div className="home__carousel-caption">
                    {categoria && (
                      <span className="home__carousel-badge">{categoria.nombre}</span>
                    )}
                    <h2 className="home__carousel-title">{producto.titulo}</h2>
                    <p className="home__carousel-price">
                      {producto.precio.toLocaleString('es-ES', { style: 'currency', currency: 'EUR' })}
                    </p>
                    <button
                      className="btn btn-light"
                      onClick={() => navigate(`/productos/${producto.id}`)}
                    >
                      Ver producto
                    </button>
                  </div>
                </div>
              );
            })}
          </div>

          {/* Controles anterior / siguiente */}
          <button className="home__carousel-control home__carousel-control--prev" onClick={prev} aria-label="Anterior">
            &#8249;
          </button>
          <button className="home__carousel-control home__carousel-control--next" onClick={next} aria-label="Siguiente">
            &#8250;
          </button>

          {/* Indicadores (puntos) */}
          <div className="home__carousel-indicators">
            {CAROUSEL_ITEMS.map((_, index) => (
              <button
                key={index}
                className={`home__carousel-dot ${index === activeIndex ? 'home__carousel-dot--active' : ''}`}
                onClick={() => goTo(index)}
                aria-label={`Ir a diapositiva ${index + 1}`}
              />
            ))}
          </div>

        </div>
      </section>

      {/* Parte 2: Categorías */}
      <section className="home__section container">
        <h2 className="home__section-title">Explorar por categoría</h2>

        {/* CSS Grid: responsivo con auto-fill */}
        <div className="home__categories-grid">
          {CATEGORIAS.map(cat => (
            <Link
              key={cat.id}
              to={`/productos?categoria=${cat.id}`}
              className="home__category-card"
            >
              <span className="home__category-icon" aria-hidden="true">
                {CATEGORIA_ICONOS[cat.nombre] ?? '🏷️'}
              </span>
              <span className="home__category-name">{cat.nombre}</span>
            </Link>
          ))}
        </div>
      </section>

      {/* Parte 3: Productos destacados */}
      <section className="home__section container">
        <div className="home__section-header">
          <h2 className="home__section-title">Últimas publicaciones</h2>
          <Link to="/productos" className="home__section-link">Ver todos →</Link>
        </div>

        {/* CSS Grid con columnas responsivas mediante media queries */}
        <div className="home__destacados-grid">
          {DESTACADOS.map(producto => {
            const categoria = CATEGORIAS.find(c => c.id === producto.categoria_id);
            return (
              <article
                key={producto.id}
                className="home__destacado-card"
                onClick={() => navigate(`/productos/${producto.id}`)}
              >
                <div className="home__destacado-img-wrapper">
                  <img
                    src={producto.imagen}
                    alt={producto.titulo}
                    className="home__destacado-img"
                  />
                  {producto.envio_disponible && (
                    <span className="home__destacado-envio">Envío</span>
                  )}
                </div>
                <div className="home__destacado-body">
                  {categoria && (
                    <p className="home__destacado-categoria">{categoria.nombre}</p>
                  )}
                  <h3 className="home__destacado-titulo">{producto.titulo}</h3>
                  <p className="home__destacado-precio">
                    {producto.precio.toLocaleString('es-ES', { style: 'currency', currency: 'EUR' })}
                  </p>
                </div>
              </article>
            );
          })}
        </div>
      </section>

    </div>
  );
}