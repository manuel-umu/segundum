import { useState, useEffect } from "react";
import { useParams } from 'react-router-dom';
import { VENTAS_MOCK, USUARIOS_MOCK } from "../services/mockData";



export default function MisProductos() {
  const [productos, setProductos] = useState([]);
  const usuario = JSON.parse(localStorage.getItem("usuario"));
  const [pagina, setPagina] = useState(0);
  const [size] = useState(10);

  // Cargamos la lista solo cuando cargamos la vista
  useEffect(() => {
    async function getUserProductos() {
      const url = `/productos?page=${pagina}&size=${size}`;
      try {
        const response = await fetch(url, {
          headers: {
            // 'Authorization': `Bearer ${cookies.get('token')}`
          }
        });
        const body = await response.json();
        if(response.status === 404){
          // TODO cuando tengamos handlebars
        } else if(response.status !== 200){
          throw Error(body.message)
        }
        // Extraemos la lista del formato HATEOAS
        const lista = body._embedded?.productoResDTOList ?? [];

        // Filtramos por el vendedor actual
        const misProductos = lista.filter(p => p.vendedor.id == usuario.id);
        setProductos(misProductos);
      } catch (error){
        console.error(`Error al obtener los productos del usuario con ID ${usuario.id}:`, error);
      }
    }
    getUserProductos();
    
  }, []);

  return (
    <section className="container py-4">
      {/* Cabecera */}
      <div className="row align-items-center mb-4">
        <div className="col">
          <h2>Mis productos</h2>
        </div>
        <div className="col-auto">
          <span className="badge bg-primary fs-6">{productos.length} productos</span>
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
              </tr>
            </thead>
            <tbody>
              {productos.map(producto => (
                <tr key={producto.id}>
                  <td>{producto.titulo}</td>
                  <td className="col-oculta">{producto.categoria.nombre}</td>
                  <td>{producto.precio} €</td>
                  <td className="col-oculta">
                      {new Date(producto.fechaPubli).toLocaleDateString('es-ES', {
                        day: '2-digit', month: 'short', year: 'numeric',
                      })}
                  </td>
                  <td>
                    {producto.vendido
                      ? <span className="badge bg-success">Vendido</span>
                      : <span className="badge bg-warning text-dark">En venta</span>
                    }
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </section>
  );
}
