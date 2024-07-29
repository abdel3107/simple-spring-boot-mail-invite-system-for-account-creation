package com.example.mailing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@PropertySource("classpath:application.properties")
public class MailingApplication {

//    @Value("${jwt.secret}")
//    public static String secretKey;

    public static void main(String[] args) {
        SpringApplication.run(MailingApplication.class, args);

    }




}
