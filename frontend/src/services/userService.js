import { MOCK_USERS } from './authService';

const API = process.env.REACT_APP_API_URL || 'http://localhost:4000';

// Pagina un array y devuelve la forma estandar de respuesta
function paginate(array, page = 1, pageSize = 10) {
  const total = array.length;
  const start = (page - 1) * pageSize;
  const items = array.slice(start, start + pageSize);
  return { items, page, pageSize, total };
}

// Devuelve todos los usuarios registrados — solo admin
export async function listAllUsers({ page = 1, pageSize = 10 } = {}) {
  await new Promise(r => setTimeout(r, 200));

  // Nunca exponer las contrasenas a la UI
  const safe = MOCK_USERS.map(({ password, ...rest }) => rest);
  return paginate(safe, page, pageSize);
}

// Actualiza los campos del perfil del usuario autenticado
export async function updateProfile(userId, data) {
  await new Promise(r => setTimeout(r, 200));

  const index = MOCK_USERS.findIndex(u => u.id === userId);
  if (index === -1) throw new Error('Usuario no encontrado');

  // Ignorar rol, password e id para evitar escalada de privilegios
  const { rol, password, id, ...allowed } = data;

  Object.assign(MOCK_USERS[index], allowed);
  const { password: _pwd, ...updated } = MOCK_USERS[index];
  return { ...updated };
}
