package controlador;

import java.time.LocalDate;

import modelo.Usuario;
import repositorios.RepositorioException;
import servicios.FactoriaServicios;
import servicios.IServicioUsuarios;

public class Controlador {
	private static Controlador unicaInstancia;
	private Usuario usuarioActual;
	private IServicioUsuarios servUser;

	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null) {
			unicaInstancia = new Controlador();
		}

		return unicaInstancia;
	}

	public Controlador() {
		servUser = FactoriaServicios.getServicio(IServicioUsuarios.class);
	}

	/*
	 * Funcionalidad
	 */

	public boolean login(String email, String contra) {
		try {
			return (servUser.login(email, contra)); // El servicio ya rellena usuarioActual
		} catch (Exception e) {
			System.err.println("Error login");
		}
		return false;
	}

	public boolean registro(String nombre, String apellidos, String email, LocalDate fecha, String clave,
			String telefono) {
		try {
			if (servUser.registrarUsuario(nombre, apellidos, email, fecha, clave, telefono) != null) {
				return true;
			}
			return false;
		} catch (RepositorioException e) { // Si lanza una IllegalArgument se propaga
			return false;
		}
	}

	/*
	 * Getters & Setters
	 */
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
	}
}
