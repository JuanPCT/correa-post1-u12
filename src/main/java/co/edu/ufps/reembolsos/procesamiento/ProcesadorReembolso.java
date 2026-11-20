package co.edu.ufps.reembolsos.procesamiento;
import co.edu.ufps.reembolsos.domain.*;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class ProcesadorReembolso {
    private final SolicitudRepositoryPort repository;
    private final CadenaValidacion validacion;
    private final SelectorCalculadora selector;
    private final List<ObservadorReembolso> observadores;
    public ProcesadorReembolso(SolicitudRepositoryPort repository,CadenaValidacion validacion,
            SelectorCalculadora selector,List<ObservadorReembolso> observadores){
        this.repository=repository;this.validacion=validacion;this.selector=selector;this.observadores=List.copyOf(observadores);
    }
    @Transactional public SolicitudReembolso radicar(SolicitudReembolso solicitud){
        var error=validacion.validar(solicitud);
        if(error.isPresent())solicitud.rechazar(error.get());
        else solicitud.calcular(selector.calcular(solicitud.getCategoria(),solicitud.getMontoBase()));
        SolicitudReembolso guardada=repository.guardar(solicitud);
        notificar(new CambioSolicitud(guardada.getId(),guardada.getEmpleado(),null,guardada.getEstado(),guardada.getMontoCalculado()));
        return guardada;
    }
    @Transactional public SolicitudReembolso aprobar(Long id){
        SolicitudReembolso solicitud=repository.buscarPorId(id).orElseThrow(()->new SolicitudNoEncontrada(id));
        EstadoSolicitud anterior=solicitud.getEstado();
        solicitud.aprobar();
        SolicitudReembolso guardada=repository.guardar(solicitud);
        notificar(new CambioSolicitud(id,guardada.getEmpleado(),anterior,guardada.getEstado(),guardada.getMontoCalculado()));
        return guardada;
    }
    private void notificar(CambioSolicitud cambio){observadores.forEach(o->o.alCambiarEstado(cambio));}
}
