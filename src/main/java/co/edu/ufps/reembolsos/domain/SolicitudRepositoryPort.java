package co.edu.ufps.reembolsos.domain;
import java.util.Optional;
public interface SolicitudRepositoryPort {
    SolicitudReembolso guardar(SolicitudReembolso solicitud);
    Optional<SolicitudReembolso> buscarPorId(Long id);
    boolean existeClaveDuplicada(String clave);
}
