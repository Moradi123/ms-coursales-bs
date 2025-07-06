package ms_coursales_bs.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO de respuesta de WebPay al iniciar una transacción")
public class WebPayTransactionResponseDTO {

    @Schema(description = "Token único para la transacción", example = "ab02a1etc.....")
    private String token;
    
    @Schema(description = "URL del formulario de pago de WebPay a la que se debe redirigir al cliente")
    private String url;
}
