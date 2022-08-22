package com.zmv.feature;

import com.zmv.feature.entities.Feature;
import com.zmv.feature.repsitories.FeatureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zvm.clients")
public class FeatureApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeatureApplication.class, args);
    }

//    @Bean
//    CommandLineRunner runner(FeatureRepository repository) {
//        return args -> {
//
//        };
//    }
}
