package co.edu.ufps.reembolsos.procesamiento.notificaciones;
import co.edu.ufps.reembolsos.domain.CambioSolicitud;
import co.edu.ufps.reembolsos.domain.ObservadorReembolso;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class NotificadorEmpleado implements ObservadorReembolso {
    private static final Logger LOG=LoggerFactory.getLogger(NotificadorEmpleado.class);
    public void alCambiarEstado(CambioSolicitud cambio){LOG.info("Empleado {}: solicitud {} pasó a {}",cambio.empleado(),cambio.solicitudId(),cambio.estadoNuevo());}
}
