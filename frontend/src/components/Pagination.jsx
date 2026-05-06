// Componente de paginacion reutilizable
export default function Pagination({ page, pageSize, total, onChange }) {
  const totalPages = Math.ceil(total / pageSize);

  if (totalPages <= 1) return null;

  // Genera un array con todos los numeros de pagina
  const pages = Array.from({ length: totalPages }, (_, i) => i + 1);

  return (
    <nav aria-label='Navegacion por paginas'>
      <ul className='pagination justify-content-center flex-wrap'>

        {/* Boton anterior */}
        <li className={`page-item ${page === 1 ? 'disabled' : ''}`}>
          <button className='page-link' onClick={() => onChange(page - 1)} disabled={page === 1}>
            &laquo;
          </button>
        </li>

        {/* Paginas numeradas */}
        {pages.map(p => (
          <li className={`page-item ${p === page ? 'active' : ''}`} key={p}>
            <button className='page-link' onClick={() => onChange(p)}>
              {p}
            </button>
          </li>
        ))}

        {/* Boton siguiente */}
        <li className={`page-item ${page === totalPages ? 'disabled' : ''}`}>
          <button className='page-link' onClick={() => onChange(page + 1)} disabled={page === totalPages}>
            &raquo;
          </button>
        </li>

      </ul>
    </nav>
  );
}
