package com.empresa.reembolsos.procesamiento.notificacion;

import com.empresa.reembolsos.dominio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificadorAuditoria implements ObservadorCambioEstado {
    private static final Logger LOG = LoggerFactory.getLogger(NotificadorAuditoria.class);

    @Override
    public void onCambioEstado(SolicitudReembolso s, EstadoSolicitud anterior) {
        LOG.info("Auditoría: solicitud {} pasó de {} a {}", s.getId(), anterior, s.getEstado());
    }
}
