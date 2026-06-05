import { useState, useEffect } from "react";
import { useParams } from 'react-router-dom';
import { VENTAS_MOCK, USUARIOS_MOCK } from "../services/mockData";

const USUARIO_ACTUAL_ID = 1;

export default function MisCompras() {
  const [compras, setCompras] = useState([]);
  var { id } = useParams();
  
  // Cargamos la lista solo cuando cargamos la vista
  useEffect(() => {
    async function getUserCompras() {
      const url = `http://localhost:8090/compraventas/compras/${id}/?page=0&size=10`;
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
        setCompras(body);
      } catch (error){
        console.error(`Error al obtener las compras del usuario con ID ${id}:`, error);
      }
    }
    getUserCompras();
  }, []);

  return (
    <section className="container py-4">
      {/* Cabecera */}
      <div className="row align-items-center mb-4">
        <div className="col">
          <h2>Mis compras</h2>
        </div>
        <div className="col-auto">
          <span className="badge bg-primary fs-6">
            {compras.length} compras
          </span>
        </div>
      </div>

      {/* Tabla */}
      <div className="table-responsive">
        <table className="table table-hover table-striped text-center">
          <thead className="table-dark">
            <tr>
              <th scope="col">Producto</th>
              <th scope="col" className="col-ocultar">
                Vendedor
              </th>
              <th scope="col">Precio</th>
              <th scope="col" className="col-ocultar">
                Fecha
              </th>
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
              compras.map((compra) => {
                // Buscamos el nombre del vendedor en el mock de usuarios
                const vendedor = USUARIOS_MOCK.find(
                  (u) => u.id === compra.vendedor_id,
                );
                const fecha = new Date(
                  compra.fecha + "T00:00:00",
                ).toLocaleDateString("es-ES", {
                  day: "2-digit",
                  month: "short",
                  year: "numeric",
                });

                return (
                  <tr key={compra.id}>
                    <td>{compra.producto}</td>
                    <td className="col-ocultar">
                      {vendedor.nombre + " " + vendedor.apellidos}
                    </td>
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
