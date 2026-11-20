# Unidad 12 — Reembolsos integrados

Aplicación Spring Boot 3 / Java 17 que integra Chain of Responsibility para validaciones, Strategy para fórmulas por categoría y Observer para cambios de estado. La propuesta arquitectónica se fechó antes del código en [`docs/adr/ADR-001.md`](docs/adr/ADR-001.md).

## Ejecutar

```bash
mvn clean verify
mvn spring-boot:run
```

La práctica utiliza H2 en memoria. Los observadores registran mensajes en log; no envían correos ni llaman a un sistema contable externo.

## API

- `POST /api/reembolsos`: radica solicitud, ejecuta validación y calcula el valor según categoría.
- `PATCH /api/reembolsos/{id}/aprobar`: aprueba una solicitud pendiente.

La respuesta de radicación incluye estado, monto calculado o motivo de rechazo. `TRANSPORTE` reconoce hasta $500.000; `ALIMENTACION` devuelve 80 % con tope de $150.000; `ALOJAMIENTO` devuelve 90 % con tope de $400.000. Son reglas didácticas visibles en cada Strategy.

## Diseño y calidad

Documentos y monto se verifican antes de consultar duplicidad. Las tres Strategies y los observers son beans descubiertos por listas Spring. El puerto del repositorio mantiene JPA en el adaptador y simplifica pruebas. ArchUnit protege la dirección de dependencias, los contratos Strategy/Observer y el límite web. La primera regla detectó que el adaptador web dependía directamente del procesador y de una excepción de su paquete; se añadió una fachada de aplicación y la excepción pasó al dominio. Los cuatro checks quedan verdes. El workflow Maven ejecuta pruebas unitarias, integración H2 y arquitectura.

Las reglas de reintento y envío externo se excluyeron: necesitarían una política de idempotencia y outbox que no exige el alcance de esta práctica.
