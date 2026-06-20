import { useState, useEffect } from "react";

export default function AdminUsuarios() {
  const [usuarios, setUsuarios] = useState([]);

  // Paginacion
  const SIZE = 10;
  const [pagina, setPagina] = useState(0);
  const totalPaginas = Math.ceil(usuarios.length / SIZE);
  const usuariosPagina = usuarios.slice(pagina * SIZE, pagina * SIZE + SIZE);

  function paginaAnterior() {
    if (pagina > 0) setPagina(pagina - 1);
  }

  function paginaSiguiente() {
    if (pagina < totalPaginas - 1) setPagina(pagina + 1);
  }

  // Cargamos la lista solo cuando cargamos la vista
  useEffect(() => {
    async function getAllUsuarios() {
      const url = `/usuarios`;
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
        setUsuarios(body);
      } catch (error){
        console.error("Error al obtener la lista de usuarios:", error);
        window.location.href = "/error/500";
      }
    }
    getAllUsuarios();
  }, []);

  return (
    <section className="container py-4">
      {/* Cabecera */}
      <div className="row align-items-center mb-4">
        <div className="col">
          <h2>Usuarios registrados</h2>
        </div>
        <div className="col-auto">
          <span className="badge bg-primary fs-6">
            {usuarios.length} usuarios
          </span>
        </div>
      </div>

      {/* Mensaje vacío */}
      {usuarios.length === 0 && (
        <p className="text-center text-muted py-4">
          No hay usuarios registrados
        </p>
      )}

      {/* Tabla */}
      {usuarios.length > 0 && (
        <div className="table-responsive">
          <table className="table table-hover table-striped text-center"
            style={{ tableLayout: 'fixed', width: '100%' }}
          >
            <thead className="table-dark">
              <tr>
                <th scope="col" style={{ width: "15%" }}>Nombre</th>
                <th className="col-ocultar" scope="col" style={{ width: "30%" }}>Apellidos</th>
                <th className="col-ocultar" scope="col" style={{ width: "25%" }}>Email</th>
                <th scope="col" style={{ width: "15%" }}>Nº compras</th>
                <th scope="col" style={{ width: "15%" }}>Nº ventas</th>
              </tr>
            </thead>
            <tbody>
              {usuariosPagina.map((usuario) => (
                <tr key={usuario.uri}>
                  <td>{usuario.nombre}</td>
                  <td className="col-ocultar">{usuario.apellidos}</td>
                  <td className="col-ocultar">{usuario.email}</td>
                  <td>{usuario.contCompras}</td>
                  <td>{usuario.contVentas}</td>
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
    </section>
  );
}
