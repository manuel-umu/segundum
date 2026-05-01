package productos.rest.excepciones;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import productos.repositorios.RepositorioException;

@ControllerAdvice
public class TratamientoRepositoryException {

	@ExceptionHandler(RepositorioException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public RespuestaError handleGlobalException(RepositorioException ex) {
		return new RespuestaError("Bad Request", ex.getMessage());
	}
}
