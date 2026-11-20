package co.edu.ufps.reembolsos.adaptadores.web;
import co.edu.ufps.reembolsos.domain.SolicitudReembolso;
import co.edu.ufps.reembolsos.procesamiento.ProcesadorReembolso;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/reembolsos")
public class ReembolsoController {
    private final ProcesadorReembolso procesador;
    public ReembolsoController(ProcesadorReembolso procesador){this.procesador=procesador;}
    @PostMapping public ResponseEntity<SolicitudResponse> radicar(@RequestBody NuevaSolicitudRequest r){
        SolicitudReembolso s=new SolicitudReembolso(r.empleado(),r.categoria(),r.montoBase(),r.documentosCompletos(),r.claveDuplicado());
        return ResponseEntity.ok(respuesta(procesador.radicar(s)));
    }
    @PatchMapping("/{id}/aprobar") public ResponseEntity<SolicitudResponse> aprobar(@PathVariable Long id){return ResponseEntity.ok(respuesta(procesador.aprobar(id)));}
    private SolicitudResponse respuesta(SolicitudReembolso s){return new SolicitudResponse(s.getId(),s.getEstado(),s.getMontoCalculado(),s.getMotivoRechazo());}
}
