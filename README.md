# Adaptador REST a SOAP (Spring Boot Middleware)

Desarrollado con fines educativos y de portafolio, demostrando patrones de integración empresarial.

## 📋 Resumen

Este proyecto es una solución de backend robusta diseñada para resolver un problema común en el entorno empresarial: **La interoperabilidad entre Microservicios modernos y Sistemas Legacy.**

Funciona como una capa intermedia (middleware) que recibe cargas de trabajo en formato **JSON** a través de endpoints REST, aplica reglas de negocio y validaciones, transforma los datos a estructuras **XML/SOAP** estrictas (construidas manualmente para máximo rendimiento) y gestiona la comunicación con proveedores externos.

## 🚀 Características Clave

* **Arquitectura Limpia:** Separación estricta de responsabilidades en capas (Controller, Service, Repository, Utility).
* **Motor de Transformación:** Builder XML personalizado optimizado con `StringBuilder` para generar estructuras SOAP complejas sin la sobrecarga de librerías de marshalling pesadas.
* **Validación Inteligente:** Uso de Jakarta Validation API (`@Valid`, `@NotBlank`, `@Positive`) para asegurar la integridad de los datos antes de procesarlos.
* **Documentación Viva:** Integración con OpenAPI (Swagger UI) para pruebas y documentación automática de endpoints.
* **Cliente HTTP Robusto:** Implementación de cliente para comunicación con servicios externos.

## 🛠️ Stack Tecnológico

* **Lenguaje:** 21
* **Framework:** Spring Boot 4.0.0 (Web, Validation)
* **Documentación:** SpringDoc OpenAPI (Swagger)
* **Herramientas:** Lombok, Maven
* **Comunicación Externa:** RestTemplate (simulación de respuesta de un servidor).

## 🏗️ Flujo de la Arquitectura

1.  **Petición del Cliente:** Se recibe un `POST` con datos JSON.
2.  **Capa de Validación:** Verifica campos obligatorios y reglas de formato.
3.  **Capa de Servicio:** Orquesta la lógica de negocio.
4.  **Transformador (Builder):** Convierte el DTO (Objeto de Transferencia) en un sobre XML específico.
5.  **Despacho Externo:** Envía la carga útil al proveedor SOAP (simulado para este entorno).
6.  **Respuesta:** Procesa la confirmación externa y devuelve una respuesta limpia al cliente.

## 📖 Documentación de la API

Una vez que la aplicación esté corriendo, puedes acceder a la interfaz interactiva para probar los endpoints aquí: http://localhost:8080/swagger-ui/index.html

## 🔧 Instalación y Uso

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/TU_USUARIO/adaptador-rest-xml-springboot.git](https://github.com/TU_USUARIO/adaptador-rest-xml-springboot.git)
    ```
2.  **Entrar a la carpeta:**
    ```bash
    cd adaptador-rest-xml-springboot
    ```
3.  **Compilar con Maven:**
    ```bash
    mvn clean install
    ```
4.  **Ejecutar:**
    ```bash
    mvn spring-boot:run
    ```

## 💡 Ejemplo de Uso

**POST** `/api/v1/integration/emitir`

**Body (JSON):**
```json
{
  "rucContribuyente": "8000123-4",
  "razonSocial": "Empresa Ejemplo S.A.",
  "montoTotal": 150000.0,
  "numeroFactura": "001-001-0000123",
  "fechaEmision": "2025-11-27"
}