package com.erpLite.enterprise_erp_lite.service;

import com.erpLite.enterprise_erp_lite.dto.FacturaRequest;
import com.erpLite.enterprise_erp_lite.util.EnterpriseXmlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.erpLite.enterprise_erp_lite.client.EnterpriseClient;

/**
 * SERVICIO (SERVICE):
 * Aquí vive la Lógica de Negocio.
 * Es el intermediario que orquesta todo.
 */
@Service // <--- Etiqueta vital. Le dice a Spring: "Aquí hay lógica importante".
public class FacturacionService {

    // Declaramos la herramienta que vamos a necesitar, pero NO le hacemos "new EnterpriseXmlBuilder()".
    // Dejamos que Spring nos la pase.
    private final EnterpriseXmlBuilder enterpriseXmlBuilder;
    private final EnterpriseClient enterpriseClient;

    // --- INYECCIÓN DE DEPENDENCIAS POR CONSTRUCTOR ---
    // Esta es la forma más profesional de conectar componentes.
    // Spring ve este constructor, busca el componente "EnterpriseXmlBuilder" que creamos antes
    // y lo "inyecta" aquí automáticamente.
    @Autowired
    public FacturacionService(EnterpriseXmlBuilder enterpriseXmlBuilder, EnterpriseClient enterpriseClient) {
        this.enterpriseXmlBuilder = enterpriseXmlBuilder;
        this.enterpriseClient = enterpriseClient;
    }

    // Este es el método principal que llamará el Controlador
    public String procesarFactura(FacturaRequest request) {

        System.out.println("SERVICE: 1. Iniciando proceso para cliente: " + request.getRazonSocial());

        // A. VALIDACIONES DE NEGOCIO
        // (Validar cosas que no sean solo formato, sino reglas de la empresa)
        if (request.getMontoTotal() < 5000) {
            // Ejemplo: No facturamos montos hormiga
            throw new IllegalArgumentException("El monto mínimo para facturar es 5000 Gs.");
        }

        // B. TRANSFORMACIÓN
        // Usamos nuestra herramienta (Builder) para convertir el objeto a XML
        System.out.println("SERVICE: 2. Generando XML para la DNIT...");
        String xmlParaDnit = enterpriseXmlBuilder.construirSoapEnvelope(request);

        // C. ENVÍO (Simulado por ahora)
        System.out.println("SERVICE: 3. Enviando a Enterprise...");
        String respuestaDelServidor = enterpriseClient.enviarAEnterprise(xmlParaDnit);

        // Retornamos la respuesta que nos dio el servidor externo
        return "Respuesta del Servidor Externo:\n" + respuestaDelServidor;
    }
}