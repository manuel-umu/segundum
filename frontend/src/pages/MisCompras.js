import { useState, useEffect } from "react";

const usuario = JSON.parse(localStorage.getItem("usuario"));

export default function MisCompras() {
  const [compras, setCompras] = useState([]);
  // Cargamos la lista solo cuando cargamos la vista
  useEffect(() => {
    async function getUserCompras() {
      const url = `/compraventas/compras/${usuario.id}/?page=0&size=10`;
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
        // Extraemos la lista del formato HATEOAS
        const lista = body._embedded?.compraventaOutputDTOList ?? [];
        setCompras(lista);
      } catch (error){
        console.error(`Error al obtener las compras del usuario con ID ${usuario.id}:`, error);
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

      {/* Mensaje vacío */}
      {compras.length === 0 && (
        <p className="text-center text-muted py-4">
          No has puesto a la venta ningún producto todavía
        </p>
      )}

      {/* Tabla */}
      {compras.length > 0 && (
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
                  <td className="text-center text-muted py-4">
                    No has realizado ninguna compra todavía
                  </td>
                </tr>
              ) : (
                compras.map((compra) => {
                  const fecha = new Date(compra.fecha).toLocaleDateString('es-ES', {
                    day: '2-digit', month: 'short', year: 'numeric',
                  });

                  return (
                    <tr key={compra.idProducto}>
                      <td>{compra.titulo}</td>
                      <td className="col-oculta">{compra.nombreVendedor}</td>
                      <td>{compra.precio} €</td>
                      <td className="col-oculta">{fecha}</td>
                    </tr>
                  );
                })
              )}
            </tbody>
          </table>
        </div>
      )}
    </section>
  );
}
