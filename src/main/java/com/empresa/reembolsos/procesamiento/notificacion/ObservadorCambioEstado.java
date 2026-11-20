package com.empresa.reembolsos.procesamiento.notificacion;

import com.empresa.reembolsos.dominio.*;

/** Reacción de un interesado ante una transición confirmada. */
public interface ObservadorCambioEstado {
    void onCambioEstado(SolicitudReembolso solicitud, EstadoSolicitud anterior);
}
