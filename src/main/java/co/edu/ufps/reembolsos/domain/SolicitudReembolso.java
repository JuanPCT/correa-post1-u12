package co.edu.ufps.reembolsos.domain;
import java.math.BigDecimal;
public class SolicitudReembolso {
    private Long id;
    private final String empleado;
    private final CategoriaGasto categoria;
    private final BigDecimal montoBase;
    private final boolean documentosCompletos;
    private final String claveDuplicado;
    private BigDecimal montoCalculado;
    private EstadoSolicitud estado=EstadoSolicitud.PENDIENTE;
    private String motivoRechazo;
    public SolicitudReembolso(String empleado,CategoriaGasto categoria,BigDecimal montoBase,boolean documentosCompletos,String claveDuplicado){
        this.empleado=empleado;this.categoria=categoria;this.montoBase=montoBase;this.documentosCompletos=documentosCompletos;this.claveDuplicado=claveDuplicado;
    }
    public void asignarId(Long id){this.id=id;}
    public void calcular(BigDecimal monto){this.montoCalculado=monto;}
    public void aprobar(){exigirPendiente();estado=EstadoSolicitud.APROBADA;}
    public void rechazar(String motivo){exigirPendiente();estado=EstadoSolicitud.RECHAZADA;motivoRechazo=motivo;}
    private void exigirPendiente(){if(estado!=EstadoSolicitud.PENDIENTE)throw new IllegalStateException("La solicitud ya fue procesada");}
    public Long getId(){return id;} public String getEmpleado(){return empleado;} public CategoriaGasto getCategoria(){return categoria;}
    public BigDecimal getMontoBase(){return montoBase;} public boolean tieneDocumentosCompletos(){return documentosCompletos;}
    public String getClaveDuplicado(){return claveDuplicado;} public BigDecimal getMontoCalculado(){return montoCalculado;}
    public EstadoSolicitud getEstado(){return estado;} public String getMotivoRechazo(){return motivoRechazo;}
}
