package ms_coursales_bs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsCoursalesBsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCoursalesBsApplication.class, args);
	}

}
