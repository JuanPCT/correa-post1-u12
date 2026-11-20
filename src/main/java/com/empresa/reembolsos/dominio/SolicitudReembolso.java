package com.empresa.reembolsos.dominio;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class SolicitudReembolso {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String empleadoId;
    @Enumerated(EnumType.STRING)
    private CategoriaGasto categoria;
    private BigDecimal montoSolicitado;
    private BigDecimal montoAprobado;
    private LocalDate fechaGasto;
    private int diasViaje;
    private boolean documentosSoporte;
    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

    protected SolicitudReembolso() { }

    public SolicitudReembolso(String empleadoId, CategoriaGasto categoria, BigDecimal montoSolicitado,
                              LocalDate fechaGasto, int diasViaje, boolean documentosSoporte) {
        this.empleadoId = empleadoId;
        this.categoria = categoria;
        this.montoSolicitado = montoSolicitado;
        this.fechaGasto = fechaGasto;
        this.diasViaje = diasViaje;
        this.documentosSoporte = documentosSoporte;
    }

    public Long getId() { return id; }
    public String getEmpleadoId() { return empleadoId; }
    public CategoriaGasto getCategoria() { return categoria; }
    public BigDecimal getMontoSolicitado() { return montoSolicitado; }
    public BigDecimal getMontoAprobado() { return montoAprobado; }
    public LocalDate getFechaGasto() { return fechaGasto; }
    public int getDiasViaje() { return diasViaje; }
    public boolean isDocumentosSoporte() { return documentosSoporte; }
    public EstadoSolicitud getEstado() { return estado; }
    public void setMontoAprobado(BigDecimal monto) { montoAprobado = monto; }
    public void setEstado(EstadoSolicitud nuevo) { estado = nuevo; }
}
