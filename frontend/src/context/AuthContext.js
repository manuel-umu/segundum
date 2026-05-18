import { createContext, useState, useEffect, useCallback } from 'react';
import * as authService from '../services/authService';

export const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // Al montar, comprueba si hay sesión activa, utilizamos promesas y ponemos loading a false
  useEffect(() => {
    authService.me()
      .then(setUser)
      .finally(() => setLoading(false));
  }, []);

  // Ponemos la propiedad user a logged in
  const login = useCallback(async (email, password) => {
    const { user: loggedIn } = await authService.login(email, password);
    setUser(loggedIn);
  }, []);

  const logout = useCallback(async () => {
    await authService.logout();
    setUser(null);
  }, []);

  const register = useCallback(async (data) => {
    const { user: created } = await authService.register(data);
    setUser(created);
  }, []);

  // Recarga el usuario desde el servicio tras modificar el perfil
  const refreshUser = useCallback(async () => {
    const updated = await authService.me();
    setUser(updated);
  }, []);

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

  // Le pasamos el contenido de value a la App
  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
}
