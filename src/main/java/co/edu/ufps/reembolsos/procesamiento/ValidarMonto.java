package co.edu.ufps.reembolsos.procesamiento;
import co.edu.ufps.reembolsos.domain.SolicitudReembolso;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Component @Order(2)
public class ValidarMonto implements ValidacionSolicitud {
    private static final BigDecimal MAXIMO=new BigDecimal("1000000");
    public Optional<String> validar(SolicitudReembolso solicitud){
        BigDecimal monto=solicitud.getMontoBase();
        if(monto==null||monto.signum()<=0||monto.compareTo(MAXIMO)>0)return Optional.of("Monto debe ser mayor que cero y no superar 1000000");
        return Optional.empty();
    }
}
