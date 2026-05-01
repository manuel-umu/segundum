import { Link } from 'react-router-dom';

// Página no encontrada
export default function NotFound() {
  return (
    <section className="text-center py-5">
      <h1>404</h1>
      <p className="lead">Página no encontrada.</p>
      <Link className="btn btn-dark" to="/">Volver al inicio</Link>
    </section>
  );
}
