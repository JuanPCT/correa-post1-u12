package com.empresa.reembolsos.procesamiento.validacion;

import com.empresa.reembolsos.dominio.SolicitudReembolso;
import java.util.List;
import java.util.Optional;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class ValidadorDocumentos implements ValidadorSolicitud {
    @Override
    public Optional<String> validar(SolicitudReembolso s, List<SolicitudReembolso> pendientes) {
        return s.isDocumentosSoporte() ? Optional.empty() : Optional.of("Faltan documentos soporte");
    }
}
