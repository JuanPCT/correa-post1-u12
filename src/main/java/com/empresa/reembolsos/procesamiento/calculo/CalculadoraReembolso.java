package com.empresa.reembolsos.procesamiento.calculo;

import com.empresa.reembolsos.dominio.*;
import java.math.BigDecimal;

/** Fórmula de reembolso de una categoría de gasto. */
public interface CalculadoraReembolso {
    CategoriaGasto categoria();
    BigDecimal calcular(SolicitudReembolso solicitud);
}
