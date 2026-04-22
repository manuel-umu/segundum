// Genera la lista de paginas a mostrar: primero, ultimo, paginas cercanas (+-2) y ellipsis
function buildPages(page, totalPages) {
  const pages = [];
  const delta = 2;

  for (let i = 1; i <= totalPages; i++) {
    if (
      i === 1 ||
      i === totalPages ||
      (i >= page - delta && i <= page + delta)
    ) {
      pages.push(i);
    }
  }

  // Insertar '...' donde haya saltos de mas de 1
  const result = [];
  let prev = null;
  for (const p of pages) {
    if (prev !== null && p - prev > 1) {
      result.push('...');
    }
    result.push(p);
    prev = p;
  }

  return result;
}

export default function Pagination({ page, pageSize, total, onChange }) {
  const totalPages = Math.ceil(total / pageSize);

  if (totalPages <= 1) return null;

  const pages = buildPages(page, totalPages);

  return (
    <nav aria-label='Navegacion por paginas'>
      <ul className='pagination justify-content-center flex-wrap'>

        {/* Boton anterior */}
        <li className={`page-item ${page === 1 ? 'disabled' : ''}`}>
          <button
            className='page-link'
            onClick={() => onChange(page - 1)}
            aria-label='Pagina anterior'
            disabled={page === 1}
          >
            &laquo;
          </button>
        </li>

        {/* Paginas numeradas con ellipsis */}
        {pages.map((p, index) =>
          p === '...' ? (
            <li className='page-item disabled' key={`ellipsis-${index}`}>
              <span className='page-link'>…</span>
            </li>
          ) : (
            <li
              className={`page-item ${p === page ? 'active' : ''}`}
              key={p}
              aria-current={p === page ? 'page' : undefined}
            >
              <button className='page-link' onClick={() => onChange(p)}>
                {p}
              </button>
            </li>
          )
        )}

        {/* Boton siguiente */}
        <li className={`page-item ${page === totalPages ? 'disabled' : ''}`}>
          <button
            className='page-link'
            onClick={() => onChange(page + 1)}
            aria-label='Pagina siguiente'
            disabled={page === totalPages}
          >
            &raquo;
          </button>
        </li>

      </ul>
    </nav>
  );
}
