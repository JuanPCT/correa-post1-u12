package co.edu.ufps.reembolsos.procesamiento;
import co.edu.ufps.reembolsos.domain.CategoriaGasto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;
@Component
public class CalculadoraAlimentacion implements CalculadoraReembolso {
    public CategoriaGasto categoria(){return CategoriaGasto.ALIMENTACION;}
    public BigDecimal calcular(BigDecimal base){return base.multiply(new BigDecimal("0.80")).min(new BigDecimal("150000")).setScale(2,RoundingMode.HALF_UP);}
}
