const SESSION_KEY = 'Segundum_session_id';

// Usuario de prueba (luego conectamos el back de segundum)
export const MOCK_USERS = [
  {
    id: 1,
    nombre: 'Admin',
    apellidos: 'Sistema',
    email: 'admin@segundum.es',
    password: 'admin123',
    rol: 'admin',
    fechaRegistro: '2025-01-01',
  },
  {
    id: 2,
    nombre: 'Manuel',
    apellidos: 'Chica Piñera',
    email: 'manuel@segundum.es',
    password: 'user1234',
    rol: 'user',
    fechaRegistro: '2025-03-10',
  },
  {
    id: 3,
    nombre: 'Emilio',
    apellidos: 'González Fernández-Piqueras',
    email: 'emilio@segundum.es',
    password: 'user1234',
    rol: 'user',
    fechaRegistro: '2025-03-11',
  },
];

// Simula latencia de red
function delay() {
  return new Promise(r => setTimeout(r, 150));
}

function findUser(email, password) {
  return MOCK_USERS.find(u => u.email === email && u.password === password) ?? null;
}

function sanitize(user) {
  if (!user) return null;
  const { password: _, ...safe } = user;
  return safe;
}

export async function login(email, password) {
  await delay();
  const user = findUser(email, password);
  if (!user) throw new Error('Credenciales incorrectas');
  localStorage.setItem(SESSION_KEY, String(user.id));
  return { user: sanitize(user) };
}

export async function logout() {
  await delay();
  localStorage.removeItem(SESSION_KEY);
}

export async function me() {
  await delay();
  const id = localStorage.getItem(SESSION_KEY);
  if (!id) return null;
  const user = MOCK_USERS.find(u => u.id === Number(id)) ?? null;
  return sanitize(user);
}

export async function register({ nombre, apellidos, email, password }) {
  await delay();
  if (MOCK_USERS.some(u => u.email === email)) {
    throw new Error('Ya existe una cuenta con ese email.');
  }
  const newUser = {
    id: MOCK_USERS.length + 1,
    nombre,
    apellidos,
    email,
    password,
    rol: 'user',
    fechaRegistro: new Date().toISOString().slice(0, 10),
  };
  MOCK_USERS.push(newUser);
  localStorage.setItem(SESSION_KEY, String(newUser.id));
  return { user: sanitize(newUser) };
}
