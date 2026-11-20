package co.edu.ufps.reembolsos.adaptadores.web;
import co.edu.ufps.reembolsos.domain.EstadoSolicitud;
import java.math.BigDecimal;
public record SolicitudResponse(Long id,EstadoSolicitud estado,BigDecimal montoCalculado,String motivoRechazo) { }
