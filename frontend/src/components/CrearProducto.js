import { useState, useEffect } from "react";

const ESTADOS = [
  { value: "NUEVO", label: "Nuevo" },
  { value: "COMONUEVO", label: "Como nuevo" },
  { value: "BUENESTADO", label: "Buen estado" },
  { value: "ACEPTABLE", label: "Aceptable" },
  { value: "PARAPIEZAS_O_REPARAR", label: "Para piezas o reparar" },
];

function CrearProducto({ onCerrar, onSuccess }) {
  const [titulo, setTitulo] = useState("");
  const [descripcion, setDescripcion] = useState("");
  const [estado, setEstado] = useState("");
  const [precio, setPrecio] = useState("");
  const [categoriaId, setCategoriaId] = useState("");
  const [envioDisponible, setEnvioDisponible] = useState(false);
  const [categorias, setCategorias] = useState([]);

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
        console.error(`Error al cargar categorías: ${err}`);
      }
    }
    cargarCategorias();
  }, []);

  async function crearProducto(e) {
    e.preventDefault();
    if (!titulo || !estado || !categoriaId || !precio) {
      alert("Título, estado, categoría y precio son obligatorios");
      return;
    }

    const usuario = JSON.parse(localStorage.getItem("usuario"));
    if (!usuario || !usuario.id) {
      window.location.href = "/error/401";
    }
    const fechaPubli = new Date().toISOString().split(".")[0];
    try {
      const res = await fetch("/productos", {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          titulo: titulo,
          descripcion: descripcion,
          precio: parseFloat(precio),
          estado: estado,
          fechaPubli: fechaPubli,
          envioDispo: envioDisponible,
          categoria: {
            id: categoriaId,
          },
          vendedor: {
            id: usuario.id,
          },
        }),
      });

      if (res.ok) {
        alert("Producto creado!");
        onSuccess();
      } else {
        window.location.href = "/error/" + res.status;
      }
    } catch (err) {
      window.location.href = "/error/502";
    }
  }

  return (
    <div className="modal d-block modal-fondo">
      <div className="modal-dialog modal-lg modal-dialog-scrollable">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">Publicar Producto</h5>
            <button className="btn-close" onClick={onCerrar}></button>
          </div>
          <div className="modal-body">
            <form onSubmit={crearProducto}>
              {/* Título */}
              <div className="mb-3">
                <label htmlFor="titulo" className="form-label fw-medium">
                  Título del producto <span className="text-danger">*</span>
                </label>
                <input
                  type="text"
                  id="titulo"
                  className="form-control"
                  value={titulo}
                  onChange={function (e) { setTitulo(e.target.value); }}
                  required
                  placeholder="¿Que vendes?"
                />
              </div>

              {/* Descripción */}
              <div className="mb-3">
                <label htmlFor="descripcion" className="form-label fw-medium">
                  Descripción <span className="text-danger">*</span>
                </label>
                <textarea
                  id="descripcion"
                  className="form-control"
                  rows="4"
                  value={descripcion}
                  onChange={function (e) { setDescripcion(e.target.value); }}
                  placeholder="Describe tu producto..."
                  required
                />
              </div>

              {/* Categoría */}
              <div className="mb-3">
                <label
                  htmlFor="categoriaPrincipal"
                  className="form-label fw-medium"
                >
                  Categoría <span className="text-danger">*</span>
                </label>
                <select
                  id="categoriaPrincipal"
                  className="form-select"
                  value={categoriaId}
                  onChange={function (e) { setCategoriaId(e.target.value); }}
                  required
                >
                  <option value="">
                    {!categorias.length
                      ? "Cargando..."
                      : "Selecciona categoría"}
                  </option>
                  {categorias.map(function(cat) {
                    return (
                      <option key={cat.id} value={cat.id}>
                        {cat.nombre}
                      </option>
                    );
                  })}
                </select>
              </div>

              {/* Estado */}
              <div className="mb-3">
                <div className="col-12 col-md-6 col-lg-3">
                  <label htmlFor="f-estado" className="form-label fw-medium">
                    Estado <span className="text-danger">*</span>
                  </label>
                  <select
                    id="f-estado"
                    className="form-select"
                    value={estado}
                    onChange={function (e) {
                      setEstado(e.target.value);
                    }}
                    required
                  >
                    <option value="">Selecciona estado</option>
                    {ESTADOS.map(function (e) {
                      return (
                        <option key={e.value} value={e.value}>
                          {e.label}
                        </option>
                      );
                    })}
                  </select>
                </div>
              </div>

              {/* Precio */}
              <div className="mb-3">
                <div className="col-12 col-md-6">
                  <label htmlFor="precio" className="form-label fw-medium">
                    Precio <span className="text-danger">*</span>
                  </label>
                  <div className="input-group">
                    <input
                      type="number"
                      id="precio"
                      className="form-control"
                      min="0.01"
                      step="0.01"
                      value={precio}
                      onChange={function (e) { setPrecio(e.target.value); }}
                      required
                    />
                  </div>
                </div>
              </div>

              {/* Envío */}
              <div className="mb-4 form-check">
                <input
                  type="checkbox"
                  id="envio"
                  className="form-check-input"
                  checked={envioDisponible}
                  onChange={function (e) { setEnvioDisponible(e.target.checked); }}
                />
                <label htmlFor="envio" className="form-check-label">
                  Envío disponible
                </label>
              </div>
              {/* BOTONES */}
              <div className="d-flex gap-2">
                <button
                  type="button"
                  className="btn btn-outline-secondary w-100"
                  onClick={onCerrar}
                >
                  Cancelar
                </button>
                <button type="submit" className="btn btn-primary w-100">
                  Publicar
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CrearProducto;
