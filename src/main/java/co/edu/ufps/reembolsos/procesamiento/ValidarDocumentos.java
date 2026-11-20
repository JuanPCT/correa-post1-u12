package co.edu.ufps.reembolsos.procesamiento;
import co.edu.ufps.reembolsos.domain.SolicitudReembolso;
import java.util.Optional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Component @Order(1)
public class ValidarDocumentos implements ValidacionSolicitud {
    public Optional<String> validar(SolicitudReembolso solicitud){return solicitud.tieneDocumentosCompletos()?Optional.empty():Optional.of("Faltan soportes obligatorios");}
}
