package com.empresa.reembolsos;

import com.empresa.reembolsos.aplicacion.SolicitudReembolsoService;
import com.empresa.reembolsos.dominio.*;
import com.empresa.reembolsos.infraestructura.RepositorioSolicitudesJpa;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReembolsosIntegrationTest {
    @Autowired SolicitudReembolsoService servicio;
    @Autowired RepositorioSolicitudesJpa repositorio;

    @Test void validaCalculaYPersisteTransiciones() {
        var viaje = servicio.crear(nueva(CategoriaGasto.VIAJE, true));
        var representacion = servicio.crear(nueva(CategoriaGasto.REPRESENTACION, true));
        var capacitacion = servicio.crear(nueva(CategoriaGasto.CAPACITACION, true));
        assertEquals(new BigDecimal("100000.00"), viaje.getMontoAprobado());
        assertEquals(new BigDecimal("100000.00"), representacion.getMontoAprobado());
        assertEquals(new BigDecimal("80000.00"), capacitacion.getMontoAprobado());
        assertThrows(IllegalArgumentException.class, () -> servicio.crear(nueva(CategoriaGasto.VIAJE, true)));
        assertThrows(IllegalArgumentException.class, () -> servicio.crear(nueva(CategoriaGasto.CAPACITACION, false)));
        servicio.cambiarEstado(viaje.getId(), EstadoSolicitud.REQUIERE_CORRECCION);
        servicio.cambiarEstado(representacion.getId(), EstadoSolicitud.RECHAZADA);
        servicio.cambiarEstado(capacitacion.getId(), EstadoSolicitud.APROBADA);
        assertEquals(EstadoSolicitud.REQUIERE_CORRECCION, repositorio.findById(viaje.getId()).orElseThrow().getEstado());
        assertEquals(EstadoSolicitud.RECHAZADA, repositorio.findById(representacion.getId()).orElseThrow().getEstado());
        assertEquals(EstadoSolicitud.APROBADA, repositorio.findById(capacitacion.getId()).orElseThrow().getEstado());
    }

    private SolicitudReembolso nueva(CategoriaGasto categoria, boolean tieneSoportes) {
        return new SolicitudReembolso("EMP-1", categoria, new BigDecimal("100000"),
                LocalDate.of(2026, 11, 20), 2, tieneSoportes);
    }
}
