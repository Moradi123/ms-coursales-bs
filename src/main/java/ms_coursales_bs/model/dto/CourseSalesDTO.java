package ms_coursales_bs.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseSalesDTO {

    private String           sessionId;
    private LocalDateTime    date;
    private Integer          amount;
    private List<CourseDTO> products;
}

