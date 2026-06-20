import { useState, useEffect } from "react";

import ProductoTarjeta from "../components/TarjetaProducto";

const ESTADOS = [
  { value: "NUEVO", label: "Nuevo" },
  { value: "COMONUEVO", label: "Como nuevo" },
  { value: "BUENESTADO", label: "Buen estado" },
  { value: "ACEPTABLE", label: "Aceptable" },
  { value: "PARAPIEZAS_O_REPARAR", label: "Para piezas o reparar" },
];

export default function ListaProductos() {
  const [productoSeleccionado, setProductoSeleccionado] = useState(null);
  const [productos, setProductos] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [errorCarga, setErrorCarga] = useState("");
  const [mensajeExito, setMensajeExito] = useState("");
  const [recarga, setRecarga] = useState(0);

  // Filtros
  const [categoria, setCategoria] = useState("");
  const [descripcion, setDescripcion] = useState("");
  const [estado, setEstado] = useState("");
  const [precio, setPrecio] = useState("");

  // Paginacion
  const SIZE = 10;
  const [pagina, setPagina] = useState(0);
  const [totalPaginas, setTotalPaginas] = useState(0);

  // Useffect 1 vez
  useEffect(function () {
    async function cargarCategorias() {
      try {
        const res = await fetch("/categorias");
        if (res.ok) {
          const data = await res.json();
          setCategorias(data);
        }
      } catch (err) {
        // Si fallan las categorias seguimos sin ellas, no es critico
      }
    }
    cargarCategorias();
  }, []);

  // Carga de productos cada vez que cambie la pagina o algun filtro
  useEffect(
    function () {
      async function cargarProductos() {
        setErrorCarga("");

        var url = "/productos/enVenta?page=" + pagina + "&size=" + SIZE;
        if (categoria !== "") {
          url += "&categoria=" + encodeURIComponent(categoria);
        }
        if (descripcion !== "") {
          url += "&texto=" + encodeURIComponent(descripcion);
        }
        if (estado !== "") {
          url += "&estado=" + encodeURIComponent(estado);
        }
        if (precio !== "") {
          url += "&precioMax=" + encodeURIComponent(precio);
        }

        try {
          const res = await fetch(url);
          if (!res.ok) {
            setErrorCarga("No se pudieron cargar los productos.");
            return;
          }
          const data = await res.json();

          // La respuesta viene en formato HATEOAS
          var lista = [];
          if (data._embedded && data._embedded.productoResDTOList) {
            lista = data._embedded.productoResDTOList;
          }
          setProductos(lista);

          var total = 0;
          if (data.page && data.page.totalPages) {
            total = data.page.totalPages;
          }
          setTotalPaginas(total);
        } catch (err) {
          setErrorCarga("Error de red al cargar los productos.");
        }
      }
      cargarProductos();
    },
    [pagina, categoria, descripcion, estado, precio, recarga],
  );

  function abrirPopup(producto) {
    setProductoSeleccionado(producto);
  }

  function cerrarPopup() {
    setProductoSeleccionado(null);
  }

  function paginaAnterior() {
    if (pagina > 0) setPagina(pagina - 1);
  }

  function paginaSiguiente() {
    if (pagina < totalPaginas - 1) setPagina(pagina + 1);
  }

  function limpiarFiltros() {
    setCategoria("");
    setDescripcion("");
    setEstado("");
    setPrecio("");
    setPagina(0);
  }

  // Indica si hay algun filtro activo para habilitar el boton de limpiar
  const hayFiltros =
    categoria !== "" || descripcion !== "" || estado !== "" || precio !== "";

  return (
    <section className="container mt-4">
      {/* Filtros*/}
      <div className="card shadow-sm mb-4">
        <div className="card-header bg-light d-flex justify-content-between align-items-center">
          <h5 className="mb-0">Filtros</h5>
          <button
            type="button"
            className="btn btn-sm btn-outline-secondary"
            onClick={limpiarFiltros}
            disabled={!hayFiltros}
          >
            Limpiar
          </button>
        </div>
        <div className="card-body">
          <div className="row g-3">
            {/* Categoria */}
            <div className="col-12 col-md-6 col-lg-3">
              <label htmlFor="f-categoria" className="form-label">
                Categoria
              </label>
              <select
                id="f-categoria"
                className="form-select"
                value={categoria}
                onChange={function (e) {
                  setCategoria(e.target.value);
                  setPagina(0);
                }}
              >
                <option value="">Todas</option>
                {categorias.map(function (cat) {
                  return (
                    <option key={cat.id} value={cat.id}>
                      {cat.nombre}
                    </option>
                  );
                })}
              </select>
            </div>

            {/* Descripcion */}
            <div className="col-12 col-md-6 col-lg-3">
              <label htmlFor="f-descripcion" className="form-label">
                Descripcion
              </label>
              <input
                id="f-descripcion"
                type="text"
                className="form-control"
                placeholder="Buscar..."
                value={descripcion}
                onChange={function (e) {
                  setDescripcion(e.target.value);
                  setPagina(0);
                }}
              />
            </div>

            {/* Estado */}
            <div className="col-12 col-md-6 col-lg-3">
              <label htmlFor="f-estado" className="form-label">
                Estado
              </label>
              <select
                id="f-estado"
                className="form-select"
                value={estado}
                onChange={function (e) {
                  setEstado(e.target.value);
                  setPagina(0);
                }}
              >
                <option value="">Todos</option>
                {ESTADOS.map(function (e) {
                  return (
                    <option key={e.value} value={e.value}>
                      {e.label}
                    </option>
                  );
                })}
              </select>
            </div>

            {/* Precio maximo (con simbolo € como sufijo) */}
            <div className="col-12 col-md-6 col-lg-3">
              <label htmlFor="f-precio" className="form-label">
                Precio maximo
              </label>
              <div className="input-group">
                <input
                  id="f-precio"
                  type="number"
                  className="form-control"
                  placeholder="0"
                  value={precio}
                  min={0}
                  onChange={function (e) {
                    setPrecio(e.target.value);
                    setPagina(0);
                  }}
                />
                <span className="input-group-text">€</span>
              </div>
            </div>
          </div>

          {/* Contador con el numero de resultados visibles */}
          {hayFiltros && (
            <div className="mt-3 text-muted">
              <small>
                Mostrando <strong>{productos.length}</strong> producto
                {productos.length === 1 ? "" : "s"} en esta pagina.
              </small>
            </div>
          )}
        </div>
      </div>

      <h1>Productos en venta</h1>

      {errorCarga && <div className="alert alert-danger">{errorCarga}</div>}
      {mensajeExito && (
        <div className="alert alert-success">{mensajeExito}</div>
      )}

      {/* Grid de tarjetas - cada tarjeta abre el popup al hacer clic */}
      <div className="grid-productos mt-3">
        {productos.map(function (p) {
          var nombreCategoria = "";
          if (p.categoria && p.categoria.nombre) {
            nombreCategoria = p.categoria.nombre;
          }

          return (
            <div
              key={p.id}
              className="card tarjeta-producto"
              onClick={function () {
                abrirPopup(p);
              }}
            >
              <div className="card-body">
                <h5 className="card-title">{p.titulo}</h5>
                <p className="text-success fw-bold mb-1">{p.precio} €</p>
                {nombreCategoria !== "" && (
                  <p className="text-muted mb-0">
                    <small>{nombreCategoria}</small>
                  </p>
                )}
              </div>
            </div>
          );
        })}

        {productos.length === 0 && !errorCarga && (
          <p className="text-muted">
            No hay productos que coincidan con los filtros.
          </p>
        )}
      </div>

      {/* Controles de paginacion */}
      {totalPaginas > 1 && (
        <nav aria-label="Navegacion de paginas" className="mt-4">
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
                  <button
                    className="page-link"
                    onClick={function () {
                      setPagina(i);
                    }}
                  >
                    {i + 1}
                  </button>
                </li>
              );
            })}

            {/* Boton siguiente */}
            <li
              className={`page-item ${pagina >= totalPaginas - 1 ? "disabled" : ""}`}
            >
              <button className="page-link" onClick={paginaSiguiente}>
                Siguiente
              </button>
            </li>
          </ul>
        </nav>
      )}

      {/* Popup de detalle - solo visible si hay un producto seleccionado */}
      {productoSeleccionado !== null && (
        <ProductoTarjeta
          producto={productoSeleccionado}
          onCerrar={cerrarPopup}
          onEliminar={function () {
            cerrarPopup();
            setRecarga(recarga + 1);
            setMensajeExito("Producto eliminado correctamente.");
          }}
          onComprar={function () {
            setTimeout(function () {
              cerrarPopup();
              setRecarga(recarga + 1);
            }, 800);
            setMensajeExito(
              "Compra solicitada correctamente. El vendedor se pondra en contacto contigo.",
            );
          }}
        />
      )}
    </section>
  );
}
