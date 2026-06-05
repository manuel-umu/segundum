import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

import ProductoTarjeta from "../components/TarjetaProducto";

const ESTADOS = [
  { value: "NUEVO", label: "Nuevo" },
  { value: "COMONUEVO", label: "Como nuevo" },
  { value: "BUENESTADO", label: "Buen estado" },
  { value: "ACEPTABLE", label: "Aceptable" },
  { value: "PARAPIEZAS_O_REPARAR", label: "Para piezas o reparar" },
];

export default function ListaProductos() {
  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const idVendedor = params.get("idVendedor");

  const [productoSeleccionado, setProductoSeleccionado] = useState(null);

  const [productos, setProductos] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [errorCarga, setErrorCarga] = useState("");

  // Filtros
  const [categoria, setCategoria] = useState("");
  const [descripcion, setDescripcion] = useState("");
  const [estado, setEstado] = useState("");
  const [precio, setPrecio] = useState("");

  // Paginacion
  const [size] = useState(10);
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

  // Carga de productos cada vez que cambie la pagina o el tamaño
  useEffect(
    function () {
      async function cargarProductos() {
        setErrorCarga("");
        try {
          const res = await fetch(
            "/productos?page=" + pagina + "&size=" + size,
          );
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
    [pagina, size],
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
  }

  // Indica si hay algun filtro activo (para habilitar el boton "Limpiar")
  const hayFiltros = categoria !== "" || descripcion !== "" || estado !== "" || precio !== "";

  const productosFiltrados = productos.filter(function (p) {
    if (idVendedor !== null) {
      if (!p.vendedor || p.vendedor.id !== idVendedor) return false;
    }
    if (categoria !== "") {
      if (!p.categoria || p.categoria.id !== categoria) return false;
    }
    if (descripcion !== "") {
      if (!p.descripcion) return false;
      if (p.descripcion.toLowerCase().indexOf(descripcion.toLowerCase()) === -1)
        return false;
    }
    if (estado !== "" && p.estado !== estado) {
      return false;
    }
    if (precio !== "" && p.precio > Number(precio)) {
      return false;
    }
    // Solo mostramos productos no vendidos
    if (p.vendido) return false;
    return true;
  });

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
                Mostrando <strong>{productosFiltrados.length}</strong> producto
                {productosFiltrados.length === 1 ? "" : "s"} en esta pagina.
              </small>
            </div>
          )}
        </div>
      </div>

      <h1>{idVendedor !== null ? "Mis productos" : "Productos en venta"}</h1>

      {errorCarga && <div className="alert alert-danger">{errorCarga}</div>}

      {/* Grid de tarjetas - cada tarjeta abre el popup al hacer clic */}
      <div className="grid-productos mt-3">
        {productosFiltrados.map(function (p) {
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

        {productosFiltrados.length === 0 && !errorCarga && (
          <p className="text-muted">
            No hay productos que coincidan con los filtros.
          </p>
        )}
      </div>

      {/* Controles de paginacion */}
      {totalPaginas > 1 && (
        <div className="d-flex justify-content-center align-items-center mt-4 gap-2">
          <button
            className="btn btn-outline-primary"
            onClick={paginaAnterior}
            disabled={pagina === 0}
          >
            Anterior
          </button>
          <span>
            Pagina {pagina + 1} de {totalPaginas}
          </span>
          <button
            className="btn btn-outline-primary"
            onClick={paginaSiguiente}
            disabled={pagina >= totalPaginas - 1}
          >
            Siguiente
          </button>
        </div>
      )}

      {/* Popup de detalle - solo visible si hay un producto seleccionado */}
      {productoSeleccionado !== null && (
        <ProductoTarjeta
          producto={productoSeleccionado}
          onCerrar={cerrarPopup}
        />
      )}
    </section>
  );
}
