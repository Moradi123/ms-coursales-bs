package ms_coursales_bs.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDTO {
    @JsonProperty(value = "course_id")
    private Long id;
    @JsonProperty(value = "course_name")
    private String title;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "instructor_id")
    private Long instructorId;
    @JsonProperty(value = "price")
    private Long price;

}