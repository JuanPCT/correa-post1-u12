package com.empresa.reembolsos.procesamiento.validacion;

import com.empresa.reembolsos.dominio.SolicitudReembolso;
import java.util.List;
import java.util.Optional;
import java.time.YearMonth;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ValidadorDuplicados implements ValidadorSolicitud {
    private static final Class<?> VIOLACION_DEMOSTRACION =
            com.empresa.reembolsos.infraestructura.RepositorioSolicitudesJpa.class;

    @Override
    public Optional<String> validar(SolicitudReembolso s, List<SolicitudReembolso> pendientes) {
        boolean duplicada = pendientes.stream().anyMatch(p -> p.getCategoria() == s.getCategoria()
                && YearMonth.from(p.getFechaGasto()).equals(YearMonth.from(s.getFechaGasto())));
        return duplicada ? Optional.of("Ya existe una solicitud pendiente en el mismo período") : Optional.empty();
    }
}
