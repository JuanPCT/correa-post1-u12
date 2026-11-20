package com.empresa.reembolsos;

import com.empresa.reembolsos.dominio.*;
import com.empresa.reembolsos.procesamiento.calculo.*;
import com.empresa.reembolsos.procesamiento.validacion.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProcesamientoTest {
    @Test void exigeDocumentos() {
        assertTrue(new ValidadorDocumentos().validar(solicitud("80000", 1, false, 2026), List.of()).isPresent());
    }

    @Test void montoAplicaTopeDiarioPorDiasDeViaje() {
        assertTrue(new ValidadorMonto().validar(solicitud("300001", 2, true, 2026), List.of()).isPresent());
        assertTrue(new ValidadorMonto().validar(solicitud("300000", 2, true, 2026), List.of()).isEmpty());
    }

    @Test void duplicadoCoincideEnMesYAnio() {
        var regla = new ValidadorDuplicados();
        var actual = solicitud("80000", 1, true, 2026);
        assertTrue(regla.validar(actual, List.of(solicitud("90000", 1, true, 2026))).isPresent());
        assertTrue(regla.validar(actual, List.of(solicitud("90000", 1, true, 2025))).isEmpty());
    }

    @Test void viajeLimitaAlTotalPorDias() {
        assertEquals(new BigDecimal("300000.00"), new CalculadoraViaje().calcular(solicitud("350000", 2, true, 2026)));
    }

    @Test void representacionReembolsaHastaElTopeMensual() {
        assertEquals(new BigDecimal("2000000.00"), new CalculadoraRepresentacion().calcular(solicitud("2010000", 1, true, 2026)));
    }

    @Test void capacitacionReconoceOchentaPorCientoConTope() {
        var estrategia = new CalculadoraCapacitacion();
        assertEquals(new BigDecimal("80000.00"), estrategia.calcular(solicitud("100000", 1, true, 2026)));
        assertEquals(new BigDecimal("3000000.00"), estrategia.calcular(solicitud("4000000", 1, true, 2026)));
    }

    private SolicitudReembolso solicitud(String monto, int dias, boolean documentos, int anio) {
        return new SolicitudReembolso("EMP-1", CategoriaGasto.VIAJE, new BigDecimal(monto),
                LocalDate.of(anio, 11, 20), dias, documentos);
    }
}
