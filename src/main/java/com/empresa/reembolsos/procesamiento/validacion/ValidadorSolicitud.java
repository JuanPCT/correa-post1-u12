package com.empresa.reembolsos.procesamiento.validacion;

import com.empresa.reembolsos.dominio.SolicitudReembolso;
import java.util.List;
import java.util.Optional;

/** Verifica una condición independiente para radicar una solicitud. */
public interface ValidadorSolicitud {
    Optional<String> validar(SolicitudReembolso solicitud, List<SolicitudReembolso> pendientes);
}
