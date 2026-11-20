package co.edu.ufps.reembolsos.adaptadores.persistencia.jpa;
import co.edu.ufps.reembolsos.domain.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="solicitud_reembolso")
public class SolicitudJpaEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    private String empleado;
    @Enumerated(EnumType.STRING) private CategoriaGasto categoria;
    @Column(precision=14,scale=2) private BigDecimal montoBase;
    private boolean documentosCompletos;
    @Column(unique=true) private String claveDuplicado;
    @Column(precision=14,scale=2) private BigDecimal montoCalculado;
    @Enumerated(EnumType.STRING) private EstadoSolicitud estado;
    private String motivoRechazo;
    protected SolicitudJpaEntity() { }
    static SolicitudJpaEntity desde(SolicitudReembolso s){
        SolicitudJpaEntity e=new SolicitudJpaEntity();e.id=s.getId();e.empleado=s.getEmpleado();e.categoria=s.getCategoria();
        e.montoBase=s.getMontoBase();e.documentosCompletos=s.tieneDocumentosCompletos();e.claveDuplicado=s.getClaveDuplicado();
        e.montoCalculado=s.getMontoCalculado();e.estado=s.getEstado();e.motivoRechazo=s.getMotivoRechazo();return e;
    }
    SolicitudReembolso aDominio(){
        SolicitudReembolso s=new SolicitudReembolso(empleado,categoria,montoBase,documentosCompletos,claveDuplicado);s.asignarId(id);
        if(montoCalculado!=null)s.calcular(montoCalculado);
        if(estado==EstadoSolicitud.APROBADA)s.aprobar();
        if(estado==EstadoSolicitud.RECHAZADA)s.rechazar(motivoRechazo);
        return s;
    }
    public Long getId(){return id;} public String getClaveDuplicado(){return claveDuplicado;}
}
