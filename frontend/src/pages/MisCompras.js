import { useState, useEffect } from 'react';
import { VENTAS_MOCK, USUARIOS_MOCK } from '../services/mockData';

const USUARIO_ACTUAL_ID = 1;

export default function MisCompras() {
  const [compras, setCompras] = useState([]);

  useEffect(() => {
    const misCompras = VENTAS_MOCK.filter(v => v.comprador_id === USUARIO_ACTUAL_ID);
    setCompras(misCompras);
  }, []);

  return (
    <section className="container py-4">

      {/* Cabecera */}
      <div className="row align-items-center mb-4">
        <div className="col">
          <h2>Mis compras</h2>
        </div>
        <div className="col-auto">
          <span className="badge bg-primary fs-6">{compras.length} compras</span>
        </div>
      </div>

      {/* Tabla */}
      <div className="table-responsive">
        <table className="table table-hover table-striped text-center">
          <thead className="table-dark">
            <tr>
              <th scope="col">Producto</th>
              <th scope="col" className="col-ocultar">Vendedor</th>
              <th scope="col">Precio</th>
              <th scope="col" className="col-ocultar">Fecha</th>
            </tr>
          </thead>
          <tbody>
            {compras.length === 0 ? (
              <tr>
                <td colSpan={2} className="text-center text-muted py-4">
                  No has realizado ninguna compra todavía
                </td>
              </tr>
            ) : (
              compras.map(compra => {
                // Buscamos el nombre del vendedor en el mock de usuarios
                const vendedor = USUARIOS_MOCK.find(u => u.id === compra.vendedor_id);
                const fecha = new Date(compra.fecha + 'T00:00:00').toLocaleDateString('es-ES', {
                  day: '2-digit', month: 'short', year: 'numeric',
                });

                return (
                  <tr key={compra.id}>
                    <td>{compra.producto}</td>
                    <td className="col-ocultar">{vendedor.nombre + ' ' + vendedor.apellidos}</td>
                    <td>{compra.precio} €</td>
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