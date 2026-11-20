package co.edu.ufps.reembolsos.procesamiento;
import co.edu.ufps.reembolsos.domain.SolicitudReembolso;
import co.edu.ufps.reembolsos.domain.SolicitudRepositoryPort;
import java.util.Optional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Component @Order(3)
public class ValidarDuplicado implements ValidacionSolicitud {
    private final SolicitudRepositoryPort repository;
    public ValidarDuplicado(SolicitudRepositoryPort repository){this.repository=repository;}
    public Optional<String> validar(SolicitudReembolso solicitud){
        String clave=solicitud.getClaveDuplicado();
        if(clave==null||clave.isBlank())return Optional.of("La clave del soporte es obligatoria");
        return repository.existeClaveDuplicada(clave)?Optional.of("El soporte ya fue radicado"):Optional.empty();
    }
}
