package com.empresa.reembolsos.aplicacion;

import com.empresa.reembolsos.dominio.*;
import com.empresa.reembolsos.infraestructura.RepositorioSolicitudesJpa;
import com.empresa.reembolsos.procesamiento.SelectorCalculadora;
import com.empresa.reembolsos.procesamiento.validacion.CadenaValidadores;
import com.empresa.reembolsos.procesamiento.notificacion.ObservadorCambioEstado;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class SolicitudReembolsoService {
    private final RepositorioSolicitudesJpa repositorio;
    private final CadenaValidadores cadena;
    private final SelectorCalculadora calculadoras;
    private final List<ObservadorCambioEstado> observadores;

    public SolicitudReembolsoService(RepositorioSolicitudesJpa repositorio, CadenaValidadores cadena,
            SelectorCalculadora calculadoras, List<ObservadorCambioEstado> observadores) {
        this.repositorio = repositorio;
        this.cadena = cadena;
        this.calculadoras = calculadoras;
        this.observadores = List.copyOf(observadores);
    }

    @Transactional
    public SolicitudReembolso crear(SolicitudReembolso solicitud) {
        if (solicitud.getEmpleadoId() == null || solicitud.getEmpleadoId().isBlank()
                || solicitud.getCategoria() == null || solicitud.getFechaGasto() == null) {
            throw new IllegalArgumentException("Empleado, categoría y fecha son obligatorios");
        }
        if (solicitud.getCategoria() == CategoriaGasto.VIAJE && solicitud.getDiasViaje() <= 0) {
            throw new IllegalArgumentException("Los viáticos requieren al menos un día de viaje");
        }
        var pendientes = repositorio.findByEmpleadoIdAndEstado(solicitud.getEmpleadoId(), EstadoSolicitud.PENDIENTE);
        cadena.validar(solicitud, pendientes).ifPresent(error -> { throw new IllegalArgumentException(error); });
        solicitud.setMontoAprobado(calculadoras.obtener(solicitud.getCategoria()).calcular(solicitud));
        return repositorio.save(solicitud);
    }

    @Transactional
    public SolicitudReembolso cambiarEstado(Long id, EstadoSolicitud siguiente) {
        var solicitud = repositorio.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Solicitud no encontrada: " + id));
        var anterior = solicitud.getEstado();
        if (siguiente == null) throw new IllegalArgumentException("El nuevo estado es obligatorio");
        if (anterior == siguiente) return solicitud;
        solicitud.setEstado(siguiente);
        var guardada = repositorio.save(solicitud);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override public void afterCommit() {
                observadores.forEach(o -> o.onCambioEstado(guardada, anterior));
            }
        });
        return guardada;
    }
}
