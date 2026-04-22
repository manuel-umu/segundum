import { CATEGORIAS } from './mockData';

const API = process.env.REACT_APP_API_URL || 'http://localhost:4000';

// Returns all available categories
export async function listCategories() {
  // Simulate network latency
  await new Promise(r => setTimeout(r, 150));
  return [...CATEGORIAS];
}
