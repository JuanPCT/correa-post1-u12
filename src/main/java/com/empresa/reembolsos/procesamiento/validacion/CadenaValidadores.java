package com.empresa.reembolsos.procesamiento.validacion;

import com.empresa.reembolsos.dominio.SolicitudReembolso;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class CadenaValidadores {
    private final List<ValidadorSolicitud> validadores;

    public CadenaValidadores(List<ValidadorSolicitud> validadores) {
        this.validadores = List.copyOf(validadores);
    }

    public Optional<String> validar(SolicitudReembolso solicitud, List<SolicitudReembolso> pendientes) {
        for (ValidadorSolicitud validador : validadores) {
            var error = validador.validar(solicitud, pendientes);
            if (error.isPresent()) return error;
        }
        return Optional.empty();
    }
}
