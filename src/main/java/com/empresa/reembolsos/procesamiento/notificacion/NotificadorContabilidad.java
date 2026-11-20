package com.empresa.reembolsos.procesamiento.notificacion;

import com.empresa.reembolsos.dominio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificadorContabilidad implements ObservadorCambioEstado {
    private static final Logger LOG = LoggerFactory.getLogger(NotificadorContabilidad.class);

    @Override
    public void onCambioEstado(SolicitudReembolso s, EstadoSolicitud anterior) {
        if (s.getEstado() == EstadoSolicitud.APROBADA) {
            LOG.info("Contabilidad: programar pago de {} para {}", s.getMontoAprobado(), s.getEmpleadoId());
        }
    }
}
