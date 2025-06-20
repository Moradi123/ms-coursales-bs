package ms_coursales_bs.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WebPayTransactionQueryResponseDTO {
    private String vci;
    private int amount;
    private String status;
    @JsonProperty("buy_order")
    private String buyOrder;
    @JsonProperty("session_id")
    private String sessionId;
    @JsonProperty("accounting_date")
    private String accountingDate;
    @JsonProperty("transaction_date")
    private String transactionDate;
    @JsonProperty("authorization_code")
    private String authorizationCode;
    @JsonProperty("payment_type_code")
    private String paymentTypeCode;
    @JsonProperty("response_code")
    private int responseCode;
    @JsonProperty("installments_number")
    private int installmentsNumber;
    @JsonProperty("card_detail")
    private WebPayTransactionQueryResponseCardDetailDTO cardDetailDTO;
}
