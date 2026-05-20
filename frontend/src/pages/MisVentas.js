import { useState, useEffect } from 'react';
import { VENTAS_MOCK, USUARIOS_MOCK } from '../services/mockData';

const USUARIO_ACTUAL_ID = 1;

export default function MisVentas() {
  const [ventas, setVentas] = useState([]);

  useEffect(() => {
    const misVentas = VENTAS_MOCK.filter(v => v.vendedor_id === USUARIO_ACTUAL_ID);
    setVentas(misVentas);
  }, []);

  return (
    <section className="container py-4">

      {/* Cabecera */}
      <div className="row align-items-center mb-4">
        <div className="col">
          <h2>Mis ventas</h2>
        </div>
        <div className="col-auto">
          <span className="badge bg-primary fs-6">{ventas.length} ventas</span>
        </div>
      </div>

      {/* Tabla */}
      <div className="table-responsive">
        <table className="table table-hover table-striped text-center">
          <thead className="table-dark">
            <tr>
              <th scope="col">Producto</th>
              <th scope="col" className="col-ocultar">Comprador</th>
              <th scope="col">Precio</th>
              <th scope="col" className="col-ocultar">Fecha</th>
            </tr>
          </thead>
          <tbody>
            {ventas.length === 0 ? (
              <tr>
                <td colSpan={2} className="text-center text-muted py-4">
                  No has realizado ninguna venta todavía
                </td>
              </tr>
            ) : (
              ventas.map(venta => {
                // Buscamos el nombre del comprador en el mock de usuarios
                const comprador = USUARIOS_MOCK.find(u => u.id === venta.comprador_id);
                const fecha = new Date(venta.fecha + 'T00:00:00').toLocaleDateString('es-ES', {
                  day: '2-digit', month: 'short', year: 'numeric',
                });

                return (
                  <tr key={venta.id}>
                    <td>{venta.producto}</td>
                    <td className="col-ocultar">{comprador.nombre + ' ' + comprador.apellidos}</td>
                    <td>{venta.precio} €</td>
                    <td className="col-ocultar">{fecha}</td>
                  </tr>
                );
              })
            )}
          </tbody>
        </table>
      </div>

    </section>
  );
}