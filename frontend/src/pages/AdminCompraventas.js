import { useState, useEffect } from "react";

export default function AdminCompraventas() {
  const [compraventas, setCompraventas] = useState([]);
  const [pagina, setPagina] = useState(0);
  const [size] = useState(10);
  const [totalPaginas, setTotalPaginas] = useState(0);
  const [totalElementos, setTotalElementos] = useState(0);

  useEffect(() => {
    async function getAllCompraventas() {
      const url = `/compraventas?page=${pagina}&size=${size}`;
      try {
        const response = await fetch(url, {
          credentials: "include"
        });
        if (!response.ok) {
          window.location.href = "/error/" + response.status;
          return;
        }
        const body = await response.json();
        const lista = body._embedded?.compraventaOutputDTOList ?? [];
        setCompraventas(lista);
        if (body.page) {
          setTotalPaginas(body.page.totalPages);
          setTotalElementos(body.page.totalElements);
        } else {
          setTotalPaginas(1);
          setTotalElementos(lista.length);
        }
      } catch (error) {
        window.location.href = "/error/502";
      }
    }
    getAllCompraventas();
  }, [pagina]);

  function paginaAnterior() {
    if (pagina > 0) setPagina(pagina - 1);
  }

  function paginaSiguiente() {
    if (pagina < totalPaginas - 1) setPagina(pagina + 1);
  }

  return (
    <section className="container py-4">
      {/* Cabecera */}
      <div className="row align-items-center mb-4">
        <div className="col">
          <h2>Compraventas entre usuarios</h2>
        </div>
        <div className="col-auto">
          <span className="badge bg-primary fs-6">
            {totalElementos} compraventas
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
          <table className="table table-hover table-striped text-center"
            style={{ tableLayout: 'fixed', width: '100%' }}
          >
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
                    <td>{compraventa.precio} €</td>
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

      {/* Paginacion */}
      {totalPaginas > 1 && (
        <nav aria-label="Navegacion de paginas" className="mt-3">
          <ul className="pagination justify-content-center">
            <li className={`page-item ${pagina === 0 ? "disabled" : ""}`}>
              <button className="page-link" onClick={paginaAnterior}>
                Anterior
              </button>
            </li>
            {Array.from({ length: totalPaginas }, function (_, i) {
              return (
                <li key={i} className={`page-item ${pagina === i ? "active" : ""}`}>
                  <button className="page-link" onClick={function () { setPagina(i); }}>
                    {i + 1}
                  </button>
                </li>
              );
            })}
            <li className={`page-item ${pagina === totalPaginas - 1 ? "disabled" : ""}`}>
              <button className="page-link" onClick={paginaSiguiente}>
                Siguiente
              </button>
            </li>
          </ul>
        </nav>
      )}
    </section>
  );
}