package web;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import controlador.Controlador;
import servicios.FactoriaServicios;
import servicios.IServicioUsuarios;

@SuppressWarnings("serial")
@Named("ControladorWeb")
@ViewScoped
public class ControladorWeb implements Serializable {
	private final String REDIRECT = "?faces-redirect=true";
	private String email, password, nombre, apellidos, telefono;
	private LocalDate fechaNac;
	private Controlador controlador;

	public ControladorWeb() {
		controlador = Controlador.getUnicaInstancia();
	}

	/*
	 * Funcionalidad
	 */
	public String login() {
		if (controlador.login(email, password)) {
			return IrAPrincipal();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Credenciales incorrectas."));
			return null;
		}
	}

	public String registro() {
		if (controlador.registro(nombre, apellidos, email, fechaNac, password, telefono)) {
			return irALogin();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Se ha encontrado un problema en el registro."));
			return null;
		}

	}

	/*
	 * Funciones para movimiento entre ventanas
	 */
	public String irALogin() {
		return "login" + REDIRECT;
	}

	public String irARegistro() {
		return "registro" + REDIRECT;
	}
	
	public String IrAPrincipal() {
		return "index" + REDIRECT;
	}

	/*
	 * Getters & Setters
	 */
	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String contraseña) {
		this.password = contraseña;
	}
}