package com.empresa.reembolsos.procesamiento.notificacion;

import com.empresa.reembolsos.dominio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificadorEmpleado implements ObservadorCambioEstado {
    private static final Logger LOG = LoggerFactory.getLogger(NotificadorEmpleado.class);

    @Override
    public void onCambioEstado(SolicitudReembolso s, EstadoSolicitud anterior) {
        LOG.info("Empleado {}: solicitud {} pasó a {}", s.getEmpleadoId(), s.getId(), s.getEstado());
    }
}
