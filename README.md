# Sistema de Gestión de Gimnasio - Arquitectura de Microservicios

## Descripción del proyecto

Este proyecto corresponde al desarrollo de un sistema de gestión para gimnasio implementado bajo arquitectura de microservicios utilizando **Spring Boot**, **Spring Data JPA**, **MySQL**, **Flyway** y comunicación entre servicios mediante **WebClient**.

El sistema permite administrar clientes, membresías, clases, reservas, ventas, pagos, asistencia, entrenadores y productos, separando cada funcionalidad en microservicios independientes para mejorar escalabilidad, mantenibilidad y modularidad.

---

## Integrantes

- Natanael Valenzuela
- Matías Araya
- Esteban Acuña

---

## Tecnologías utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Web
- Spring WebFlux
- MySQL
- Flyway
- Maven
- Lombok
- IntelliJ IDEA

---

## Arquitectura del sistema

El proyecto está compuesto por los siguientes microservicios:

- cliente-service
- membresia-service
- tipomembresia-service
- clase-service
- reserva-service
- entrenador-service
- producto-service
- venta-service
- pago-service
- asistencia-service

Cada microservicio posee:

- Controller
- Service
- Repository
- DTO
- Mapper
- Exception Handler
- Configuración independiente
- Base de datos y migraciones con Flyway

---

## Funcionalidades implementadas

### Cliente
- Crear cliente
- Listar clientes
- Obtener cliente por ID
- Modificar cliente
- Eliminar cliente

### Membresía
- Crear membresía
- Listar membresías
- Obtener membresía por ID
- Modificar membresía
- Eliminar membresía
- Validar membresía activa

### Tipo Membresía
- CRUD completo de tipos de membresía

### Clase
- Crear clases
- Listar clases
- Obtener clase por ID
- Modificar clase
- Eliminar clase

### Reserva
- Crear reservas
- Validación de membresía activa
- Validación de clase existente
- Listar reservas
- Obtener reserva
- Eliminar reserva

### Asistencia
- Registrar entrada
- Registrar salida
- Listar asistencias
- Obtener asistencia por ID
- Eliminar asistencia

### Entrenador
- CRUD completo de entrenadores

### Producto
- CRUD completo de productos

### Venta
- Registrar venta
- Validar cliente existente
- Validar producto existente
- Validar stock disponible

### Pago
- Registrar pago
- Validar cliente existente
- Validar membresía activa

---

## Comunicación entre microservicios

Se implementó comunicación síncrona mediante **WebClient**.

Relaciones implementadas:

- reserva-service → membresia-service
- reserva-service → clase-service
- pago-service → cliente-service
- pago-service → membresia-service
- venta-service → cliente-service
- venta-service → producto-service
- clase-service → entrenador-service

---

## Manejo de excepciones

Cada microservicio incluye manejo global de excepciones mediante:

- NotFoundException
- BusinessException
- GlobalExceptionHandler
- ConflictException

Respuestas estructuradas con:

- timestamp
- status
- error
- message
- path

---

## Reglas de negocio implementadas

- No se puede registrar reserva sin membresía activa
- No se puede registrar reserva si clase no existe
- No se puede registrar pago sin cliente existente
- No se puede registrar pago con membresía inactiva
- No se puede registrar venta si producto no existe
- No se puede registrar venta sin stock
- No se puede crear clase sin entrenador válido

---

## Pasos para ejecutar el proyecto

### 1. Clonar repositorio

```bash
git clone URL_DEL_REPOSITORIO
```

---

### 2. Crear base de datos MySQL

Crear base de datos:

```sql
CREATE DATABASE gymdb;
```

Modificar credenciales en cada archivo:

```properties
application.properties
```

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gymdb
spring.datasource.username=root
spring.datasource.password=tu_password
```

---

### 3. Instalar dependencias Maven

Desde la raíz:

```bash
mvn clean install
```

---

### 4. Ejecutar microservicios

Ejecutar cada servicio individualmente desde su clase principal:

- ClienteServiceApplication
- MembresiaServiceApplication
- ClaseServiceApplication
- ReservaServiceApplication
- PagoServiceApplication
- VentaServiceApplication
- ProductoServiceApplication
- EntrenadorServiceApplication
- AsistenciaServiceApplication
- TipoMembresiaServiceApplication

---

### 5. Verificar puertos

Cada microservicio debe ejecutarse en puerto diferente.

Ejemplo:

8080 -> gateway-service
8081 -> cliente-service
8082 -> entrenador-service
8083 -> tipomembresia-service
8084 -> clase-service
8085 -> membresia-service
8086 -> producto-service
8087 -> reserva-service
8088 -> asistencia-service
8089 -> venta-service
8090 -> pago-service

---

## Ejecucion con Docker

El proyecto incluye un `docker-compose.yml` para levantar MySQL, los microservicios del dominio y el API Gateway.

### Requisitos

- Docker Desktop abierto y en estado "Running".
- Internet disponible la primera vez, porque Docker descarga imagenes base y Maven descarga dependencias.
- Ejecutar los comandos desde la raiz del proyecto.

```powershell
cd C:\Users\WARRIOR\Desktop\gymFinal-master\gymFinal
```

### Levantar todo el sistema

Primera ejecucion o despues de cambios importantes:

```powershell
docker compose up -d --build
```

Ejecuciones siguientes:

```powershell
docker compose up -d
```

Verificar contenedores:

```powershell
docker compose ps
```

Ver logs generales:

```powershell
docker compose logs -f
```

Ver logs de un servicio especifico:

```powershell
docker compose logs -f gateway-service
docker compose logs -f cliente-service
```

### Puertos disponibles

- Gateway: http://localhost:8080
- Cliente: http://localhost:8081
- Entrenador: http://localhost:8082
- Tipo membresia: http://localhost:8083
- Clase: http://localhost:8084
- Membresia: http://localhost:8085
- Producto: http://localhost:8086
- Reserva: http://localhost:8087
- Asistencia: http://localhost:8088
- Venta: http://localhost:8089
- Pago: http://localhost:8090
- MySQL en Docker: localhost:3307, base de datos `gym_cliente`, usuario `gym`, password `gym`

### Pruebas rapidas para la defensa

Revisar estado del Gateway:

```powershell
curl http://localhost:8080/actuator/health
```

Revisar rutas cargadas en Gateway:

```powershell
curl http://localhost:8080/actuator/gateway/routes
```

Probar un microservicio directo:

```powershell
curl http://localhost:8081/api/clientes
```

Probar el mismo microservicio pasando por Gateway:

```powershell
curl http://localhost:8080/api/clientes
```

Abrir Swagger de un servicio:

```text
http://localhost:8081/doc/swagger-ui.html
http://localhost:8086/doc/swagger-ui.html
http://localhost:8090/doc/swagger-ui.html
```

Tambien se puede ejecutar una prueba rapida automatizada:

```powershell
.\scripts\probar-docker.ps1
```

### Apagar el sistema

Apagar conservando datos de MySQL:

```powershell
docker compose down
```

Apagar y borrar la base de datos Docker para empezar limpio:

```powershell
docker compose down -v
```

### Explicacion tecnica breve

- `docker-compose.yml` crea una red interna para que los contenedores se comuniquen por nombre.
- En ejecucion local los YAML usan `localhost`, por ejemplo `http://localhost:8081`.
- En ejecucion Docker se activa `SPRING_PROFILES_ACTIVE=docker`; con ese perfil los servicios usan nombres internos como `mysql`, `cliente-service`, `membresia-service` y `producto-service`.
- El Gateway queda en el puerto `8080` y centraliza las rutas `/api/clientes/**`, `/api/membresias/**`, `/api/productos/**`, `/api/pagos/**`, entre otras.

---

## Estado del proyecto

Proyecto finalizado y funcional para evaluación académica.

---
Sistema de Gestión de Gimnasio - Arquitectura de Microservicios
Rutas principales del API Gateway

El sistema utiliza Spring Cloud Gateway como punto único de acceso a los microservicios.

http://localhost:8084/doc/swagger-ui.html

Servicio	Ruta Gateway
Cliente Service	http://localhost:8080/api/clientes
Entrenador Service	http://localhost:8080/api/entrenadores
Tipo Membresía Service	http://localhost:8080/api/tipos-membresias
Membresía Service	http://localhost:8080/api/membresias
Clase Service	http://localhost:8080/api/clases
Reserva Service	http://localhost:8080/api/reservas
Producto Service	http://localhost:8080/api/productos
Venta Service	http://localhost:8080/api/ventas
Pago Service	http://localhost:8080/api/pagos
Asistencia Service	http://localhost:8080/api/asistencias
Documentación Swagger

La documentación de cada microservicio puede consultarse mediante Swagger UI.

Servicio	Swagger UI
Cliente Service	http://localhost:8081/swagger-ui/index.html
Entrenador Service	http://localhost:8082/swagger-ui/index.html
Tipo Membresía Service	http://localhost:8083/swagger-ui/index.html
Clase Service	http://localhost:8084/swagger-ui/index.html
Membresía Service	http://localhost:8085/swagger-ui/index.html
Producto Service	http://localhost:8086/swagger-ui/index.html
Reserva Service	http://localhost:8087/swagger-ui/index.html
Asistencia Service	http://localhost:8088/swagger-ui/index.html
Venta Service	http://localhost:8089/swagger-ui/index.html
Pago Service	http://localhost:8090/swagger-ui/index.html
Pruebas Unitarias

El proyecto incorpora pruebas unitarias para validar la lógica de negocio de los microservicios.

Objetivos alcanzados:

Validación de servicios y controladores.
Pruebas de creación, modificación, consulta y eliminación de registros.
Validación de excepciones y reglas de negocio.
Cobertura mínima esperada: 80%.

Para ejecutar las pruebas:

mvn test

Para generar el reporte de cobertura:

mvn verify

Convención de Commits

Se utilizó una estrategia de commits descriptivos y técnicos para facilitar la trazabilidad de los cambios.

Ejemplos:

feat(cliente): implementar CRUD de clientes
feat(producto): agregar validaciones de stock
feat(clase): integrar horarios y relaciones JPA
feat(gateway): configurar rutas de microservicios
docs(swagger): documentar endpoints de reservas
refactor(service): optimizar lógica de membresías
fix(reserva): corregir validación de cupos disponibles
test(cliente): agregar pruebas unitarias del servicio

No se utilizaron mensajes genéricos como:

cambios
actualización
prueba
arreglo
commit final

Todos los commits describen de forma técnica y precisa la modificación realizada.

## Autoría

Proyecto desarrollado con fines académicos para asignatura de desarrollo backend con arquitectura de microservicios.
