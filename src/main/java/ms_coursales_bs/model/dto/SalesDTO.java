package ms_coursales_bs.model.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalesDTO {

    @JsonProperty(value = "venta_id")
    private Long   id;
    @JsonProperty(value = "venta_monto")
    private Long   amount; 
    @JsonProperty(value = "venta_cliente_id")
    private Long   customerId;
    @JsonProperty(value = "venta_detalle")
    private List<CourseSalesDetailDTO> salesDetailDtoList;
}
