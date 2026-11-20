package com.empresa.reembolsos;

import com.empresa.reembolsos.procesamiento.calculo.CalculadoraReembolso;
import com.empresa.reembolsos.procesamiento.notificacion.ObservadorCambioEstado;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

class ReglasArquitectura {
    private final com.tngtech.archunit.core.domain.JavaClasses codigo =
            new ClassFileImporter().importPackages("com.empresa.reembolsos");

    @Test void procesamientoSinPersistenciaNiWeb() {
        noClasses().that().resideInAPackage("..procesamiento..").should().dependOnClassesThat()
                .resideInAnyPackage("..infraestructura..", "..adaptadores..", "org.springframework.data..",
                        "jakarta.persistence..", "org.springframework.web..").check(codigo);
    }

    @Test void controladorSoloAccedeAlaAplicacionYDominio() {
        classes().that().haveSimpleName("SolicitudReembolsoController").should().onlyAccessClassesThat()
                .resideInAnyPackage("..aplicacion..", "..dominio..", "..adaptadores.rest..", "org.springframework.web..",
                        "org.springframework.http..", "java..").check(codigo);
    }

    @Test void calculadorasImplementanStrategy() {
        classes().that().resideInAPackage("..procesamiento.calculo..").and().areNotInterfaces()
                .should().implement(CalculadoraReembolso.class).check(codigo);
    }

    @Test void notificadoresImplementanObserver() {
        classes().that().resideInAPackage("..procesamiento.notificacion..").and().areNotInterfaces()
                .should().implement(ObservadorCambioEstado.class).check(codigo);
    }
}
