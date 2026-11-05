Minim1 - Flota de drones

Parte 1:
- Interfaz GestorDrones y fachada GestorDronesImpl hechas.
- Trazas de Log4J al inicio y al final de cada método, con los parámetros. También hay
  trazas de ERROR cuando una operación falla.
- Test JUnit (GestorDronesTest) con cuatro operaciones: crear plan de vuelo, listar drones
  por horas, guardar/reparar dron y planes de un piloto.

Parte 2:
- Servicio REST hecho en la clase ServicioDrones con GET, POST y PUT, usando la fachada y
  objetos de transferencia (DTO).
- Solo he podido comprobar el GET de drones por horas. El resto está implementado pero no
  lo he probado.
- No he hecho la documentación de la API con Swagger.

Definición del servicio REST

Servicio: ServicioDrones. Ruta base: http://localhost:8080/dsaApp/flota. Formato: JSON.

| Operación | Método y ruta | Petición | Respuesta | Códigos |
|---|---|---|---|---|
| Crear plan de vuelo | POST /planes | JSON con idDron, idPiloto, fecha, duracionHoras y posiciones | Plan creado | 201; 400 faltan datos; 409 dron en almacén o plan solapado |
| Drones por horas de vuelo | GET /drones/horas | Sin cuerpo | Lista de drones | 200 |
| Guardar dron en el almacén | PUT /almacen/{idDron} | Sin cuerpo | Sin cuerpo | 204 |
| Planes de un piloto | GET /pilotos/{idPiloto}/planes | Sin cuerpo | Lista de planes | 200 |

Ejemplo de petición para crear un plan:

```json
{ "idDron": "D1", "idPiloto": "P1", "fecha": "2026-11-10T10:00",
  "duracionHoras": 2, "posiciones": [ { "latitud": 41.27, "longitud": 1.98 } ] }
```
Se devuelven objetos de transferencia (DronDTO, PlanDeVueloDTO, PosicionDTO) para evitar ciclos.

Errores y problemas encontrados

- Si el id de un dron o piloto no existe, falla con NullPointerException y el servicio
  devuelve 500.
- Si el almacén está vacío, repararDron escribe un ERROR y devuelve null.

Pendiente

- Probar el POST, el PUT y el GET de planes de un piloto.
- Documentar la API con Swagger.
- Devolver 404 cuando el id no exista.
