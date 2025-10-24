package aadd;

import java.time.LocalDate;

import servicios.FactoriaServicios;
import servicios.IServicioUsuarios;

public class Test {

	public static void main(String[] args) throws Exception{
		IServicioUsuarios servicio = FactoriaServicios.getServicio(IServicioUsuarios.class);
		String nombre = "Alphonse";
		String apellidos = "Apellido ap";
		String email = "gmail@gmail.com";
		LocalDate fecha = LocalDate.of(2000, 12, 1);
		String contra = "hola1234";
		String telefono = "666777888";
		String id = servicio.registrarUsuario(nombre, apellidos, email, fecha, contra, telefono);
		
		
	}
}
