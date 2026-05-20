import { useState } from "react";
import { PRODUCTOS, CATEGORIAS } from "../services/mockData";
import ProductoTarjeta from "../components/TarjetaProducto";

export default function ListaProductos() {
  // Producto seleccionado para el popup (null = cerrado)
  const [productoSeleccionado, setProductoSeleccionado] = useState(null);

  function abrirPopup(producto) {
    setProductoSeleccionado(producto);
  }

  function cerrarPopup() {
    setProductoSeleccionado(null);
  }

  return (
    <section className="container mt-4">
      <h1>Productos en venta</h1>

      {/* Grid de tarjetas - cada tarjeta abre el popup al hacer clic */}
      <div className="grid-productos mt-3">
        {PRODUCTOS.map(function (p) {
          // Buscamos el nombre de la categoria del producto
          var nombreCategoria = "";
          for (var j = 0; j < CATEGORIAS.length; j++) {
            if (CATEGORIAS[j].id === p.categoria_id) {
              nombreCategoria = CATEGORIAS[j].nombre;
            }
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
      </div>

      {/* Popup de detalle - solo visible si hay un producto seleccionado */}
      {productoSeleccionado && (
        <ProductoTarjeta producto={productoSeleccionado} onCerrar={cerrarPopup} />
      )}
    </section>
  );
}
