import { useState, useEffect } from "react";

export default function AdminCompraventas() {
  const [compraventas, setCompraventas] = useState([]);

  // Cargamos la lista solo cuando cargamos la vista
  useEffect(() => {
    async function getAllCompraventas() {
      const url = `/compraventas?page=0&size=10`;
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
        setCompraventas(lista);
      } catch (error){
        console.error("Error al obtener la lista de compraventas:", error);
      }
    }
    getAllCompraventas();
  }, []);

  return (
    <section className="container py-4">
      {/* Cabecera */}
      <div className="row align-items-center mb-4">
        <div className="col">
          <h2>Compraventas entre usuarios</h2>
        </div>
        <div className="col-auto">
          <span className="badge bg-primary fs-6">
            {compraventas.length} compraventas
          </span>
        </div>
      </div>

      {/* Mensaje vacío */}
      {compraventas.length === 0 && (
        <p className="text-center text-muted py-4">
          No hay compraventas registradas
        </p>
      )}

      {/* Tabla */}
      {compraventas.length > 0 && (
        <div className="table-responsive">
          <table className="table table-hover table-striped text-center">
            <thead className="table-dark">
              <tr>
                <th scope="col" style={{ width: "30%" }}>Producto</th>
                <th scope="col" style={{ width: "10%" }}>Precio</th>
                <th scope="col" className="col-ocultar" style={{ width: "20%" }}>Comprador</th>
                <th scope="col" className="col-ocultar" style={{ width: "20%" }}>Vendedor</th>
                <th scope="col" style={{ width: "20%" }}>Fecha</th>
              </tr>
            </thead>
            <tbody>
              {compraventas.map((compraventa) => {
                const fecha = new Date(compraventa.fecha).toLocaleDateString("es-ES", {
                  day: "2-digit", month: "short", year: "numeric",
                });
                return (
                  <tr key={compraventa.id}>
                    <td>{compraventa.titulo}</td>
                    <td>{compraventa.precio}</td>
                    <td className="col-ocultar">{compraventa.nombreComprador}</td>
                    <td className="col-ocultar">{compraventa.nombreVendedor}</td>
                    <td>{fecha}</td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      )}
    </section>
  );
}
