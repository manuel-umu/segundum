import '../App.css';

const AÑO = new Date().getFullYear();

export default function Footer() {
  return (
    <footer className="footer py-4 mt-auto">
      <div className="container">
        <div className="row gy-3 align-items-start">

          {/* Marca */}
          <div className="col-12 col-md-4">
            <h6 className="text-white fw-bold mb-1">SegundUM</h6>
            <p className="mb-0">
              Plataforma de compraventa de productos de segunda mano.
            </p>
          </div>

          {/* Info */}
          <div className="col-12 col-md-4">
            <h6 className="text-white fw-bold mb-1">Asignatura</h6>
            <p className="mb-0">Segundum</p>
            <p className="mb-0">Facultad de Informática</p>
            <p className="mb-0">Universidad de Murcia</p>
          </div>

          {/* Grupo*/}
          <div className="col-12 col-md-4">
            <h6 className="text-white fw-bold mb-1">Grupo</h6>
            <p className="mb-0">Manuel Chica Piñera</p>
            <p className="mb-0">Emilio González Fernández-Piqueras</p>
          </div>
        </div>

        <hr className="footer-divider my-3" />

        {/* Copy */}
        <p className="text-center mb-0">
          &copy; {AÑO} SegundUM · Universidad de Murcia
        </p>
      </div>
    </footer>
  );
}
