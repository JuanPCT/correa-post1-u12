package com.empresa.reembolsos.procesamiento.calculo;

import com.empresa.reembolsos.dominio.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;

@Component
public class CalculadoraCapacitacion implements CalculadoraReembolso {
    @Override public CategoriaGasto categoria() { return CategoriaGasto.CAPACITACION; }

    @Override
    public BigDecimal calcular(SolicitudReembolso solicitud) {
        BigDecimal monto = solicitud.getMontoSolicitado();
        return monto.multiply(new BigDecimal("0.80")).min(new BigDecimal("3000000")).setScale(2, RoundingMode.HALF_UP);
    }
}
