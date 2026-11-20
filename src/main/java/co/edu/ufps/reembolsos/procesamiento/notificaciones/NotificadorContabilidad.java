package co.edu.ufps.reembolsos.procesamiento.notificaciones;
import co.edu.ufps.reembolsos.domain.CambioSolicitud;
import co.edu.ufps.reembolsos.domain.EstadoSolicitud;
import co.edu.ufps.reembolsos.domain.ObservadorReembolso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class NotificadorContabilidad implements ObservadorReembolso {
    private static final Logger LOG=LoggerFactory.getLogger(NotificadorContabilidad.class);
    public void alCambiarEstado(CambioSolicitud cambio){
        if(cambio.estadoNuevo()==EstadoSolicitud.APROBADA)LOG.info("Contabilidad: aprobar desembolso de solicitud {}",cambio.solicitudId());
    }
}
