package ms_coursales_bs.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WebPayTransactionQueryResponseCardDetailDTO {

    @JsonProperty("card_number")
    private String cardNumber;

}
