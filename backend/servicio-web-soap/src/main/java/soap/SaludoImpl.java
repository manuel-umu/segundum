package soap;

import javax.jws.WebService;

@WebService(endpointInterface = "soap.Saludo", targetNamespace = "http://um.es/segundum")
public class SaludoImpl implements Saludo {
	
	@Override
	public String getSaludo(String nombre) {
		return "Hola " + nombre;
	}
}