import { VENTAS, PRODUCTOS } from './mockData';

const API = process.env.REACT_APP_API_URL || 'http://localhost:4000';

// In-memory store so mutations persist during the session
let ventas = [...VENTAS];
let nextId = ventas.length + 1;

// Helper: paginate an array and return the standard response shape
function paginate(array, page = 1, pageSize = 10) {
  const total = array.length;
  const start = (page - 1) * pageSize;
  const items = array.slice(start, start + pageSize);
  return { items, page, pageSize, total };
}

// Creates a purchase request for a product; throws if already requested
export async function requestPurchase(productId, buyerId) {
  await new Promise(r => setTimeout(r, 200));

  const product = PRODUCTOS.find(p => p.id === productId);
  if (!product) throw new Error('Producto no encontrado');

  const alreadyRequested = ventas.find(
    v => v.producto_id === productId && v.comprador_id === buyerId && v.estado === 'solicitada'
  );
  if (alreadyRequested) throw new Error('Ya has solicitado este producto');

  const nueva = {
    id: nextId++,
    producto_id: productId,
    comprador_id: buyerId,
    vendedor_id: product.vendedor_id,
    fecha_solicitud: new Date().toISOString().slice(0, 10),
    estado: 'solicitada',
  };

  ventas.push(nueva);
  return { ...nueva };
}

// Sales where the user is the seller
export async function listMySales(userId, { page = 1, pageSize = 10 } = {}) {
  await new Promise(r => setTimeout(r, 200));

  const mySales = ventas
    .filter(v => v.vendedor_id === userId)
    .sort((a, b) => b.fecha_solicitud.localeCompare(a.fecha_solicitud));

  return paginate(mySales, page, pageSize);
}

// Purchases where the user is the buyer
export async function listMyPurchases(userId, { page = 1, pageSize = 10 } = {}) {
  await new Promise(r => setTimeout(r, 200));

  const myPurchases = ventas
    .filter(v => v.comprador_id === userId)
    .sort((a, b) => b.fecha_solicitud.localeCompare(a.fecha_solicitud));

  return paginate(myPurchases, page, pageSize);
}

// All sales — admin only
export async function listAllSales({ page = 1, pageSize = 10 } = {}) {
  await new Promise(r => setTimeout(r, 200));

  const sorted = [...ventas].sort((a, b) =>
    b.fecha_solicitud.localeCompare(a.fecha_solicitud)
  );

  return paginate(sorted, page, pageSize);
}
