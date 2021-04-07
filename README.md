# Proyecto Final del Curso RESTful web service con spring-boot
## Elaborado por **Daniel Bojorge**

### Usuarios Guardados

El proyecto va con dos usuarios registrados, uno con rol **ADMIN** y otro **USER**

| Usario   | Password                 | Rol     |
| -------- | ------------------------ | --------|
|  ucem    | daniel                   | ADMIN   |
|  jperez  | 1234                     | USER    |

### Test o Run
Puerto: **8181**
Fechas:  **Formato Aceptado** _yyyy-MM-dd_  Ejemplo: *2017-02-01*
#### Categorías:
**Listar Todas**
```
http://localhost:8181/v1/inventario/categorias
```
**Aplicar Filtros:**
```
     localhost:8181/v1/inventario/categorias?search=indivi
     localhost:8181/v1/inventario/categorias?descripcion=indivi
     localhost:8181/v1/inventario/categorias?nombre=senci
     localhost:8181/v1/inventario/categorias?fields=nombre,descripcion
```
**Aplicar Offset y limit**
```
    localhost:8181/v1/inventario/categorias?nombre=doble&offset=0&limit=1
```
**Request Un Elemento:**
```
    localhost:8181/v1/inventario/categorias/1
```
**Request 1 con Filtros:**
```
    localhost:8181/v1/inventario/categorias/2?fields=nombre
```
#### Cupo:
  **Consulta:**
```
    localhost:8181/v1/disponibilidad/cupos?desde=2017-01-01&hasta=2017-01-02
```
  **Aplicando filtros:**
```
    http://localhost:8181/v1/disponibilidad/cupos?desde=2017-01-01&hasta=2017-01-02&offset=0&limit=1
```
#### Reservacion:
  **Consulta:**
  ```
    http://localhost:8181/v1/reservaciones/2
```
  **Haciendo Reservación:**
  <br>
  _Cuerpo:_
  ```json
    {"desde":"2017-01-17","hasta":"2017-03-18","cuarto":"2","huesped":"2"}
```
```
    http://localhost:8181/v1/reservaciones
```

##### Servicio para la gestión de las operaciones de un hotel, el cual consta de los siguientes componentes:

>- Servicio de inventario.
>- Servicio de consulta de disponibilidad de cupo.
>- Servicio de reservación.

### Software requerido
>- JDK 1.8+.
>- Maven 3.0+.
>- Firefox, Chrome o Postman.

### 	Construir un JAR ejecutable

Para construir un JAR ejecutable, desde la línea de comando ejecutamos el comando:

```
 mvn compile package
```
### Ejecutar JAR

Una vez generado el JAR, podemos correr la aplicación con:

```
java -jar target/hotel-web-api-1.0-SNAPSHOT.jar
```
### Correr la aplicación con Maven

Ejecutando el siguiente comando podemos correr la aplicación sin generar el JAR:

```
mvn spring-boot:run
```