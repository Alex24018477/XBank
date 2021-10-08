package com.alex.xbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching//включает анотацию  @Cacheable в классе RateRetriever
public class XbankApplication {

    public static void main(String[] args) {
        SpringApplication.run(XbankApplication.class, args);
    }

}
