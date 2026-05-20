import { useState } from "react";

export default function ProductoTarjeta({ producto, onCerrar }) {
  const [comprado, setComprado] = useState(false);

  var usuarioId = 1;
  var esMiProducto = producto.vendedor_id === usuarioId;

  var textoEstado = "";
  if (producto.estado === "nuevo") {
    textoEstado = "Nuevo";
  }
  if (producto.estado === "como_nuevo") {
    textoEstado = "Como nuevo";
  }
  if (producto.estado === "buen_estado") {
    textoEstado = "Buen estado";
  }
  if (producto.estado === "aceptable") {
    textoEstado = "Aceptable";
  }
  if (producto.estado === "para_piezas_o_reparar") {
    textoEstado = "Para piezas o reparar";
  }

  function comprar() {
    // TODO: llamada al backend para registrar la compra
    console.log("Comprando producto:", producto.id);
    setComprado(true);
  }

  function eliminarProducto() {
    // TODO: llamada al backend para eliminar
    console.log("Eliminando producto:", producto.id);
    onCerrar();
  }

  return (
    <div
      className="modal d-block modal-fondo"
    >
      <div className="modal-dialog modal-lg modal-dialog-scrollable">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">{producto.titulo}</h5>
            <button className="btn-close" onClick={onCerrar}></button>
          </div>

          <div className="modal-body">
            {/* Aviso si la compra ya se realizo */}
            {comprado && (
              <div className="alert alert-success">
                Compra realizada. El vendedor se pondrá en contacto contigo.
              </div>
            )}

            {/* Datos del producto */}
            <div>
              <p className="fs-3 fw-bold text-success">{producto.precio} €</p>

              {textoEstado !== "" && (
                <p>
                  <span className="text-muted">Estado: </span>
                  {textoEstado}
                </p>
              )}

              {producto.lugar_recogida && (
                <p>
                  <span className="text-muted">Recogida en: </span>
                  {producto.lugar_recogida}
                </p>
              )}

              {producto.envio_disponible && (
                <p className="text-success">Envio disponible</p>
              )}

              {esMiProducto && (
                <p className="text-muted">
                  <small>Este anuncio es tuyo.</small>
                </p>
              )}
            </div>

            {/* Descripcion completa */}
            <p className="mt-3">{producto.descripcion}</p>

          </div>

          <div className="modal-footer">
            <button className="btn btn-secondary" onClick={onCerrar}>
              Cerrar
            </button>

            {!esMiProducto && !comprado && (
              <button className="btn btn-primary" onClick={comprar}>
                Comprar
              </button>
            )}

            {esMiProducto && (
              <button className="btn btn-danger" onClick={eliminarProducto}>
                Eliminar anuncio
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
