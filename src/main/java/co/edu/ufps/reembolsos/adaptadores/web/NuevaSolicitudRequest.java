package co.edu.ufps.reembolsos.adaptadores.web;
import co.edu.ufps.reembolsos.domain.CategoriaGasto;
import java.math.BigDecimal;
public record NuevaSolicitudRequest(String empleado,CategoriaGasto categoria,BigDecimal montoBase,
                                   boolean documentosCompletos,String claveDuplicado) { }
