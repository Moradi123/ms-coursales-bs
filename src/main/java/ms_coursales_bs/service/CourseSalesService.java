package ms_coursales_bs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ms_coursales_bs.clients.CourseBsFeignClient;
import ms_coursales_bs.clients.CourseSalesDbFeignClient;
import ms_coursales_bs.clients.WebPayFeignClient;
import ms_coursales_bs.model.dto.*;
import lombok.extern.log4j.Log4j2;
import java.util.UUID;
import java.util.ArrayList;

@Service
@Log4j2
public class CourseSalesService {

    @Autowired
    private WebPayFeignClient webPayFeignClient;

    @Autowired
    private CourseSalesDbFeignClient salesDbFeignClient;

    @Autowired
    private CourseBsFeignClient productBsFeignClient;

    public WebPayTransactionResponseDTO initiateSaleTransaction(CourseSalesDTO saleRequest) {
        log.info("Iniciando transacción para la sesión: {}", saleRequest.getSessionId());

        final String apiKeyId = "597055555532";
        final String apiKeySecret = "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C";
        final String returnUrl = "http://localhost:8181/api/s_courses/transaction/confirm"; 

        String buyOrder = "edu-" + UUID.randomUUID().toString().substring(0, 8);
        
        WebPayTransactionRequestDTO webpayRequest = new WebPayTransactionRequestDTO(
            buyOrder,
            saleRequest.getSessionId(),
            saleRequest.getAmount(),
            returnUrl
        );
        
        return webPayFeignClient.generateTransaction(apiKeyId, apiKeySecret, webpayRequest);
    }

    public SalesDTO confirmSaleTransaction(String token) {
        log.info("Confirmando transacción con token: {}", token);

        final String apiKeyId = "597055555532";
        final String apiKeySecret = "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C";

        WebPayTransactionQueryResponseDTO transactionStatus = webPayFeignClient.queryTransaction(apiKeyId, apiKeySecret, token);
        log.info("Estado de la transacción consultada: {}", transactionStatus);

        if (transactionStatus != null && "AUTHORIZED".equals(transactionStatus.getStatus()) && transactionStatus.getResponseCode() == 0) {
            log.info("¡Pago autorizado! Procediendo a guardar la venta en la base de datos.");

            SalesDTO saleToSave = new SalesDTO();
            saleToSave.setAmount(Long.valueOf(transactionStatus.getAmount()));
            saleToSave.setCustomerId(1L); 

            ArrayList<CourseSalesDetailDTO> details = new ArrayList<>();
            CourseSalesDetailDTO detail = new CourseSalesDetailDTO();
            
            CourseDTO exampleCourse = new CourseDTO(
                1L,
                "Curso de Ejemplo Comprado",
                "Descripción del curso pagado.",
                101L, 
                Long.valueOf(transactionStatus.getAmount())
            );

            detail.setProduct(exampleCourse);
            detail.setQuantity(1L);
            details.add(detail);
            
            saleToSave.setSalesDetailDtoList(details);
            
            return this.insertSale(saleToSave);
        } else {
            log.error("El pago no fue autorizado o falló. Estado: {}", transactionStatus != null ? transactionStatus.getStatus() : "N/A");
            return null;
        }
    }

    public SalesDTO findSalesById(Long id) {
        SalesDTO salesDTO = salesDbFeignClient.findSalesById(id).getBody();
        
        if (salesDTO != null && salesDTO.getSalesDetailDtoList() != null) {
            for (CourseSalesDetailDTO salesDetailDTO : salesDTO.getSalesDetailDtoList()) {
                if (salesDetailDTO.getProduct() != null && salesDetailDTO.getProduct().getId() != null) {
                    Long idProducto = salesDetailDTO.getProduct().getId();
                    CourseDTO product = productBsFeignClient.findProductById(idProducto).getBody();
                    salesDetailDTO.setProduct(product);
                }
            }
        }
        return salesDTO;
    }

    public SalesDTO insertSale(SalesDTO saleDTO) {
        SalesDTO dto = salesDbFeignClient.insertSale(saleDTO).getBody();
        log.info("Venta guardada con éxito en la BD. ID: {}", dto.getId());
        return dto;
    }
}
