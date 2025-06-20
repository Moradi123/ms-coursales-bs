package ms_coursales_bs.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseSalesDetailDTO {

    private Long id;
    private CourseDTO product;
    private Long quantity; 
    private Long salesId;
}
