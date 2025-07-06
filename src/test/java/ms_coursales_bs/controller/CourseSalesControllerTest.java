package ms_coursales_bs.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import ms_coursales_bs.model.dto.CourseSalesDTO;
import ms_coursales_bs.model.dto.SalesDTO;
import ms_coursales_bs.model.dto.WebPayTransactionResponseDTO;
import ms_coursales_bs.service.CourseSalesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

@WebMvcTest(CourseSalesController.class)
class CourseSalesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseSalesService courseSalesService;

    @Autowired
    private ObjectMapper objectMapper;

    private CourseSalesDTO saleRequest;
    private WebPayTransactionResponseDTO webPayResponse;

    @BeforeEach
    void setUp() {
        saleRequest = new CourseSalesDTO("sess_456", null, 150000, new ArrayList<>());
        webPayResponse = new WebPayTransactionResponseDTO("token456", "http://webpay.cl/pay_url");
    }

    @Test
    void testInitiatePayment_ShouldReturnOk() throws Exception {
        when(courseSalesService.initiateSaleTransaction(any(CourseSalesDTO.class))).thenReturn(webPayResponse);

        mockMvc.perform(post("/api/s_courses/initiate-payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token456"))
                .andExpect(jsonPath("$.url").value("http://webpay.cl/pay_url"));
    }

    @Test
    void testConfirmPayment_WhenSuccess_ShouldRedirectToSuccessUrl() throws Exception {
        String token = "good_token";
        SalesDTO confirmedSale = new SalesDTO();
        confirmedSale.setId(1L); 
        when(courseSalesService.confirmSaleTransaction(token)).thenReturn(confirmedSale);

        mockMvc.perform(get("/api/s_courses/transaction/confirm").param("token_ws", token))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/fe-ecommerce/resultado.html?status=success&saleId=1"));
    }

    @Test
    void testConfirmPayment_WhenFailed_ShouldRedirectToFailedUrl() throws Exception {
        String token = "bad_token";
        when(courseSalesService.confirmSaleTransaction(token)).thenReturn(null);

        mockMvc.perform(get("/api/s_courses/transaction/confirm").param("token_ws", token))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/fe-ecommerce/resultado.html?status=failed"));
    }

    @Test
    void testFindSalesById_WhenFound_ShouldReturnOk() throws Exception {
        Long saleId = 1L;
        SalesDTO salesDTO = new SalesDTO(saleId, 150000L, 123L, new ArrayList<>());
        when(courseSalesService.findSalesById(saleId)).thenReturn(salesDTO);

        mockMvc.perform(get("/api/s_courses/{id}", saleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.venta_id").value(saleId))
                .andExpect(jsonPath("$.venta_monto").value(150000L));
    }
    
    @Test
    void testFindSalesById_WhenNotFound_ShouldReturnNotFound() throws Exception {
        Long saleId = 99L;
        when(courseSalesService.findSalesById(saleId)).thenReturn(null);

        mockMvc.perform(get("/api/s_courses/{id}", saleId))
                .andExpect(status().isNotFound());
    }
}