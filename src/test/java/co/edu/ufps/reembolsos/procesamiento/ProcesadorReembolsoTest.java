package co.edu.ufps.reembolsos.procesamiento;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import co.edu.ufps.reembolsos.domain.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
class ProcesadorReembolsoTest {
    @Mock SolicitudRepositoryPort repository; @Mock CadenaValidacion validacion; @Mock SelectorCalculadora selector;
    @Mock ObservadorReembolso empleado; @Mock ObservadorReembolso contabilidad; @Mock ObservadorReembolso auditoria;
    ProcesadorReembolso procesador;
    @BeforeEach void setUp(){procesador=new ProcesadorReembolso(repository,validacion,selector,List.of(empleado,contabilidad,auditoria));}
    @Test void radicarGuardaPendienteCalculadoYNotifica(){
        SolicitudReembolso solicitud=solicitud();when(validacion.validar(solicitud)).thenReturn(Optional.empty());
        when(selector.calcular(CategoriaGasto.TRANSPORTE,new BigDecimal("80000"))).thenReturn(new BigDecimal("80000.00"));
        when(repository.guardar(solicitud)).thenAnswer(call->{SolicitudReembolso s=call.getArgument(0);s.asignarId(7L);return s;});
        SolicitudReembolso guardada=procesador.radicar(solicitud);
        assertEquals(EstadoSolicitud.PENDIENTE,guardada.getEstado());assertEquals(new BigDecimal("80000.00"),guardada.getMontoCalculado());
        verify(empleado).alCambiarEstado(any());verify(contabilidad).alCambiarEstado(any());verify(auditoria).alCambiarEstado(any());
    }
    @Test void rechazoNoCalculaMontoPeroSeNotifica(){
        SolicitudReembolso solicitud=solicitud();when(validacion.validar(solicitud)).thenReturn(Optional.of("Faltan soportes"));
        when(repository.guardar(solicitud)).thenAnswer(call->{SolicitudReembolso s=call.getArgument(0);s.asignarId(8L);return s;});
        SolicitudReembolso guardada=procesador.radicar(solicitud);
        assertEquals(EstadoSolicitud.RECHAZADA,guardada.getEstado());assertNull(guardada.getMontoCalculado());
        verifyNoInteractions(selector);verify(auditoria).alCambiarEstado(any());
    }
    @Test void aprobacionPublicaTransicionAnteriorYNueva(){
        SolicitudReembolso solicitud=solicitud();solicitud.asignarId(9L);solicitud.calcular(new BigDecimal("80000.00"));
        when(repository.buscarPorId(9L)).thenReturn(Optional.of(solicitud));when(repository.guardar(solicitud)).thenReturn(solicitud);
        assertEquals(EstadoSolicitud.APROBADA,procesador.aprobar(9L).getEstado());
        ArgumentCaptor<CambioSolicitud> evento=ArgumentCaptor.forClass(CambioSolicitud.class);
        verify(contabilidad).alCambiarEstado(evento.capture());
        assertEquals(EstadoSolicitud.PENDIENTE,evento.getValue().estadoAnterior());assertEquals(EstadoSolicitud.APROBADA,evento.getValue().estadoNuevo());
    }
    private SolicitudReembolso solicitud(){return new SolicitudReembolso("Ana",CategoriaGasto.TRANSPORTE,new BigDecimal("80000"),true,"factura-1");}
}
