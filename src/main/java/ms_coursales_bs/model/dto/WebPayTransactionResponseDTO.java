package ms_coursales_bs.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WebPayTransactionResponseDTO {

    private String token;
    private String url;
}

