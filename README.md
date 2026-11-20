# Unidad 12 · Solicitudes de reembolso

## Diseño Propuesto

| Requisito | Variabilidad | Patrón y justificación |
|---|---|---|
| RF1 | Orden y cantidad de verificaciones | Chain of Responsibility permite insertar una regla y detener el flujo en el primer error. |
| RF2 | Fórmula de cada categoría | Strategy encapsula la fórmula y permite agregar implementaciones sin editar el selector. |
| RF3 | Reacciones al cambio de estado | Observer permite que cada interesado defina su reacción al mismo evento. |

El commit `d756ab1` conserva la propuesta original anterior al código. Se ajustaron las categorías y la persistencia al revisar la especificación.

## Decisiones de diseño

1. **Orden.** Documentos y monto son comprobaciones inmediatas. Comparar los pendientes al final evita recorrerlos cuando ya hay un error. Ubicar duplicados primero mostraría ese motivo antes, a costa de revisar la lista en solicitudes inválidas.
2. **Selección.** Spring inyecta las calculadoras y el mapa detecta registros duplicados. Las categorías pertenecen al enum de dominio.
3. **Persistencia y notificación.** El servicio usa JPA directamente. El contrato Observer admite el cambio previsto en Contabilidad. El [ADR-001](docs/adr/ADR-001.md) documenta los costos.
4. **Límites.** ArchUnit permite estereotipos de Spring para ensamblaje y prohíbe que el procesamiento dependa de HTTP o persistencia.

## Qué se descartó y por qué

- Un bus de eventos genérico añadiría configuración a tres observadores conocidos. Se reconsideraría con consumidores asíncronos y reintentos separados.
- Una fábrica de exportadores PDF y Excel requiere casos reales de uso. Se reconsideraría cuando Contabilidad confirme ambos formatos.
- Una excepción por cada validador repetiría el manejo HTTP 400. Se reconsideraría si un consumidor externo requiere acciones diferentes según la regla.

## Ejecutar

Usa Java 17 y Maven. Ejecuta `mvn clean verify` para las seis pruebas unitarias, una de integración con H2 y cuatro de arquitectura. Inicia el servicio con `mvn spring-boot:run`.

- `POST /api/solicitudes` recibe `{"empleadoId":"EMP-1","categoria":"VIAJE","montoSolicitado":215000,"fechaGasto":"2026-11-20","diasViaje":2,"documentosSoporte":true}`. Las categorías son VIAJE, REPRESENTACION y CAPACITACION.
- `PUT /api/solicitudes/{id}/estado` recibe un estado, por ejemplo `"APROBADA"`. Empleado y Auditoría reciben cada transición y Contabilidad actúa en aprobaciones.

Cada solicitud aplica el tope de su categoría. En duplicados, el período es mes y año y se comparan solicitudes pendientes del mismo empleado y categoría. Un control de acumulados requeriría el registro de desembolsos de Finanzas.
