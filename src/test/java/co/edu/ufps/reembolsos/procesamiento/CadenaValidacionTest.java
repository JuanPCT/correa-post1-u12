package co.edu.ufps.reembolsos.procesamiento;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import co.edu.ufps.reembolsos.domain.*;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
class CadenaValidacionTest {
    @Mock SolicitudRepositoryPort repository;
    private SolicitudReembolso solicitud(boolean docs,String monto,String clave){return new SolicitudReembolso("Ana",CategoriaGasto.TRANSPORTE,new BigDecimal(monto),docs,clave);}
    private CadenaValidacion cadena(){return new CadenaValidacion(List.of(new ValidarDocumentos(),new ValidarMonto(),new ValidarDuplicado(repository)));}
    @Test void documentosDetienenCadenaAntesDeConsultar(){
        assertEquals("Faltan soportes obligatorios",cadena().validar(solicitud(false,"50000","doc-1")).orElseThrow());verifyNoInteractions(repository);
    }
    @Test void montoInvalidoEvitaConsultaCostosa(){
        assertTrue(cadena().validar(solicitud(true,"0","doc-1")).orElseThrow().contains("Monto"));verifyNoInteractions(repository);
    }
    @Test void documentoDuplicadoSeRechazaTrasReglasBaratas(){
        when(repository.existeClaveDuplicada("doc-1")).thenReturn(true);
        assertEquals("El soporte ya fue radicado",cadena().validar(solicitud(true,"50000","doc-1")).orElseThrow());verify(repository).existeClaveDuplicada("doc-1");
    }
    @Test void solicitudUnicaPasaTodasLasEtapas(){
        when(repository.existeClaveDuplicada("doc-2")).thenReturn(false);assertTrue(cadena().validar(solicitud(true,"50000","doc-2")).isEmpty());
    }
}
