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

Rutas del servicio (base http://localhost:8080/dsaApp/flota):
- POST /planes: crear plan de vuelo. Devuelve 201, 400 si faltan datos y 409 si el dron
  está en el almacén o el plan se solapa.
- GET /drones/horas: lista de drones por horas de vuelo. Devuelve 200.
- PUT /almacen/{idDron}: guarda un dron en el almacén. Devuelve 204.
- GET /pilotos/{idPiloto}/planes: planes de un piloto. Devuelve 200.

Errores y problemas encontrados

- Si el id de un dron o piloto no existe, falla con NullPointerException y el servicio
  devuelve 500.
- Si el almacén está vacío, repararDron escribe un ERROR y devuelve null.

Pendiente

- Probar el POST, el PUT y el GET de planes de un piloto.
- Documentar la API con Swagger.
- Devolver 404 cuando el id no exista.
