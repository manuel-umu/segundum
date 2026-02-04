package saludo.soap;

import es.um.segundum.Saludo;
import es.um.segundum.SaludoImplService;

public class Programa {

	public static void main(String[] args) {
		
		SaludoImplService servicio = new SaludoImplService();
		Saludo puerto = servicio.getSaludoImplPort();
		System.out.println(puerto.getSaludo("Pepe"));
	}
}
