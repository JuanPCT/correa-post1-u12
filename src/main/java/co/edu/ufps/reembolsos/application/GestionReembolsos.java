package co.edu.ufps.reembolsos.application;
import co.edu.ufps.reembolsos.domain.SolicitudReembolso;
import co.edu.ufps.reembolsos.procesamiento.ProcesadorReembolso;
import org.springframework.stereotype.Service;
@Service
public class GestionReembolsos {
    private final ProcesadorReembolso procesador;
    public GestionReembolsos(ProcesadorReembolso procesador){this.procesador=procesador;}
    public SolicitudReembolso radicar(SolicitudReembolso solicitud){return procesador.radicar(solicitud);}
    public SolicitudReembolso aprobar(Long id){return procesador.aprobar(id);}
}
