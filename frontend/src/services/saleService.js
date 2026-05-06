import { VENTAS, PRODUCTOS } from './mockData';

// Array en memoria para que las mutaciones persistan durante la sesion
let ventas = [...VENTAS];
let nextId = ventas.length + 1;

// Pagina un array y devuelve la forma estandar de respuesta
function paginate(array, page = 1, pageSize = 10) {
  const total = array.length;
  const start = (page - 1) * pageSize;
  const items = array.slice(start, start + pageSize);
  return { items, page, pageSize, total };
}

// Crea una solicitud de compra para un producto
export async function requestPurchase(productId, buyerId) {
  await new Promise(r => setTimeout(r, 200));

  const product = PRODUCTOS.find(p => p.id === productId);
  if (!product) throw new Error('Producto no encontrado');

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

// Ventas donde el usuario es el vendedor
export async function listMySales(userId, { page = 1, pageSize = 10 } = {}) {
  await new Promise(r => setTimeout(r, 200));
  const mySales = ventas.filter(v => v.vendedor_id === userId);
  return paginate(mySales, page, pageSize);
}

// Compras donde el usuario es el comprador
export async function listMyPurchases(userId, { page = 1, pageSize = 10 } = {}) {
  await new Promise(r => setTimeout(r, 200));
  const myPurchases = ventas.filter(v => v.comprador_id === userId);
  return paginate(myPurchases, page, pageSize);
}

// Todas las ventas — solo admin
export async function listAllSales({ page = 1, pageSize = 10 } = {}) {
  await new Promise(r => setTimeout(r, 200));
  return paginate(ventas, page, pageSize);
}
