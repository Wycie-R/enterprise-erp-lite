# Enterprise ERP Lite — REST → SOAP Integration Middleware

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-6DB33F?logo=springboot&logoColor=white)
![OpenAPI](https://img.shields.io/badge/OpenAPI-Swagger%20UI-85EA2D?logo=swagger&logoColor=black)
![Maven](https://img.shields.io/badge/Maven-build-C71A36?logo=apachemaven)

> 🇬🇧 **English summary**
>
> A Spring Boot 4 middleware that bridges modern REST clients and legacy SOAP/XML services. It receives an invoice as JSON, validates it (format rules with Jakarta Validation plus business rules in the service layer), transforms it into a SOAP envelope modeled on Paraguay's electronic invoice format (DNIT/SIFEN `rDE`), and dispatches it to an external provider through an HTTP client. The external provider is simulated with httpbin.org so the project runs anywhere. Built as a portfolio project to demonstrate enterprise integration patterns, layered architecture and constructor-based dependency injection.

---

## 📋 Resumen

Middleware de backend que resuelve un problema muy común en empresas: **hacer que sistemas modernos (REST/JSON) se comuniquen con sistemas legacy (SOAP/XML)**.

El servicio recibe una factura en **JSON**, la valida, la transforma en un **sobre SOAP** con una estructura simplificada basada en el Documento Electrónico de la DNIT (`rDE`) y la envía a un proveedor externo. El proveedor está simulado con [httpbin.org](https://httpbin.org) para que el proyecto pueda ejecutarse sin credenciales.

## 🚀 Características

- **Arquitectura en capas:** `Controller` → `Service` → `Util (XML Builder)` → `Client`, cada una con una única responsabilidad.
- **Validación en dos niveles:**
  - *Formato:* Jakarta Validation (`@NotBlank`, `@NotNull`, `@Positive`) en el DTO.
  - *Negocio:* reglas en la capa de servicio (por ejemplo, monto mínimo facturable de 5.000 Gs).
- **Transformación JSON → SOAP/XML:** builder propio con `StringBuilder`, sin dependencias pesadas de marshalling.
- **Cliente HTTP desacoplado:** `RestTemplate` configurado como bean e inyectado por constructor.
- **Manejo de errores HTTP:** respuestas `400 Bad Request` para errores de validación y `500` para fallas inesperadas.
- **Documentación viva:** Swagger UI generado automáticamente con SpringDoc OpenAPI.

## 🛠️ Stack

| Área | Tecnología |
|---|---|
| Lenguaje | Java 21 |
| Framework | Spring Boot 4.0.0 (Web MVC, Validation, DevTools) |
| Documentación | SpringDoc OpenAPI 3 (Swagger UI) |
| Cliente HTTP | RestTemplate |
| Herramientas | Maven (wrapper incluido), Lombok |

## 🏗️ Arquitectura

```mermaid
flowchart LR
    A[Cliente REST<br/>JSON] -->|POST /emitir| B[EnterpriseController<br/>@Valid]
    B --> C[FacturacionService<br/>reglas de negocio]
    C --> D[EnterpriseXmlBuilder<br/>JSON → SOAP/XML]
    D --> C
    C --> E[EnterpriseClient<br/>RestTemplate]
    E -->|text/xml| F[(Proveedor SOAP<br/>simulado: httpbin)]
    F --> E --> C --> B -->|200 / 400 / 500| A
```

1. **Petición:** el cliente envía un `POST` con la factura en JSON.
2. **Validación de formato:** `@Valid` rechaza campos vacíos o montos no positivos.
3. **Servicio:** aplica reglas de negocio y orquesta el flujo.
4. **Transformación:** el builder convierte el DTO en un sobre SOAP.
5. **Despacho:** el cliente HTTP envía el XML (`Content-Type: text/xml`) al proveedor externo.
6. **Respuesta:** se devuelve la respuesta del proveedor al cliente.

## 📂 Estructura

```
src/main/java/com/erpLite/enterprise_erp_lite/
├── controller/EnterpriseController.java   # Endpoint REST y manejo de errores HTTP
├── service/FacturacionService.java        # Reglas de negocio y orquestación
├── util/EnterpriseXmlBuilder.java         # Transformación DTO → sobre SOAP
├── client/EnterpriseClient.java           # Comunicación con el proveedor externo
├── dto/FacturaRequest.java                # Contrato de entrada + validaciones
└── EnterpriseErpLiteApplication.java      # Arranque y bean RestTemplate
```

## 🔧 Instalación y uso

**Requisitos:** Java 21. Maven no hace falta porque el wrapper viene incluido.

```bash
git clone https://github.com/Wycie-R/enterprise-erp-lite.git
cd enterprise-erp-lite
./mvnw spring-boot:run        # En Windows: mvnw.cmd spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

## 📖 Documentación interactiva

Con la aplicación corriendo, abrí **Swagger UI** para probar el endpoint desde el navegador:

👉 http://localhost:8080/swagger-ui/index.html

## 💡 Ejemplo de uso

**`POST /api/v1/Enterprise/emitir`**

```bash
curl -X POST http://localhost:8080/api/v1/Enterprise/emitir \
  -H "Content-Type: application/json" \
  -d '{
    "rucContribuyente": "8000123-4",
    "razonSocial": "Empresa Ejemplo S.A.",
    "montoTotal": 150000.0,
    "numeroFactura": "001-001-0000123",
    "fechaEmision": "2025-11-27"
  }'
```

**XML SOAP generado y enviado al proveedor:**

```xml
<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'>
  <soapenv:Header/>
  <soapenv:Body>
    <rDE>
      <dVerFor>150</dVerFor>
      <gDatGralOpe>
        <dFeEmiDE>2025-11-27</dFeEmiDE>
        <gEmis>
          <dRucEm>8000123-4</dRucEm>
          <dRazSoc>Empresa Ejemplo S.A.</dRazSoc>
        </gEmis>
      </gDatGralOpe>
      <gTimb><dNumDoc>001-001-0000123</dNumDoc></gTimb>
      <gTotSub><dTotOpe>150000.0</dTotOpe></gTotSub>
    </rDE>
  </soapenv:Body>
</soapenv:Envelope>
```

**Respuestas posibles:**

| Código | Caso |
|---|---|
| `200 OK` | Factura procesada. Devuelve la respuesta del proveedor externo. |
| `400 Bad Request` | Falta un campo obligatorio, el monto no es positivo o es menor a 5.000 Gs. |
| `500 Internal Server Error` | Falla de comunicación con el proveedor u otro error inesperado. |

## 🗺️ Próximos pasos

- [ ] Escapar caracteres especiales en el XML (`&`, `<`, `>`) o migrar a JAXB.
- [ ] Reemplazar `RestTemplate` por `RestClient` y agregar timeouts y reintentos.
- [ ] Manejo global de errores con `@RestControllerAdvice` y respuestas JSON estructuradas.
- [ ] Tests unitarios del builder y tests de integración del endpoint.
- [ ] Logging con SLF4J en lugar de `System.out`.
- [ ] Dockerfile para despliegue.

---

> Proyecto desarrollado con fines educativos y de portafolio. La estructura XML es una versión simplificada y **no** representa la especificación técnica oficial de la DNIT.

**Autor:** Giovanni Rojas · [LinkedIn](https://www.linkedin.com/in/giovanni-rojas-b8aa5925a) · [GitHub](https://github.com/Wycie-R)
