package xco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.TimeZone;

@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
public class Application {
	static {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}