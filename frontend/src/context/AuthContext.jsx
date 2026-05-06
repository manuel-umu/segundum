import { createContext, useState, useEffect } from 'react';
import * as authService from '../services/authService';

export const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Al montar, comprueba si hay sesion activa
  useEffect(() => {
    authService.me()
      .then(u => setUser(u))
      .finally(() => setLoading(false));
  }, []);

  async function login(email, password) {
    const { user: loggedIn } = await authService.login(email, password);
    setUser(loggedIn);
  }

  async function logout() {
    await authService.logout();
    setUser(null);
  }

  async function register(data) {
    const { user: created } = await authService.register(data);
    setUser(created);
  }

  // Recarga el usuario desde el servicio tras modificar el perfil
  async function refreshUser() {
    const updated = await authService.me();
    setUser(updated);
  }

  const value = {
    user,
    loading,
    isAuthenticated: !!user,
    isAdmin: user?.rol === 'admin',
    login,
    logout,
    register,
    refreshUser,
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
}
