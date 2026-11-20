package co.edu.ufps.reembolsos.adaptadores.persistencia.jpa;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SolicitudSpringDataRepository extends JpaRepository<SolicitudJpaEntity,Long> {
    boolean existsByClaveDuplicado(String claveDuplicado);
}
