package com.empresa.reembolsos.procesamiento.calculo;

import com.empresa.reembolsos.dominio.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;

@Component
public class CalculadoraViaje implements CalculadoraReembolso {
    @Override public CategoriaGasto categoria() { return CategoriaGasto.VIAJE; }

    @Override
    public BigDecimal calcular(SolicitudReembolso solicitud) {
        BigDecimal monto = solicitud.getMontoSolicitado();
        return monto.min(new BigDecimal("150000").multiply(BigDecimal.valueOf(solicitud.getDiasViaje()))).setScale(2, RoundingMode.HALF_UP);
    }
}
