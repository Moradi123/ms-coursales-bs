package ms_coursales_bs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import ms_coursales_bs.model.dto.CourseSalesDTO;
import ms_coursales_bs.model.dto.WebPayTransactionResponseDTO;
import ms_coursales_bs.service.CourseSalesService;

import ms_coursales_bs.model.dto.SalesDTO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/s_courses")
public class CourseSalesController {

    @Autowired
    private CourseSalesService saleService;

    @PostMapping("/initiate-payment")
    public ResponseEntity<WebPayTransactionResponseDTO> initiatePayment(@RequestBody CourseSalesDTO saleRequest) {
        WebPayTransactionResponseDTO response = saleService.initiateSaleTransaction(saleRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/transaction/confirm")
    public RedirectView confirmPayment(@RequestParam(name = "token_ws") String token) {
        SalesDTO confirmedSale = saleService.confirmSaleTransaction(token);
        
        
        if (confirmedSale != null) {
            return new RedirectView("http://localhost/fe-ecommerce/resultado.html?status=success&saleId=" + confirmedSale.getId());
        } else {
            return new RedirectView("http://localhost/fe-ecommerce/resultado.html?status=failed");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesDTO> findSalesById(@PathVariable("id") Long id) {
        SalesDTO salesDTO = saleService.findSalesById(id);
        return (salesDTO != null) ?
               new ResponseEntity<>(salesDTO, HttpStatus.OK) :
               new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}