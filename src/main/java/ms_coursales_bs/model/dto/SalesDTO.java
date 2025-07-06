package ms_coursales_bs.model.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO que representa una venta completada")
public class SalesDTO {

    @JsonProperty(value = "venta_id")
    @Schema(description = "ID de la venta registrada", example = "26")
    private Long   id;

    @JsonProperty(value = "venta_monto")
    @Schema(description = "Monto final de la venta", example = "80000")
    private Long   amount; 

    @JsonProperty(value = "venta_cliente_id")
    @Schema(description = "ID del cliente", example = "123")
    private Long   customerId;

    @JsonProperty(value = "venta_detalle")
    @Schema(description = "Lista con los detalles de la venta")
    private List<CourseSalesDetailDTO> salesDetailDtoList;
}
