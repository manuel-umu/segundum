package segundum;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {
	private static final String URL_BASE = "http://localhost:8080/usuarios/";
	
	
	
	
	public static void main(String[] args) throws Exception {
		HttpURLConnection cliente = (HttpURLConnection) new URL(URL_BASE).openConnection();
		cliente.setRequestMethod("POST");
		cliente.setDoOutput(true);
		cliente.setRequestProperty("Content-Type", "application/json");

		PrintWriter writer = new PrintWriter(cliente.getOutputStream());
		writer.print("");
		writer.flush();
		writer.close();
		// Procesamos la respuesta
		int codigoRespuesta = cliente.getResponseCode();
		System.out.println("Respuesta HTTP: " + codigoRespuesta);
	}
}
