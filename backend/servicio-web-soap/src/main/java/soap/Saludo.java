package soap;

import javax.jws.WebService;

@WebService(targetNamespace = "http://um.es/segundum")
public interface Saludo {

	String getSaludo(String nombre);
}