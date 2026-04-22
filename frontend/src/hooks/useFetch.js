import { useState, useEffect, useCallback } from 'react';

// Hook generico para llamadas asincronas: gestiona loading, error y data
export default function useFetch(asyncFn, deps = []) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Permite disparar la llamada manualmente desde el componente
  const [tick, setTick] = useState(0);
  const refetch = useCallback(() => setTick(t => t + 1), []);

  useEffect(() => {
    let cancelled = false;

    setLoading(true);
    setError(null);

    asyncFn()
      .then(result => {
        if (!cancelled) setData(result);
      })
      .catch(err => {
        if (!cancelled) setError(err.message ?? 'Error desconocido');
      })
      .finally(() => {
        if (!cancelled) setLoading(false);
      });

    // Evita actualizaciones de estado si el componente se desmonta antes de que termine
    return () => { cancelled = true; };
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [tick, ...deps]);

  return { data, loading, error, refetch };
}
