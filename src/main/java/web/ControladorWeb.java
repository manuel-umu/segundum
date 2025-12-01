package web;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import servicios.FactoriaServicios;
import servicios.IServicioUsuarios;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class ControladorWeb implements Serializable {
	private String email, password, nombre, apellidos,telefono;
	private LocalDate fechaNac;

	private IServicioUsuarios servicioUsuario;

	public ControladorWeb() {
		servicioUsuario = FactoriaServicios.getServicio(IServicioUsuarios.class);
	}

	public String login() {
		// comprobación de campos
		if(email.equals("hola@a.a")) {		// Cambiar por el login del repo
		//if (email.isEmpty() || password.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Credenciales incorrectas."));
			return null;
		} else {
			return "index?faces-redirect=true";
		}
	}
	
	public String registro() {
		return "index";
	}
	
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
	
	
	public String irALogin() {
		return "login";
	}
	
	public String irARegistro() {
		return "registro";
	}

}