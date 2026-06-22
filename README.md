# Sistema Inmobiliaria — Arquitectura de Microservicios

Plataforma de gestión inmobiliaria construida con **Spring Boot 3.4.5** bajo una arquitectura de **10 microservicios** independientes, orquestados mediante **Eureka Server** (Service Discovery) y **Spring Cloud Gateway** (API Gateway), con documentación interactiva en **Swagger / OpenAPI 3.0** y despliegue completo en **Docker**.

## Integrantes

- César Salvo
- Javier Miranda

---

## Tabla de contenidos

- [Arquitectura](#arquitectura)
- [Microservicios](#microservicios)
- [Tecnologías](#tecnologías)
- [Requisitos previos](#requisitos-previos)
- [Cómo ejecutar el proyecto](#cómo-ejecutar-el-proyecto)
  - [Opción A: Docker (recomendado)](#opción-a-docker-recomendado)
  - [Opción B: Ejecución local](#opción-b-ejecución-local)
- [Documentación de las APIs (Swagger)](#documentación-de-las-apis-swagger)
- [Pruebas unitarias](#pruebas-unitarias)
- [Eureka Dashboard](#eureka-dashboard)
- [Variables de entorno y perfiles](#variables-de-entorno-y-perfiles)

---

## Arquitectura

```
                              ┌─────────────────┐
                              │  Eureka Server   │
                              │     :8761        │
                              └────────▲─────────┘
                                       │ registro
                  ┌────────────────────┼────────────────────┐
                  │                    │                     │
         ┌────────▼────────┐  ┌────────▼────────┐   ┌────────▼────────┐
         │   API Gateway    │  │  10 Microserv.   │   │   MySQL x10      │
         │     :8080        │──│  de negocio       │──│  (1 BD c/u)      │
         └──────────────────┘  └──────────────────┘   └──────────────────┘
```

Cada microservicio:
- Tiene su **propia base de datos MySQL** (Database per Service)
- Expone su API REST documentada con **Swagger/OpenAPI**
- Se registra automáticamente en **Eureka**
- Se comunica con otros microservicios mediante **Feign Client** cuando necesita datos de otro dominio
- Sigue el patrón **Controller → Service → Repository/Model**

## Microservicios

| Microservicio | Puerto | Base de datos | Responsabilidad |
|---|---|---|---|
| `inmobiliaria_cliente` | 8081 | `inmobiliaria_db` | Gestión de clientes |
| `inmobiliaria-propiedad` | 8082 | `inmobiliaria_propiedades_db` | Gestión de propiedades en venta/arriendo |
| `inmobiliaria-agente` | 8083 | `inmobiliaria_agentes_db` | Gestión de agentes inmobiliarios |
| `inmobiliaria-visita` | 8084 | `inmobiliaria_visitas_db` | Agendamiento de visitas a propiedades |
| `inmobiliaria-contrato` | 8085 | `inmobiliaria_contratos_db` | Contratos de arriendo y venta |
| `inmobiliaria-pago` | 8086 | `inmobiliaria_pagos_db` | Registro y control de pagos / cuotas |
| `inmobiliaria-notificacion` | 8087 | `inmobiliaria_notificaciones_db` | Notificaciones a clientes |
| `inmobiliaria-auth` | 8088 | `inmobiliaria_auth_db` | Autenticación y registro (JWT) |
| `inmobiliaria-reporte` | 8089 | `inmobiliaria_reportes_db` | Reportes de mantención/incidencias |
| `inmobiliaria-mantencion` | 8090 | `inmobiliaria_mantenciones_db` | Solicitudes de mantención de propiedades |
| `eureka-server` | 8761 | — | Service Discovery |
| `api-gateway` | 8080 | — | Punto de entrada único / enrutamiento |

## Tecnologías

- **Java 17**
- **Spring Boot 3.4.5**
- **Spring Cloud 2024.0.1** (Eureka, Gateway, OpenFeign)
- **Spring Data JPA** + **Hibernate**
- **MySQL 8.0**
- **Flyway** — versionado de esquema de base de datos
- **Lombok**
- **springdoc-openapi 2.6.0** — documentación Swagger/OpenAPI 3.0
- **JUnit 5 + Mockito + AssertJ** — pruebas unitarias
- **Docker / Docker Compose**

## Requisitos previos

- **Java 17** (JDK)
- **Maven 3.9+**
- **Docker Desktop** (para la opción recomendada de ejecución)
- Si se ejecuta localmente sin Docker: **MySQL 8.0** instalado

## Cómo ejecutar el proyecto

### Opción A: Docker (recomendado)

Esta opción levanta automáticamente las 10 bases de datos MySQL, los 10 microservicios, Eureka y el API Gateway.

```bash
docker-compose up -d
```

Para levantar solo las bases de datos (por ejemplo, si vas a correr los microservicios desde tu IDE):

```bash
docker-compose up -d mysql-clientes mysql-auth mysql-propiedades mysql-agentes mysql-visitas mysql-contratos mysql-pagos mysql-notificaciones mysql-reportes mysql-mantenciones
```

Para ver los logs de un servicio específico:

```bash
docker-compose logs -f inmobiliaria-pago
```

Para detener todo:

```bash
docker-compose down
```

### Opción B: Ejecución local

**1.** Levanta primero **Eureka Server**:

```bash
cd eureka-server
mvn spring-boot:run
```

**2.** Levanta cada microservicio de negocio que necesites, con el perfil `local`:

```bash
cd inmobiliaria-propiedad
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

> Repite este paso para cada uno de los 10 microservicios. Cada uno requiere su base de datos MySQL correspondiente activa.

**3.** Levanta el **API Gateway** al final, una vez que los microservicios estén registrados en Eureka:

```bash
cd api-gateway
mvn spring-boot:run
```

**4.** Verifica que todo esté registrado correctamente en:
```
http://localhost:8761
```

## Documentación de las APIs (Swagger)

Cada microservicio expone su propia documentación interactiva de Swagger UI:

| Microservicio | URL Swagger UI |
|---|---|
| Clientes | `http://localhost:8081/doc/swagger-ui/index.html` |
| Propiedades | `http://localhost:8082/doc/swagger-ui/index.html` |
| Agentes | `http://localhost:8083/doc/swagger-ui/index.html` |
| Visitas | `http://localhost:8084/doc/swagger-ui/index.html` |
| Contratos | `http://localhost:8085/doc/swagger-ui/index.html` |
| Pagos | `http://localhost:8086/doc/swagger-ui/index.html` |
| Notificaciones | `http://localhost:8087/doc/swagger-ui/index.html` |
| Autenticación | `http://localhost:8088/doc/swagger-ui/index.html` |
| Reportes | `http://localhost:8089/doc/swagger-ui/index.html` |
| Mantenciones | `http://localhost:8090/doc/swagger-ui/index.html` |

Además, el **API Gateway** centraliza el acceso a toda la documentación desde un único punto, con un selector para cambiar entre microservicios:

```
http://localhost:8080/doc/swagger-ui/index.html
```

## Pruebas unitarias

Cada microservicio cuenta con pruebas unitarias sobre su capa de servicio (lógica de negocio), utilizando **JUnit 5**, **Mockito** y **AssertJ**. Las dependencias externas (repositorios y Feign Clients) se simulan mediante mocks, por lo que estas pruebas **no requieren** base de datos ni otros microservicios activos.

Para ejecutar las pruebas de un microservicio:

```bash
cd inmobiliaria-pago
mvn test
```

Para ejecutar las pruebas de todos los microservicios, repetir el comando en cada carpeta.

## Eureka Dashboard

Una vez levantado el `eureka-server`, el panel de instancias registradas está disponible en:

```
http://localhost:8761
```

Desde ahí se puede verificar que los 10 microservicios y el API Gateway estén correctamente registrados (estado `UP`) antes de realizar pruebas de integración entre servicios.

## Variables de entorno y perfiles

El proyecto utiliza perfiles de Spring para diferenciar entornos:

| Perfil | Uso |
|---|---|
| `local` | Ejecución desde el IDE, apuntando a MySQL local |
| `dev` | Ejecución vía Docker Compose |

Las credenciales de base de datos para el entorno Docker se configuran como variables de entorno dentro de `docker-compose.yml` (usuario `root`, una base de datos y un contenedor MySQL por microservicio).
