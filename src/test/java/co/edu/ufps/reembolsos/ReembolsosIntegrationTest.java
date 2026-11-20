package co.edu.ufps.reembolsos;
import static org.junit.jupiter.api.Assertions.*;
import co.edu.ufps.reembolsos.domain.*;
import co.edu.ufps.reembolsos.procesamiento.ProcesadorReembolso;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
class ReembolsosIntegrationTest {
    @Autowired ProcesadorReembolso procesador; @Autowired SolicitudRepositoryPort repository;
    @Test @Transactional void radicaCalculaPersisteDetectaDuplicadoYAprobar(){
        var solicitud=new SolicitudReembolso("Juan",CategoriaGasto.ALIMENTACION,new BigDecimal("100000"),true,"factura-int-1");
        var radicada=procesador.radicar(solicitud);
        assertEquals(EstadoSolicitud.PENDIENTE,radicada.getEstado());assertEquals(new BigDecimal("80000.00"),radicada.getMontoCalculado());
        assertTrue(repository.buscarPorId(radicada.getId()).isPresent());
        var duplicada=procesador.radicar(new SolicitudReembolso("Juan",CategoriaGasto.ALIMENTACION,new BigDecimal("100000"),true,"factura-int-1"));
        assertEquals(EstadoSolicitud.RECHAZADA,duplicada.getEstado());
        assertEquals(EstadoSolicitud.APROBADA,procesador.aprobar(radicada.getId()).getEstado());
        assertEquals(EstadoSolicitud.APROBADA,repository.buscarPorId(radicada.getId()).orElseThrow().getEstado());
    }
}
