package com.erpLite.enterprise_erp_lite.dto;

// Importamos las validaciones (para que los datos no vengan vacíos)

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Esta clase representa los datos que nos envía el cliente (Frontend/Postman).
 * Es un DTO (Data Transfer Object).
 */
@Data // <--- ESTO ES MAGIA: Genera automáticamente Getters, Setters, toString, equals y hashCode.
@AllArgsConstructor // <--- Genera un constructor con todos los argumentos.
@NoArgsConstructor  // <--- Genera un constructor vacío (necesario para que Spring funcione).
public class FacturaRequest {

    // @NotBlank verifica que el texto no sea null ni esté vacío ni sea solo espacios en blanco.
    @NotBlank(message = "El RUC del contribuyente es obligatorio")
    private String rucContribuyente;

    @NotBlank(message = "La Razón Social es obligatoria")
    private String razonSocial;

    // @NotNull es para objetos (Double) y @Positive asegura que sea mayor a cero.
    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto total debe ser mayor a cero")
    private Double montoTotal;

    @NotBlank(message = "El número de factura es obligatorio")
    private String numeroFactura;

    // Agregamos fecha para hacerlo más real
    @NotBlank(message = "La fecha es obligatoria (formato YYYY-MM-DD)")
    private String fechaEmision;

}