package ms_coursales_bs.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO para la solicitud de creación de una transacción en WebPay")
public class WebPayTransactionRequestDTO {

    @JsonProperty("buy_order")
    @Schema(description = "Orden de compra única para la transacción", example = "edu-a1b2c3d4")
    private String buyOrder;

    @JsonProperty("session_id")
    @Schema(description = "ID de sesión del cliente", example = "SESS_1a7b3c9d")
    private String sessionId;

    @JsonProperty("amount")
    @Schema(description = "Monto a cobrar", example = "80000")
    private int    amount;

    @JsonProperty("return_url")
    @Schema(description = "URL a la que WebPay redirigirá al cliente después del pago", example = "http://localhost:8181/api/s_courses/transaction/confirm")
    private String returnUrl;
}
