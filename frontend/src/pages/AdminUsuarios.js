import { useState, useEffect } from "react";

export default function AdminUsuarios() {
  const [usuarios, setUsuarios] = useState([]);

  // Cargamos la lista solo cuando cargamos la vista
  useEffect(() => {
    async function getAllUsuarios() {
      const url = `/usuarios`;
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
        setUsuarios(body);
      } catch (error){
        console.error("Error al obtener la lista de usuarios:", error);
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
          <table className="table table-hover table-striped text-center">
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
              {usuarios.map((usuario) => (
                <tr key={usuario.id}>
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
    </section>
  );
}
