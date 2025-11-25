package web;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class AltaSegundumWeb implements Serializable {
	private String email, password;

	private IServicioUsuarios servicioUsuario;

	public AltaSegundumWeb() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String contraseña) {
		this.password = contraseña;
	}

}