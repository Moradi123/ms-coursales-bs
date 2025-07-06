package ms_coursales_bs.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import ms_coursales_bs.controller.CourseSalesController;
import ms_coursales_bs.model.dto.CourseSalesDTO;
import ms_coursales_bs.model.dto.SalesDTO;
import ms_coursales_bs.model.dto.WebPayTransactionResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

@WebMvcTest(CourseSalesController.class)
class CourseSalesServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseSalesService courseSalesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testInitiatePayment() throws Exception {
        CourseSalesDTO saleRequest = new CourseSalesDTO("sess_456", null, 150000, new ArrayList<>());
        WebPayTransactionResponseDTO webPayResponse = new WebPayTransactionResponseDTO("token456", "http://webpay.cl/pay_url");
        when(courseSalesService.initiateSaleTransaction(any(CourseSalesDTO.class))).thenReturn(webPayResponse);

        mockMvc.perform(post("/api/s_courses/initiate-payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token456"));
    }

    @Test
    void testConfirmPayment_Success() throws Exception {
        String token = "good_token";
        SalesDTO confirmedSale = new SalesDTO(1L, 0L, 0L, null);
        when(courseSalesService.confirmSaleTransaction(token)).thenReturn(confirmedSale);

        mockMvc.perform(get("/api/s_courses/transaction/confirm").param("token_ws", token))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/fe-ecommerce/resultado.html?status=success&saleId=1"));
    }

    @Test
    void testConfirmPayment_Failed() throws Exception {
        String token = "bad_token";
        when(courseSalesService.confirmSaleTransaction(token)).thenReturn(null);

        mockMvc.perform(get("/api/s_courses/transaction/confirm").param("token_ws", token))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/fe-ecommerce/resultado.html?status=failed"));
    }

    @Test
    void testFindSalesById_Found() throws Exception {
        Long saleId = 1L;
        SalesDTO salesDTO = new SalesDTO(saleId, 150000L, 123L, new ArrayList<>());
        when(courseSalesService.findSalesById(saleId)).thenReturn(salesDTO);

        mockMvc.perform(get("/api/s_courses/{id}", saleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.venta_id").value(saleId));
    }
    
    @Test
    void testFindSalesById_NotFound() throws Exception {
        Long saleId = 99L;
        when(courseSalesService.findSalesById(saleId)).thenReturn(null);

        mockMvc.perform(get("/api/s_courses/{id}", saleId))
                .andExpect(status().isNotFound());
    }
}