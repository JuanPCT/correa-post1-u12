package co.edu.ufps.reembolsos.adaptadores.web;
import co.edu.ufps.reembolsos.domain.SolicitudNoEncontrada;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class ErroresApi {
    @ExceptionHandler(SolicitudNoEncontrada.class) ResponseEntity<Map<String,String>> noEncontrada(RuntimeException e){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error",e.getMessage()));}
    @ExceptionHandler({IllegalArgumentException.class,IllegalStateException.class}) ResponseEntity<Map<String,String>> solicitudInvalida(RuntimeException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}
}
