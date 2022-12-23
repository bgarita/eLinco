package bga.com.fe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class FeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeApplication.class, args);
	}

}
