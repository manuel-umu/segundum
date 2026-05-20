import '../App.css';

const AÑO = new Date().getFullYear();

export default function Footer() {
  return (
    <footer className="footer py-4 mt-auto">
      <div className="container">
        <div className="row gy-3 align-items-start">
          {/* Columna 1 — marca */}
          <div className="col-12 col-md-4">
            <h6 className="text-white fw-bold mb-1">2ª Mano</h6>
            <p className="mb-0">
              Plataforma de compraventa de productos de segunda mano.
            </p>
          </div>

          {/* Columna 2 — asignatura */}
          <div className="col-12 col-md-4">
            <h6 className="text-white fw-bold mb-1">Asignatura</h6>
            <p className="mb-0">Segundum</p>
            <p className="mb-0">Facultad de Informática</p>
            <p className="mb-0">Universidad de Murcia</p>
          </div>

          {/* Columna 3 — grupo */}
          <div className="col-12 col-md-4">
            <h6 className="text-white fw-bold mb-1">Grupo</h6>
            <p className="mb-0">Alumno 1 (placeholder)</p>
            <p className="mb-0">Alumno 2 (placeholder)</p>
          </div>
        </div>

        <hr className="footer-divider my-3" />

        <p className="text-center mb-0">
          &copy; {AÑO} 2ª Mano &mdash; Segundum 25/26 · Universidad de Murcia
        </p>
      </div>
    </footer>
  );
}
