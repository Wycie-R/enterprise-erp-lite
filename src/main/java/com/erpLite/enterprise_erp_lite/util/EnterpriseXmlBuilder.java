package com.erpLite.enterprise_erp_lite.util;

import com.erpLite.enterprise_erp_lite.dto.FacturaRequest;
import org.springframework.stereotype.Component;

/**
 * Esta clase es el "Traductor".
 * Su único trabajo es recibir un objeto Java moderno y convertirlo
 * en el XML antiguo y estricto que pide la DNIT.
 */
@Component // <--- IMPORTANTE: Esto le dice a Spring: "Guárdame en tu caja de herramientas".
public class EnterpriseXmlBuilder {

    public String construirSoapEnvelope(FacturaRequest factura) {
        StringBuilder xml = new StringBuilder();

        // 1. EL SOBRE (Envelope): Es como el sobre de una carta real.
        // Define que esto es un mensaje SOAP.
        xml.append("<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'>");

        // 2. EL ENCABEZADO (Header): Enterprise no suele pedir nada aquí en la versión estándar, lo dejamos vacío.
        xml.append("<soapenv:Header/>");

        // 3. EL CUERPO (Body): Aquí va el contenido real de la factura.
        xml.append("<soapenv:Body>");

        // rDE: Root del Documento Electrónico (Tag raíz de Enterprise)
        xml.append("<rDE>");

        // Versión del formato (150 es la versión actual aprox)
        xml.append("<dVerFor>150</dVerFor>");

        // Datos Generales de la Operación
        xml.append("<gDatGralOpe>");
        // Fecha de emisión que nos pasó el cliente
        xml.append("<dFeEmiDE>").append(factura.getFechaEmision()).append("</dFeEmiDE>");

        // Grupo del Emisor (Quien vende)
        xml.append("<gEmis>");
        // RUC del emisor
        xml.append("<dRucEm>").append(factura.getRucContribuyente()).append("</dRucEm>");
        // Nombre o Razón Social
        xml.append("<dRazSoc>").append(factura.getRazonSocial()).append("</dRazSoc>");
        xml.append("</gEmis>");
        xml.append("</gDatGralOpe>");

        // Datos Específicos (Número de factura)
        xml.append("<gTimb>");
        xml.append("<dNumDoc>").append(factura.getNumeroFactura()).append("</dNumDoc>");
        xml.append("</gTimb>");

        // Totales (El dinero)
        xml.append("<gTotSub>");
        // El monto total que nos pasaron
        xml.append("<dTotOpe>").append(factura.getMontoTotal()).append("</dTotOpe>");
        xml.append("</gTotSub>");

        xml.append("</rDE>");

        xml.append("</soapenv:Body>");
        xml.append("</soapenv:Envelope>");

        // Convertimos todo el StringBuilder a un simple texto (String) y lo devolvemos.
        return xml.toString();
    }
}