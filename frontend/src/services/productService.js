import { PRODUCTOS, VENTAS } from './mockData';

const API = process.env.REACT_APP_API_URL;

function delay() {
  return new Promise(r => setTimeout(r, 200));
}

function paginate(array, page, pageSize) {
  const p = Math.max(1, Number(page) || 1);
  const ps = Math.max(1, Number(pageSize) || 10);
  const start = (p - 1) * ps;
  return {
    items: array.slice(start, start + ps),
    page: p,
    pageSize: ps,
    total: array.length,
  };
}

function applySort(array, sort) {
  const [campo, orden] = (sort ?? 'fecha_publicacion:desc').split(':');
  return [...array].sort((a, b) => {
    let va = a[campo];
    let vb = b[campo];
    if (typeof va === 'string') va = va.toLowerCase();
    if (typeof vb === 'string') vb = vb.toLowerCase();
    if (va < vb) return orden === 'asc' ? -1 : 1;
    if (va > vb) return orden === 'asc' ? 1 : -1;
    return 0;
  });
}

// Ids de productos que ya tienen una venta completada
function soldProductIds() {
  return new Set(
    VENTAS.filter(v => v.estado === 'completada').map(v => v.producto_id)
  );
}

export async function listProducts({
  page = 1,
  pageSize = 10,
  categoria,
  descripcion,
  estado,
  precioMax,
  sort,
} = {}) {
  await delay();

  const vendidos = soldProductIds();
  let result = PRODUCTOS.filter(p => !vendidos.has(p.id));

  if (categoria) result = result.filter(p => p.categoria_id === Number(categoria));
  if (estado) result = result.filter(p => p.estado === estado);
  if (precioMax) result = result.filter(p => p.precio <= Number(precioMax));
  if (descripcion) {
    const term = descripcion.toLowerCase();
    result = result.filter(p =>
      p.titulo.toLowerCase().includes(term) ||
      p.descripcion.toLowerCase().includes(term)
    );
  }

  result = applySort(result, sort);
  return paginate(result, page, pageSize);
}

export async function getProduct(id) {
  await delay();
  const product = PRODUCTOS.find(p => p.id === Number(id));
  if (!product) throw new Error('Producto no encontrado');
  product.num_visualizaciones += 1;
  return { ...product };
}

export async function createProduct(data) {
  await delay();
  const newProduct = {
    ...data,
    id: PRODUCTOS.length + 1,
    fecha_publicacion: new Date().toISOString().slice(0, 10),
    num_visualizaciones: 0,
    imagen: `https://picsum.photos/seed/prod${PRODUCTOS.length + 1}/400/300`,
  };
  PRODUCTOS.push(newProduct);
  return { ...newProduct };
}

export async function updateProduct(id, data) {
  await delay();
  const index = PRODUCTOS.findIndex(p => p.id === Number(id));
  if (index === -1) throw new Error('Producto no encontrado');
  PRODUCTOS[index] = { ...PRODUCTOS[index], ...data };
  return { ...PRODUCTOS[index] };
}

export async function deleteProduct(id) {
  await delay();
  const index = PRODUCTOS.findIndex(p => p.id === Number(id));
  if (index === -1) throw new Error('Producto no encontrado');
  PRODUCTOS.splice(index, 1);
}

export async function listMyProducts(userId, { page = 1, pageSize = 10, filter = 'en_venta' } = {}) {
  await delay();
  const vendidos = soldProductIds();

  let result = PRODUCTOS.filter(p => {
    if (p.vendedor_id !== Number(userId)) return false;
    return filter === 'vendidos' ? vendidos.has(p.id) : !vendidos.has(p.id);
  });

  result = applySort(result, 'fecha_publicacion:desc');
  return paginate(result, page, pageSize);
}
