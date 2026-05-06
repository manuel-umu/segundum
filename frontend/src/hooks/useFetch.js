import { useState, useEffect } from 'react';

// Hook para llamadas asincronas: gestiona loading, error y data
export default function useFetch(asyncFn, deps = []) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    setError(null);

    asyncFn()
      .then(result => setData(result))
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));

  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, deps);

  return { data, loading, error };
}
