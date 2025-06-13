# 📌TransaccionesSofka Despliegue📌

Arquitectura Basada en Contenedores<br>
Este proyecto utiliza una arquitectura basada en contenedores, lo que permite desplegar fácilmente los servicios mediante Docker.<br>
<br>
🚀 Instrucciones para levantar los contenedores<br>
Ejecuta el siguiente comando en la terminal:<br>
<code>
docker-compose up
</code>
Esto iniciará todos los servicios definidos en el archivo docker-compose.yml.<br>
<br>
Ver las conexxiones del eureka con :<br>
<code>
http://localhost:8761/
</code>

📌 Consulta de Información<br>
Puedes probar los endpoints utilizando la colección de Postman disponible en el siguiente enlace:<br>
https://documenter.getpostman.com/view/13910567/2sAYdoGTRa

# TransaccionesSofka Documentacion

Este documento ofrece una visión general completa del sistema **TransaccionesSofka**, una plataforma de gestión de transacciones financieras basada en microservicios.

## 📌 Propósito del Sistema

**TransaccionesSofka** es un sistema distribuido para el procesamiento de transacciones financieras, diseñado para gestionar clientes, cuentas y movimientos financieros utilizando una arquitectura de microservicios.

El sistema expone APIs REST para:
- Gestión de clientes
- Operaciones sobre cuentas
- Procesamiento de transacciones
- Reportes financieros

La plataforma implementa una arquitectura basada en contenedores, permitiendo un despliegue escalable y eficiente de los servicios financieros. Incluye descubrimiento de servicios, comunicación orientada a eventos y documentación completa de APIs.

---

## 🧩 Arquitectura del Sistema

**TransaccionesSofka** sigue un patrón de arquitectura de microservicios con las siguientes características clave:

- 📦 Despliegue basado en contenedores usando **Docker Compose**
- 🧭 Descubrimiento de servicios mediante **Eureka Server**
- 📡 Comunicación orientada a eventos con **Apache Kafka**
- 📚 Modelo de datos compartido a través de una librería común de entidades
- 📃 Diseño orientado a API con documentación en **OpenAPI / Swagger**

## 🧩 Arquitectura General del Sistema

El sistema **TransaccionesSofka** implementa una arquitectura de microservicios con descubrimiento de servicios y comunicación asíncrona mediante mensajería.

### 🔧 Servicios Principales

El sistema está compuesto por **cuatro servicios principales**:

- **Movimientos_Cliente** (puerto `8080`):  
  Maneja operaciones de clientes  
  _Referencia: `MovimientosApplication.java:7-8`_

- **Movimientos_Transaccion** (puerto `8081`):  
  Gestiona cuentas, movimientos y reportes  
  _Referencia: `MovimientosApplication.java:7-8`_

- **Movimientos_Eureka** (puerto `8761`):  
  Servidor de registro y descubrimiento de servicios  
  _Referencia: `MovimientosApplication.java:8-9`_

- **Orquestador**:  
  Gateway API implementado con **Spring Cloud Gateway**  
  _Referencia: `OrquestadorApplication.java:7-8`_

---

## 🧱 Stack Tecnológico

| Componente        | Tecnología                         | Referencia     |
|-------------------|-------------------------------------|----------------|
| **Framework**     | Spring Boot 2.7.3 (core), 3.4.3 (orquestador) | `pom.xml:6-8`  |
| **Service Discovery** | Netflix Eureka                | `pom.xml:99-101` |
| **Mensajería**    | Apache Kafka                       | `pom.xml:108-110` |
| **Base de Datos** | PostgreSQL con JPA/Hibernate       | `pom.xml:34-37` |
| **Documentación** | OpenAPI / Swagger                  | `pom.xml:62-65` |

---

## 🗃️ Entidades Compartidas

Los servicios comparten el módulo **`MovimientosEntidad`**, que contiene:

- Entidades JPA comunes
- Repositorios compartidos
---
## 🛠️ Tecnologías Utilizadas

**TransaccionesSofka** está construido sobre la siguiente base tecnológica:

| Componente            | Tecnología                        | Propósito                                      |
|-----------------------|-----------------------------------|------------------------------------------------|
| Framework             | Spring Boot                       | Fundamento de los microservicios              |
| Descubrimiento        | Spring Cloud Netflix Eureka       | Registro y descubrimiento de servicios        |
| Mensajería            | Apache Kafka                      | Comunicación orientada a eventos              |
| Base de Datos         | PostgreSQL                        | Persistencia de datos                         |
| Documentación de API  | OpenAPI / Swagger                 | Especificación de las APIs REST               |
| Contenedores          | Docker & Docker Compose           | Despliegue y orquestación                     |
| Herramienta de Build  | Maven                             | Gestión de dependencias y construcción        |
| Generación de Código  | Lombok                            | Reducción de código repetitivo (boilerplate)  |

---

## ⚙️ Detalles de Configuración de Contenedores

| Servicio                | Imagen Base              | Límites de Memoria      | Puerto | Ruta de Configuración                     |
|------------------------|--------------------------|--------------------------|--------|-------------------------------------------|
| Movimientos_Cliente     | openjdk:17-jdk-alpine     | Xmx192m, Xms192m         | 8080   | `/tmp/config/application.properties`      |
| Movimientos_Transaccion | openjdk:17-jdk-alpine     | Xmx192m, Xms192m         | 8081   | `/tmp/config/application.properties`      |
| Eureka Server           | openjdk:17-jdk-alpine     | Predeterminado           | 8761   | Predeterminado                            |

---

## 👤 Servicio de Gestión de Clientes

El servicio `Movimientos_Cliente` gestiona todas las operaciones relacionadas con los clientes y actúa como punto de entrada para la administración de usuarios.

| Componente              | Propósito                                       | Paquete                                       |
|------------------------|--------------------------------------------------|-----------------------------------------------|
| ClientControllers       | Endpoints REST para operaciones de cliente      | `com.sofka.movimientos.controllers`           |
| ClientService           | Lógica de negocio para gestión de clientes      | `com.sofka.movimientos.services`              |
| GlobalInterceptor       | Procesamiento de peticiones y respuestas        | `com.sofka.movimientos.infrastructure`        |
| GlobalExceptionHandler  | Manejo centralizado de errores                  | `com.sofka.movimientos.infrastructure`        |

**Responsabilidades Clave:**
- Registro y validación de clientes  
- Gestión del perfil del cliente  
- Integración con la capa de entidades compartidas  
- Registro en Eureka para descubrimiento de servicios

## 🗃️ Estructura de Tablas

| Entidad     | Tabla           | Clave Primaria     | Relaciones Clave                           |
|-------------|------------------|--------------------|---------------------------------------------|
| Client      | `mov_tclient`    | `idClient` (UUID)  | Hereda de `Person`                          |
| Account     | `mov_taccount`   | `idaccount` (UUID) | Referencia a `Client.idClient`             |
| Movements   | `mov_tmovement`  | `idmovement` (UUID)| Referencia a `Account.idaccount`           |

---

## 🧬 Características de las Entidades

### 🔹 Entidad Client

La entidad `Client` hereda de `Person` e incluye atributos específicos del sistema financiero.

- **Herencia**: Hereda desde la clase base `Person` utilizando herencia JPA  
  📄 `Movimientos_Entidad/src/main/java/com/sofka/movimientos/entities/Client.java:16`
- **Seguridad**: El campo `password` se excluye de la serialización JSON  
  📄 `Client.java:15`
- **Estado lógico**: Campo booleano `status` para eliminar lógicamente registros  
  📄 `Client.java:31-32`
- **UUID como ID**: Uso de UUID autogenerado para entornos distribuidos  
  📄 `Client.java:20-23`

---

### 🔸 Entidad Movements

La entidad `Movements` representa las transacciones financieras con seguimiento detallado:

- **Precisión financiera**: Uso de `BigDecimal` para los valores monetarios  
  📄 `Movements.java:34-39`
- **Relación con Account**: Relación `many-to-one` con carga diferida (lazy)  
  📄 `Movements.java:45-48`
- **Seguimiento de transacciones**: Guarda el valor del movimiento y el saldo actualizado  
  📄 `Movements.java:34-39`
- **Auditoría**: Incluye fecha y tipo de movimiento para trazabilidad  
  📄 `Movements.java:25-31`

---

## 🧩 Implementación del Patrón Repositorio

El sistema utiliza repositorios de Spring Data JPA con métodos personalizados para el acceso a datos.

### 📁 Estructura del Repositorio

La interfaz `ClientRepository` ejemplifica cómo se implementan consultas específicas del negocio:

- 🔍 **Búsqueda por identificación**:  
  `findByIdentificationCard()` para recuperar clientes únicos  
  📄 `ClientRepository.java:13`

- ✅ **Filtrado por estado activo**:  
  `findByStatusTrue()` para obtener sólo clientes activos  
  📄 `ClientRepository.java:15`

- 🔎 **Consultas JPQL personalizadas**:  
  Uso de `@Query` para filtrados complejos  
  📄 `ClientRepository.java:17-18`

---

📂 Fuentes:

- `src/main/java/com/sofka/movimientos/entities/Client.java`
- `src/main/java/com/sofka/movimientos/entities/Movements.java`
- `src/main/java/com/sofka/movimientos/repositories/ClientRepository.java`
