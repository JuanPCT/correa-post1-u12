package com.empresa.reembolsos.procesamiento.calculo;

import com.empresa.reembolsos.dominio.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;

@Component
public class CalculadoraRepresentacion implements CalculadoraReembolso {
    @Override public CategoriaGasto categoria() { return CategoriaGasto.REPRESENTACION; }

    @Override
    public BigDecimal calcular(SolicitudReembolso solicitud) {
        BigDecimal monto = solicitud.getMontoSolicitado();
        return monto.min(new BigDecimal("2000000")).setScale(2, RoundingMode.HALF_UP);
    }
}
