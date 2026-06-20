import { useState, useEffect } from "react";
import CrearProducto from "../components/CrearProducto";
import ModificarProducto from "../components/ModificarProducto";

const usuario = JSON.parse(localStorage.getItem("usuario"));

export default function MisProductos() {
  const [productos, setProductos] = useState([]);
  const [modalCrearProducto, setModalCrearProducto] = useState(false);
  const [productoAModificar, setProductoAModificar] = useState(null);
  const [pagina, setPagina] = useState(0);
  const [size] = useState(10);
  const [totalPaginas, setTotalPaginas] = useState(0);
  const [totalElementos, setTotalElementos] = useState(0);

  async function getUserProductos() {
    const url = `/productos/usuario/${usuario.id}?page=${pagina}&size=${size}`;
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
      const lista = body._embedded && body._embedded.productoResDTOList ? body._embedded.productoResDTOList : [];
      setProductos(lista);
      if (body.page) {
        setTotalPaginas(body.page.totalPages);
        setTotalElementos(body.page.totalElements);
      } else {
        setTotalPaginas(1);
        setTotalElementos(lista.length);
      }
    } catch (error) {
      // Error de red u otro fallo inesperado: lo tratamos como error de servidor
      console.error(
        `Error al obtener los productos del usuario con ID ${usuario.id}:`,
        error,
      );
      window.location.href = "/error/500";
    }
  }
    // Cargamos la lista solo cuando cargamos la vista
  function paginaAnterior() {
    if (pagina > 0) setPagina(pagina - 1);
  }

  function paginaSiguiente() {
    if (pagina < totalPaginas - 1) setPagina(pagina + 1);
  }

  // Recargamos la lista cada vez que cambia la pagina
  useEffect(function () {
    getUserProductos();
  }, [pagina]);

  return (
    <section className="container py-4">
      {/* Cabecera */}
      <div className="row align-items-center mb-4">
        <div className="col">
          <h2>Mis productos</h2>
        </div>
        {/* Contador de productos y boton de Crear Producto */}
        <div className="col-auto d-flex gap-2">
          <span
            className="btn btn-primary fw-bold"
            style={{ pointerEvents: "none" }} // Para que no se marque como boton
          >
            {totalElementos} productos
          </span>
          <button
            className="btn btn-secondary"
            onClick={() => setModalCrearProducto(true)}
          >
            Crear Producto
          </button>
        </div>
      </div>

      {/* Mensaje vacío */}
      {productos.length === 0 && (
        <p className="text-center text-muted py-4">
          No has puesto a la venta ningún producto todavía
        </p>
      )}

      {/* Tabla */}
      {productos.length > 0 && (
        <div className="table-responsive">
          <table className="table table-hover table-striped text-center">
            <thead className="table-dark">
              <tr>
                <th scope="col">Título</th>
                <th scope="col" className="col-ocultar">
                  Categoría
                </th>
                <th scope="col">Precio</th>
                <th scope="col" className="col-ocultar">
                  Fecha
                </th>
                <th scope="col">¿Vendido?</th>
                <th scope="col">Accion</th>
              </tr>
            </thead>
            <tbody>
              {productos.map((producto) => (
                <tr key={producto.id}>
                  <td>{producto.titulo}</td>
                  <td className="col-ocultar">{producto.categoria.nombre}</td>
                  <td>{producto.precio} €</td>
                  <td className="col-ocultar">
                    {new Date(producto.fechaPubli).toLocaleDateString("es-ES", {
                      day: "2-digit",
                      month: "short",
                      year: "numeric",
                    })}
                  </td>
                  <td>
                    {producto.vendido ? (
                      <span className="badge bg-success">Vendido</span>
                    ) : (
                      <span className="badge bg-warning text-dark">
                        En venta
                      </span>
                    )}
                  </td>
                  <td>
                    <button
                      className="btn btn-sm btn-outline-primary"
                      disabled={producto.vendido}
                      onClick={function () { setProductoAModificar(producto); }}
                    >
                      Modificar
                    </button>
                  </td>
                </tr>
              ))}
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

      {productoAModificar !== null && (
        <ModificarProducto
          producto={productoAModificar}
          onCerrar={function () { setProductoAModificar(null); }}
          onSuccess={function () {
            setProductoAModificar(null);
            getUserProductos();
          }}
        />
      )}

      {modalCrearProducto && (
        <CrearProducto
          onCerrar={() => setModalCrearProducto(false)}
          onSuccess={() => {
            setModalCrearProducto(false);
            getUserProductos(); // Recargar la tabla
          }}
        />
      )}
    </section>
  );
}
