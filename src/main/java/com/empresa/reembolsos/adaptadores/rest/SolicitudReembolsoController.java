package com.empresa.reembolsos.adaptadores.rest;

import com.empresa.reembolsos.aplicacion.SolicitudReembolsoService;
import com.empresa.reembolsos.dominio.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudReembolsoController {
    private final SolicitudReembolsoService servicio;

    public SolicitudReembolsoController(SolicitudReembolsoService servicio) { this.servicio = servicio; }

    @PostMapping
    public SolicitudReembolso crear(@RequestBody NuevaSolicitud solicitud) {
        return servicio.crear(new SolicitudReembolso(solicitud.empleadoId(), solicitud.categoria(),
                solicitud.montoSolicitado(), solicitud.fechaGasto(), solicitud.diasViaje(), solicitud.documentosSoporte()));
    }

    @PutMapping("/{id}/estado")
    public SolicitudReembolso cambiarEstado(@PathVariable Long id, @RequestBody EstadoSolicitud estado) {
        return servicio.cambiarEstado(id, estado);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> errorDeValidacion(IllegalArgumentException error) {
        return ResponseEntity.badRequest().body(Map.of("error", error.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String,String>> noEncontrada(NoSuchElementException error) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", error.getMessage()));
    }

    public record NuevaSolicitud(String empleadoId, CategoriaGasto categoria, BigDecimal montoSolicitado,
                                 LocalDate fechaGasto, int diasViaje, boolean documentosSoporte) { }
}
