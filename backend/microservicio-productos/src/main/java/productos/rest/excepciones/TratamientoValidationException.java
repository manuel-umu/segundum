package productos.rest.excepciones;

import java.util.LinkedList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TratamientoValidationException {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<RespuestaError>> handleValidationException(MethodArgumentNotValidException ex) {
		List<RespuestaError> errores = new LinkedList<RespuestaError>();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			RespuestaError resp = new RespuestaError(((FieldError) error).getField(), error.getDefaultMessage());
			errores.add(resp);
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
	}
}
