import { useState, useEffect } from "react";
import { VENTAS_MOCK } from '../services/mockData';

export default function AdminCompraventas() {

  const [compraventas, setCompraventas] = useState([]);

  // Cargamos la lista solo cuando cargamos la vista
  useEffect(() => {
    setCompraventas(VENTAS_MOCK);
  }, []);

  return (
    <section className="container py-4">

      {/* Cabecera */}
      <div className="row align-items-center mb-4">
        <div className="col">
          <h2>Compraventas entre usuarios</h2>
        </div>
        <div className="col-auto">
          <span className="badge bg-primary fs-6">{compraventas.length} compraventas</span>
        </div>
      </div>

      {/* Tabla */}
      <div className="table-responsive">
        <table className="table table-hover table-striped text-center">
          <thead className="table-dark">
            <tr>
              <th scope='col' style={{ width: '30%' }}>Producto</th>
              <th scope='col' style={{ width: '10%' }}>Precio</th>
              <th scope='col' className="col-ocultar" style={{ width: '20%' }}>Comprador</th>
              <th scope='col' className="col-ocultar" style={{ width: '20%' }}>Vendedor</th>
              <th scope='col' style={{ width: '20%' }}>Fecha</th>
            </tr>
          </thead>
          <tbody>
            {compraventas.length === 0 ? (
              <tr>
                <td colSpan={5} className="text-muted py-4"> No hay compraventas registradas.</td>
              </tr>
            ) : (
              compraventas.map(compraventa => {
                const fecha = new Date(compraventa.fecha + 'T00:00:00').toLocaleDateString('es-ES', {
                  day: '2-digit', month: 'short', year: 'numeric',
                });

                return (
                  <tr key={compraventa.id}>
                    <td>{compraventa.producto}</td>
                    <td>{compraventa.precio}</td>
                    <td className="col-ocultar">{compraventa.comprador}</td>
                    <td className="col-ocultar">{compraventa.vendedor}</td>
                    <td>{fecha}</td>
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
