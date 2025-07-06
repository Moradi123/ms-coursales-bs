package ms_coursales_bs.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO para iniciar una transacción de venta de cursos")
public class CourseSalesDTO {

    @Schema(description = "ID de la sesión", example = "SESS_1a7b3c9d")
    private String sessionId;
    
    @Schema(description = "Fecha de la solicitud", nullable = true)
    private LocalDateTime date;
    
    @Schema(description = "Monto total de la compra a procesar", example = "80000")
    private Integer amount;
    
    @Schema(description = "Lista de productos que se están comprando")
    private List<CourseDTO> products;
}

