package com.erpLite.enterprise_erp_lite.controller;

import com.erpLite.enterprise_erp_lite.dto.FacturaRequest;
import com.erpLite.enterprise_erp_lite.service.FacturacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * CONTROLADOR (CONTROLLER):
 * Es la cara visible de tu sistema.
 * Recibe peticiones HTTP (JSON) y devuelve respuestas HTTP (200 OK, 400 Bad Request).
 */
@RestController // <--- Le dice a Spring: "Este es un controlador REST (para APIs)".
@RequestMapping("/api/v1/Enterprise") // <--- Define la ruta base: localhost:8080/api/v1/Enterprise
public class EnterpriseController {

    private final FacturacionService facturacionService;

    // Inyección de dependencias: El Mesero necesita conocer al Chef (Service)
    @Autowired
    public EnterpriseController(FacturacionService facturacionService) {
        this.facturacionService = facturacionService;
    }

    // Definimos el endpoint POST.
    // La ruta completa será: POST localhost:8080/api/v1/enterprise/emitir
    @PostMapping("/emitir")
    public ResponseEntity<String> emitirFactura(@Valid @RequestBody FacturaRequest request) {

        // @Valid: Activa las validaciones que pusimos en el DTO (como @NotBlank).
        // @RequestBody: Le dice a Spring "Toma el JSON que viene y conviértelo a objeto Java".

        try {
            // Llamamos al Chef para que haga el trabajo
            String xmlResultado = facturacionService.procesarFactura(request);

            // Si todo sale bien, respondemos 200 OK y mostramos el XML
            return ResponseEntity.ok(xmlResultado);

        } catch (IllegalArgumentException e) {
            // Si el servicio se queja (ej: monto bajo), respondemos 400 Bad Request
            return ResponseEntity.badRequest().body("Error de validación: " + e.getMessage());

        } catch (Exception e) {
            // Si explota algo que no esperábamos
            return ResponseEntity.internalServerError().body("Ocurrió un error grave: " + e.getMessage());
        }
    }
}