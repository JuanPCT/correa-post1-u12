package co.edu.ufps.reembolsos.domain;
import java.math.BigDecimal;
public record CambioSolicitud(Long solicitudId,String empleado,EstadoSolicitud estadoAnterior,EstadoSolicitud estadoNuevo,BigDecimal monto) { }
