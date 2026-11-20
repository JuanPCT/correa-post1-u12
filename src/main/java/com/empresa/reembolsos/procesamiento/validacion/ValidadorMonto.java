package com.empresa.reembolsos.procesamiento.validacion;

import com.empresa.reembolsos.dominio.SolicitudReembolso;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class ValidadorMonto implements ValidadorSolicitud {
    @Override
    public Optional<String> validar(SolicitudReembolso s, List<SolicitudReembolso> pendientes) {
        if (s.getMontoSolicitado() == null || s.getMontoSolicitado().signum() <= 0) {
            return Optional.of("El monto solicitado debe ser positivo");
        }
        BigDecimal limite = switch (s.getCategoria()) {
            case VIAJE -> new BigDecimal("150000").multiply(BigDecimal.valueOf(s.getDiasViaje()));
            case REPRESENTACION -> new BigDecimal("2000000");
            case CAPACITACION -> new BigDecimal("3000000");
        };
        return s.getMontoSolicitado().compareTo(limite) > 0
                ? Optional.of("Monto excede el tope de la categoría") : Optional.empty();
    }
}
