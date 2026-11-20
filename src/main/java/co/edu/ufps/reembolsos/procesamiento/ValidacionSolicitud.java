package co.edu.ufps.reembolsos.procesamiento;
import co.edu.ufps.reembolsos.domain.SolicitudReembolso;
import java.util.Optional;
public interface ValidacionSolicitud { Optional<String> validar(SolicitudReembolso solicitud); }
