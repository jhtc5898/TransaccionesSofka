# TransaccionesSofka

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

# TransaccionesSofka

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
