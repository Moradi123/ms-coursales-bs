package ms_coursales_bs.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ms_coursales_bs.model.dto.SalesDTO;

@FeignClient(name = "ms-coursales-db", url = "http://localhost:8282")
public interface CourseSalesDbFeignClient {

    @GetMapping("/api/c_sales/{id}")
    public ResponseEntity<SalesDTO> findSalesById(@PathVariable("id") Long id);

    @PostMapping("/api/c_sales")
    public ResponseEntity<SalesDTO> insertSale(@RequestBody SalesDTO salesDTO);
}
