package com.empresa.reembolsos.procesamiento;

import com.empresa.reembolsos.dominio.CategoriaGasto;
import com.empresa.reembolsos.procesamiento.calculo.CalculadoraReembolso;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class SelectorCalculadora {
    private final Map<CategoriaGasto, CalculadoraReembolso> estrategias;

    public SelectorCalculadora(List<CalculadoraReembolso> calculadoras) {
        estrategias = calculadoras.stream().collect(
                Collectors.toUnmodifiableMap(CalculadoraReembolso::categoria, Function.identity()));
    }

    public CalculadoraReembolso obtener(CategoriaGasto categoria) {
        return Optional.ofNullable(estrategias.get(categoria))
                .orElseThrow(() -> new IllegalArgumentException("Categoría no soportada: " + categoria));
    }
}
