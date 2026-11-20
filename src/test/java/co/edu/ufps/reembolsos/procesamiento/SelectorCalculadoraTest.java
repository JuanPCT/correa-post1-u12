package co.edu.ufps.reembolsos.procesamiento;
import static org.junit.jupiter.api.Assertions.assertEquals;
import co.edu.ufps.reembolsos.domain.CategoriaGasto;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
class SelectorCalculadoraTest {
    private final SelectorCalculadora selector=new SelectorCalculadora(List.of(new CalculadoraTransporte(),new CalculadoraAlimentacion(),new CalculadoraAlojamiento()));
    @Test void transporteAplicaTope(){assertEquals(new BigDecimal("500000.00"),selector.calcular(CategoriaGasto.TRANSPORTE,new BigDecimal("800000")));}
    @Test void alimentacionReembolsaOchentaPorCiento(){assertEquals(new BigDecimal("80000.00"),selector.calcular(CategoriaGasto.ALIMENTACION,new BigDecimal("100000")));}
    @Test void alimentacionRespetaTope(){assertEquals(new BigDecimal("150000.00"),selector.calcular(CategoriaGasto.ALIMENTACION,new BigDecimal("250000")));}
    @Test void alojamientoReembolsaNoventaPorCientoConTope(){assertEquals(new BigDecimal("400000.00"),selector.calcular(CategoriaGasto.ALOJAMIENTO,new BigDecimal("600000")));}
}
