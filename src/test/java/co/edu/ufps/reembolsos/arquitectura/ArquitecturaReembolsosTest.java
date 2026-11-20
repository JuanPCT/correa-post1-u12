package co.edu.ufps.reembolsos.arquitectura;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import co.edu.ufps.reembolsos.domain.ObservadorReembolso;
import co.edu.ufps.reembolsos.procesamiento.CalculadoraReembolso;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;
class ArquitecturaReembolsosTest {
    private final JavaClasses proyecto=new ClassFileImporter().importPackages("co.edu.ufps.reembolsos");
    @Test void procesamientoNoDependeDeAdaptadoresNiJPA(){
        noClasses().that().resideInAPackage("..procesamiento..").should().dependOnClassesThat()
                .resideInAnyPackage("..adaptadores..","jakarta.persistence..","org.springframework.data..").check(proyecto);
    }
    @Test void adaptadorWebNoConoceProcesamientoNiPersistencia(){
        noClasses().that().resideInAPackage("..adaptadores.web..").should().dependOnClassesThat()
                .resideInAnyPackage("..procesamiento..","..adaptadores.persistencia..").check(proyecto);
    }
    @Test void calculadorasConcretasImplementanStrategy(){
        classes().that().haveSimpleNameStartingWith("Calculadora").and().areNotInterfaces()
                .should().implement(CalculadoraReembolso.class).check(proyecto);
    }
    @Test void listenersImplementanObserver(){
        classes().that().resideInAPackage("..procesamiento.notificaciones..")
                .should().implement(ObservadorReembolso.class).check(proyecto);
    }
}
