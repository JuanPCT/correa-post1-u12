package co.edu.ufps.reembolsos.procesamiento;
import co.edu.ufps.reembolsos.domain.CategoriaGasto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;
@Component
public class CalculadoraTransporte implements CalculadoraReembolso {
    public CategoriaGasto categoria(){return CategoriaGasto.TRANSPORTE;}
    public BigDecimal calcular(BigDecimal base){return base.min(new BigDecimal("500000")).setScale(2,RoundingMode.HALF_UP);}
}
