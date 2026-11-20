package co.edu.ufps.reembolsos.adaptadores.persistencia.jpa;
import co.edu.ufps.reembolsos.domain.SolicitudReembolso;
import co.edu.ufps.reembolsos.domain.SolicitudRepositoryPort;
import java.util.Optional;
import org.springframework.stereotype.Repository;
@Repository
public class SolicitudRepositoryAdapter implements SolicitudRepositoryPort {
    private final SolicitudSpringDataRepository repository;
    public SolicitudRepositoryAdapter(SolicitudSpringDataRepository repository){this.repository=repository;}
    public SolicitudReembolso guardar(SolicitudReembolso s){return repository.save(SolicitudJpaEntity.desde(s)).aDominio();}
    public Optional<SolicitudReembolso> buscarPorId(Long id){return repository.findById(id).map(SolicitudJpaEntity::aDominio);}
    public boolean existeClaveDuplicada(String clave){return repository.existsByClaveDuplicado(clave);}
}
