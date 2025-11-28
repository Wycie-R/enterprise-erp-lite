package com.erpLite.enterprise_erp_lite.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EnterpriseClient {

    private final RestTemplate restTemplate;

    @Autowired
    public EnterpriseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String enviarAEnterprise(String xmlFirmado) {
        // 1. Definimos la URL de destino (En producción sería la de la DNIT)
        // Usamos httpbin.org para simular, ya que acepta cualquier cosa que le enviemos.
        String urlEnterprise = "https://httpbin.org/post";

        // 2. Preparamos las cabeceras (decimos "te voy a enviar XML")
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);

        // 3. Empaquetamos el XML y las cabeceras en un sobre HTTP
        HttpEntity<String> request = new HttpEntity<>(xmlFirmado, headers);

        try {
            System.out.println("CLIENTE: Conectando con " + urlEnterprise + "...");

            // 4. ¡FUEGO! Enviamos la petición POST
            // postForObject(url, solicitud, tipo_de_respuesta_esperada)
            String respuesta = restTemplate.postForObject(urlEnterprise, request, String.class);

            System.out.println("CLIENTE: ¡Respuesta recibida!");
            return respuesta;

        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con Enterprise: " + e.getMessage());
        }
    }
}