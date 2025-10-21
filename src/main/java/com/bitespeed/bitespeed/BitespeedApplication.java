package com.bitespeed.bitespeed;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BitespeedApplication implements CommandLineRunner {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    public static void main(String[] args) {
        SpringApplication.run(BitespeedApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("JDBC URL: " + dbUrl);
    }
}
