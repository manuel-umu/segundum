import { useState } from "react";

function ModificarProducto({ producto, onCerrar, onSuccess }) {
  const [descripcion, setDescripcion] = useState(producto.descripcion);
  const [precio, setPrecio] = useState(producto.precio);
  const [error, setError] = useState("");

  async function modificar(e) {
    e.preventDefault();
    setError("");

    if (!precio || parseFloat(precio) <= 0) {
      setError("El precio debe ser mayor que 0.");
      return;
    }

    try {
      const res = await fetch("/productos/" + producto.id, {
        method: "PATCH",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          precio: parseFloat(precio),
          descripcion: descripcion,
        }),
      });

      if (res.ok) {
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
            <h5 className="modal-title">Modificar producto</h5>
            <button className="btn-close" onClick={onCerrar}></button>
          </div>
          <div className="modal-body">
            {error && <div className="alert alert-danger">{error}</div>}
            <form onSubmit={modificar}>
              {/* Descripcion */}
              <div className="mb-3">
                <label
                  htmlFor="mod-descripcion"
                  className="form-label fw-medium"
                >
                  Descripcion <span className="text-danger">*</span>
                </label>
                <textarea
                  id="mod-descripcion"
                  className="form-control"
                  rows="4"
                  value={descripcion}
                  onChange={function (e) {
                    setDescripcion(e.target.value);
                  }}
                  placeholder="Describe tu producto..."
                  required
                />
              </div>

              {/* Precio */}
              <div className="mb-4">
                <label htmlFor="mod-precio" className="form-label fw-medium">
                  Precio <span className="text-danger">*</span>
                </label>
                <div className="input-group col-12 col-md-6">
                  <input
                    type="number"
                    id="mod-precio"
                    className="form-control"
                    min="0.01"
                    step="0.01"
                    value={precio}
                    onChange={function (e) {
                      setPrecio(e.target.value);
                    }}
                    required
                  />
                </div>
              </div>

              {/* Botones */}
              <div className="d-flex gap-2">
                <button
                  type="button"
                  className="btn btn-outline-secondary w-100"
                  onClick={onCerrar}
                >
                  Cancelar
                </button>
                <button type="submit" className="btn btn-primary w-100">
                  Guardar cambios
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ModificarProducto;
