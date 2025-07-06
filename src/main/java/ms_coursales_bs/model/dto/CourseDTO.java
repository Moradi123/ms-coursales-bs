package ms_coursales_bs.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "DTO para representar un curso en el sistema")
public class CourseDTO {
    
    @JsonProperty(value = "course_id")
    @Schema(description = "Id del curso", example = "1")
    private Long id;
    
    @JsonProperty(value = "course_name")
    @Schema(description = "Nombre del curso", example = "Base de Datos")
    private String title;
    
    @JsonProperty(value = "description")
    @Schema(description = "Descripcion de que va a tratar el curso", example = ".")
    private String description;
    
    @JsonProperty(value = "instructor_id")
    @Schema(description = "Id del prefesor de la clase", example = "101")
    private Long instructorId;
    
    @JsonProperty(value = "price")
    @Schema(description = "Precio de los cursos", example = "50.000")
    private Long price;
}