package jp.oiyokan.sitedemo.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SitedemoTestApplication {
    public static void main(String[] args) {
        System.err.println("Starting sitedemo test.");
        SpringApplication.run(SitedemoTestApplication.class, args);
    }
}
