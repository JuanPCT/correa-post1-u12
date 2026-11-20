package co.edu.ufps.reembolsos.procesamiento;
import co.edu.ufps.reembolsos.domain.CategoriaGasto;
import java.math.BigDecimal;
public interface CalculadoraReembolso { CategoriaGasto categoria(); BigDecimal calcular(BigDecimal montoBase); }
