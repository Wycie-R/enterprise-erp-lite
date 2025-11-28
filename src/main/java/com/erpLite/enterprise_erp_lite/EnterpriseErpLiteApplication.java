package com.erpLite.enterprise_erp_lite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EnterpriseErpLiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnterpriseErpLiteApplication.class, args);
    }

    // AGREGA ESTO:
    // Creamos el "navegador" que usará Spring para salir a internet
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
