package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (scanBasePackages = {"se.ju23.typespeeder"})
public class TypeSpeederApplication{

    public static void main(String[] args) {
        SpringApplication.run(TypeSpeederApplication.class, args);
    }

}
