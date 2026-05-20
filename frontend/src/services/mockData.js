export const USUARIOS_MOCK = [
  {
    id: 1,
    nombre: 'Emilio',
    apellidos: 'González Fernández-Piqueras',
    email: 'emilio@segundum.es',
    num_compras: 3,
    num_ventas: 1,
  },
  {
    id: 2,
    nombre: 'Manuel',
    apellidos: 'Chica Piñera',
    email: 'manuel@segundum.es',
    num_compras: 1,
    num_ventas: 5,
  },
  {
    id: 3,
    nombre: 'Laura',
    apellidos: 'Martínez Ruiz',
    email: 'laura@segundum.es',
    num_compras: 0,
    num_ventas: 2,
  },
];

export const VENTAS_MOCK = [
  {
    id: 1,
    producto: 'iPhone 13 128GB Azul',
    precio: 550,
    comprador: 'Emilio González',
    vendedor: 'Manuel Chica',
    fecha: '2026-03-15',
  },
  {
    id: 2,
    producto: 'MacBook Pro 14" M1 Pro',
    precio: 1400,
    comprador: 'Manuel Chica',
    vendedor: 'Emilio González',
    fecha: '2026-04-02',
  },
  {
    id: 3,
    producto: 'Teclado mecánico Keychron K2',
    precio: 75,
    comprador: 'Emilio González',
    vendedor: 'Manuel Chica',
    fecha: '2026-05-10',
  },
];
