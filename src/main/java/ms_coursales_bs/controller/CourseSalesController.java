package ms_coursales_bs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import ms_coursales_bs.model.dto.CourseSalesDTO;
import ms_coursales_bs.model.dto.WebPayTransactionResponseDTO;
import ms_coursales_bs.service.CourseSalesService;
import ms_coursales_bs.model.dto.SalesDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Hidden;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/s_courses")
@Tag(name = "Ventas de Cursos - BS", description = "Operaciones de negocio para la venta de cursos y pagos")
public class CourseSalesController {

    @Autowired
    private CourseSalesService saleService;

    @Operation(summary = "Iniciar un pago con WebPay", description = "Recibe una solicitud de venta y genera una transacción en la pasarela de pagos, devolviendo una URL para redirigir al usuario.")
    @PostMapping("/initiate-payment")
    public ResponseEntity<WebPayTransactionResponseDTO> initiatePayment(@RequestBody CourseSalesDTO saleRequest) {
        WebPayTransactionResponseDTO response = saleService.initiateSaleTransaction(saleRequest);
        return ResponseEntity.ok(response);
    }

    @Hidden // Se oculta porque es una URL de callback, no para ser llamada directamente por un usuario
    @GetMapping("/transaction/confirm")
    public RedirectView confirmPayment(@RequestParam(name = "token_ws") String token) {
        SalesDTO confirmedSale = saleService.confirmSaleTransaction(token);
        
        if (confirmedSale != null) {
            return new RedirectView("http://localhost/fe-ecommerce/resultado.html?status=success&saleId=" + confirmedSale.getId());
        } else {
            return new RedirectView("http://localhost/fe-ecommerce/resultado.html?status=failed");
        }
    }

    @Operation(summary = "Buscar una venta por su ID", description = "Devuelve los detalles de una venta procesada por la capa de negocio.")
    @GetMapping("/{id}")
    public ResponseEntity<SalesDTO> findSalesById(
            @Parameter(description = "ID de la venta a consultar", required = true, example = "1")
            @PathVariable("id") Long id) {
        SalesDTO salesDTO = saleService.findSalesById(id);
        return (salesDTO != null) ?
               new ResponseEntity<>(salesDTO, HttpStatus.OK) :
               new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}