package za.ac.cput;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BuzzCarSalesApplication {
    public static void main( String[] args )
    {
        SpringApplication.run(BuzzCarSalesApplication.class, args);
    }
}
