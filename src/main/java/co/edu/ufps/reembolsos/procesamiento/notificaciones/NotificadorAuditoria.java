package co.edu.ufps.reembolsos.procesamiento.notificaciones;
import co.edu.ufps.reembolsos.domain.CambioSolicitud;
import co.edu.ufps.reembolsos.domain.ObservadorReembolso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class NotificadorAuditoria implements ObservadorReembolso {
    private static final Logger LOG=LoggerFactory.getLogger(NotificadorAuditoria.class);
    public void alCambiarEstado(CambioSolicitud cambio){LOG.info("AUDIT solicitud {}: {} -> {}",cambio.solicitudId(),cambio.estadoAnterior(),cambio.estadoNuevo());}
}
