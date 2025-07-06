package ms_coursales_bs.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO para el detalle de una venta")
public class CourseSalesDetailDTO {

    @Schema(description = "ID del detalle", example = "43")
    private Long id;
    
    @Schema(description = "Producto (curso) vendido")
    private CourseDTO product;
    
    @Schema(description = "Cantidad", example = "1")
    private Long quantity; 
    
    @Schema(description = "ID de la venta a la que pertenece este detalle", example = "26")
    private Long salesId;
}
