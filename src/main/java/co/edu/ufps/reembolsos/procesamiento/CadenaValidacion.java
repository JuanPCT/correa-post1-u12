package co.edu.ufps.reembolsos.procesamiento;
import co.edu.ufps.reembolsos.domain.SolicitudReembolso;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
@Component
public class CadenaValidacion {
    private final List<ValidacionSolicitud> validadores;
    public CadenaValidacion(List<ValidacionSolicitud> validadores){this.validadores=List.copyOf(validadores);}
    public Optional<String> validar(SolicitudReembolso solicitud){
        for(ValidacionSolicitud validador:validadores){Optional<String> error=validador.validar(solicitud);if(error.isPresent())return error;}
        return Optional.empty();
    }
}
