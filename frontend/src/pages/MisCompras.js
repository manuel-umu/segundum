import { useState, useEffect } from "react";

const usuario = JSON.parse(localStorage.getItem("usuario"));

export default function MisCompras() {
  const [compras, setCompras] = useState([]);
  const [pagina, setPagina] = useState(0);
  const [size] = useState(10);
  const [totalPaginas, setTotalPaginas] = useState(0);
  const [totalElementos, setTotalElementos] = useState(0);

  async function getUserCompras() {
    const url = `/compraventas/compras/${usuario.id}/?page=${pagina}&size=${size}`;
    try {
      const response = await fetch(url, {
        credentials: "include"
      });
      // En caso de error, plantilla de express
      if (!response.ok) {
        window.location.href = "/error/" + response.status;
        return;
      }
      const body = await response.json();
      // Extraemos la lista del formato HATEOAS
      const lista = body._embedded?.compraventaOutputDTOList ?? [];
      setCompras(lista);
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

  function paginaAnterior() {
    if (pagina > 0) setPagina(pagina - 1);
  }

  function paginaSiguiente() {
    if (pagina < totalPaginas - 1) setPagina(pagina + 1);
  }

  useEffect(function () {
    getUserCompras();
  }, [pagina]);

  return (
    <section className="container py-4">
      {/* Cabecera */}
      <div className="row align-items-center mb-4">
        <div className="col">
          <h2>Mis compras</h2>
        </div>
        <div className="col-auto">
          <span className="badge bg-primary fs-6">
            {totalElementos} compras
          </span>
        </div>
      </div>

      {/* Mensaje vacío */}
      {compras.length === 0 && (
        <p className="text-center text-muted py-4">
          No has realizado ninguna compra todavía
        </p>
      )}

      {/* Tabla */}
      {compras.length > 0 && (
        <div className="table-responsive">
          <table className="table table-hover table-striped text-center"
            style={{ tableLayout: 'fixed', width: '100%' }}
          >
            <thead className="table-dark">
              <tr>
                <th scope="col" style={{ width: "30%" }} >Producto</th>
                <th scope="col" style={{ width: "30%" }} className="col-ocultar">
                  Vendedor
                </th>
                <th scope="col" style={{ width: "10%" }} >Precio</th>
                <th scope="col" style={{ width: "30%" }} className="col-ocultar">
                  Fecha
                </th>
              </tr>
            </thead>
            <tbody>
              {compras.map((compra) => {
                const fecha = new Date(compra.fecha).toLocaleDateString('es-ES', {
                  day: '2-digit', month: 'short', year: 'numeric',
                });

                return (
                  <tr key={compra.idProducto}>
                    <td>{compra.titulo}</td>
                    <td className="col-ocultar">{compra.nombreVendedor}</td>
                    <td>{compra.precio} €</td>
                    <td className="col-ocultar">{fecha}</td>
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
            {/* Boton anterior */}
            <li className={`page-item ${pagina === 0 ? "disabled" : ""}`}>
              <button className="page-link" onClick={paginaAnterior}>
                Anterior
              </button>
            </li>

            {/* Paginas numeradas */}
            {Array.from({ length: totalPaginas }, function (_, i) {
              return (
                <li
                  key={i}
                  className={`page-item ${pagina === i ? "active" : ""}`}
                >
                  <button className="page-link" onClick={function () { setPagina(i); }}>
                    {i + 1}
                  </button>
                </li>
              );
            })}

            {/* Boton siguiente */}
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
