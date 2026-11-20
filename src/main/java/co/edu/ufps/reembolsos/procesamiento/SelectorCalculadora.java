package co.edu.ufps.reembolsos.procesamiento;
import co.edu.ufps.reembolsos.domain.CategoriaGasto;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
@Component
public class SelectorCalculadora {
    private final Map<CategoriaGasto,CalculadoraReembolso> calculadoras=new EnumMap<>(CategoriaGasto.class);
    public SelectorCalculadora(List<CalculadoraReembolso> items){items.forEach(c->calculadoras.put(c.categoria(),c));}
    public BigDecimal calcular(CategoriaGasto categoria,BigDecimal monto){
        CalculadoraReembolso calculadora=calculadoras.get(categoria);
        if(calculadora==null)throw new IllegalArgumentException("Sin fórmula para "+categoria);
        return calculadora.calcular(monto);
    }
}
