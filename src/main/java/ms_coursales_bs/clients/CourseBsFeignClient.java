package ms_coursales_bs.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ms_coursales_bs.model.dto.CourseDTO;

@FeignClient(name = "ms-courses-bs", url = "http://localhost:8182")
public interface CourseBsFeignClient {

    @GetMapping("/api/courses/{id}")
    public ResponseEntity<CourseDTO> findProductById(@PathVariable("id") Long id);
}
