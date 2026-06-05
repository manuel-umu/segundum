import { useState, useEffect } from "react";
import { useParams } from 'react-router-dom';
import { VENTAS_MOCK, USUARIOS_MOCK } from "../services/mockData";

const USUARIO_ACTUAL_ID = 1;

export default function MisVentas() {
  const [ventas, setVentas] = useState([]);
  const { id } = useParams();

  // Cargamos la lista solo cuando cargamos la vista
  useEffect(() => {
    async function getUserVentas() {
      const url = `http://localhost:8090/compraventas/ventas/${id}/?page=0&size=10`;
      try {
        const response = await fetch(url, {
          headers: {
            // 'Authorization': `Bearer ${cookies.get('token')}`
          }
        });
        const body = await response.json();
        if(response.status === 404){
          // TODO cuando tengamos handlebars
        } else if(response.status !== 200){
          throw Error(body.message)
        }
        setVentas(body);
      } catch (error){
        console.error(`Error al obtener las ventas del usuario con ID ${id}:`, error);
      }
    }

    getUserVentas();
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
              <th scope="col" className="col-ocultar">
                Comprador
              </th>
              <th scope="col">Precio</th>
              <th scope="col" className="col-ocultar">
                Fecha
              </th>
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
              ventas.map((venta) => {
                // Buscamos el nombre del comprador en el mock de usuarios
                const comprador = USUARIOS_MOCK.find(
                  (u) => u.id === venta.comprador_id,
                );
                const fecha = new Date(
                  venta.fecha + "T00:00:00",
                ).toLocaleDateString("es-ES", {
                  day: "2-digit",
                  month: "short",
                  year: "numeric",
                });

                return (
                  <tr key={venta.id}>
                    <td>{venta.producto}</td>
                    <td className="col-ocultar">
                      {comprador.nombre + " " + comprador.apellidos}
                    </td>
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
