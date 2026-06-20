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

- Java 17
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

- ResourceNotFoundException
- BusinessException
- GlobalExceptionHandler

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

- 8081 → asistencia-service
- 8082 → membresia-service
- 8083 → cliente-service
- 8084 → clase-service
- 8085 → producto-service
- 8086 → entrenador-service
- 8087 → reserva-service
- 8088 → pago-service
- 8089 → venta-service
- 8090 → tipomembresia-service

---

## Estado del proyecto

Proyecto finalizado y funcional para evaluación académica.

---

## Autoría

Proyecto desarrollado con fines académicos para asignatura de desarrollo backend con arquitectura de microservicios.
