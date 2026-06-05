import { useState } from "react";

function textoEstado(estado) {
  if (estado === "NUEVO") return "Nuevo";
  if (estado === "COMONUEVO") return "Como nuevo";
  if (estado === "BUENESTADO") return "Buen estado";
  if (estado === "ACEPTABLE") return "Aceptable";
  if (estado === "PARAPIEZAS_O_REPARAR") return "Para piezas o reparar";
  return "";
}

export default function TarjetaProducto({ producto, onCerrar }) {
  const [comprado, setComprado] = useState(false);
  const [errorAccion, setErrorAccion] = useState("");

  // Usuario logueado (puede ser null si no hay sesion)
  const usuario = JSON.parse(localStorage.getItem("usuario"));
  const usuarioId = usuario === null ? null : usuario.id;

  // Comprobamos si el producto es del usuario actual
  var esMiProducto = false;
  if (producto.vendedor && producto.vendedor.id === usuarioId) {
    esMiProducto = true;
  }

  const estadoTxt = textoEstado(producto.estado);

  var lugarRecogida = "";
  if (producto.recogida && producto.recogida.descripcion) {
    lugarRecogida = producto.recogida.descripcion;
  }

  async function comprar() {
    setErrorAccion("");
    if (usuarioId === null) {
      setErrorAccion("Debes iniciar sesion para comprar.");
      return;
    }
    try {
      const res = await fetch("/api/compraventas", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          idProducto: producto.id,
          idComprador: usuarioId,
        }),
      });
      if (res.ok) {
        setComprado(true);
      } else {
        setErrorAccion("No se pudo registrar la compra.");
      }
    } catch (err) {
      setErrorAccion("Error de red al comprar.");
    }
  }

  return (
    <div className="modal d-block modal-fondo">
      <div className="modal-dialog modal-lg modal-dialog-scrollable">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">{producto.titulo}</h5>
            <button className="btn-close" onClick={onCerrar}></button>
          </div>

          <div className="modal-body">
            {comprado && (
              <div className="alert alert-success">
                Compra realizada. El vendedor se pondra en contacto contigo.
              </div>
            )}

            {errorAccion !== "" && (
              <div className="alert alert-danger">{errorAccion}</div>
            )}

            {/* Datos del producto */}
            <div>
              <p className="fs-3 fw-bold text-success">{producto.precio} €</p>

              {estadoTxt !== "" && (
                <p>
                  <span className="text-muted">Estado: </span>
                  {estadoTxt}
                </p>
              )}
              {producto.categoria && producto.categoria.nombre && (
                <p>
                  <span className="text-muted">Categoria: </span>
                  {producto.categoria.nombre}
                </p>
              )}
              {lugarRecogida !== "" && (
                <p>
                  <span className="text-muted">Recogida en: </span>
                  {lugarRecogida}
                </p>
              )}
              {producto.envioDispo && (
                <p className="text-success">Envio disponible</p>
              )}
              {producto.vendedor &&
                producto.vendedor.nombre &&
                !esMiProducto && (
                  <p>
                    <span className="text-muted">Vendedor: </span>
                    {producto.vendedor.nombre}
                  </p>
                )}
              {esMiProducto && (
                <p className="text-muted">
                  <small>Este anuncio es tuyo.</small>
                </p>
              )}
            </div>
            <p className="mt-3">{producto.descripcion}</p>
          </div>

          {/* Botones */}
          <div className="modal-footer">
            <button className="btn btn-secondary" onClick={onCerrar}>
              Cerrar
            </button>
            {!esMiProducto && !comprado && (
              <button className="btn btn-primary" onClick={comprar}>
                Comprar
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
