package segundum.rest.excepciones;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import segundum.repositorios.EntidadNoEncontrada;
import segundum.repositorios.RepositorioException;

@ControllerAdvice
public class TratamientoNotFoundException {

	@ExceptionHandler(EntidadNoEncontrada.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RespuestaError handleGlobalException(EntidadNoEncontrada ex) {
		return new RespuestaError("Bad Request", ex.getMessage());
	}
}
