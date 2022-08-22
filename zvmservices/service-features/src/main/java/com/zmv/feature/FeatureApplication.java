package com.zmv.feature;

import com.zmv.feature.entities.Feature;
import com.zmv.feature.enums.FeatureState;
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

    @Bean
    CommandLineRunner runner(FeatureRepository repository) {
        return args -> {
            Feature feature1 = Feature.builder()
                    .createdByUserId(1000)
                    .name("TestFeature1")
                    .description("Test Feature assigned to no one")
                    .featureState(FeatureState.OPEN)
                    .build();
            Feature feature2 = Feature.builder()
                    .createdByUserId(1000)
                    .name("TestFeature2")
                    .description("Test Feature assigned to no one")
                    .featureState(FeatureState.OPEN)
                    .build();
            Feature feature3 = Feature.builder()
                    .createdByUserId(1000)
                    .devId(1001)
                    .name("TestFeature3")
                    .description("Test Feature assigned dev TestUser2(id 1001)")
                    .featureState(FeatureState.IN_PROGRESS)
                    .build();
            Feature feature4 = Feature.builder()
                    .createdByUserId(1000)
                    .devId(1001)
                    .name("TestFeature4")
                    .description("Test Feature assigned dev TestUser2(id 1001)")
                    .featureState(FeatureState.IN_PROGRESS)
                    .build();
            Feature feature5 = Feature.builder()
                    .createdByUserId(1000)
                    .devId(1001)
                    .name("TestFeature5")
                    .description("Test Feature assigned dev TestUser2(id 1001)")
                    .featureState(FeatureState.RESOLVED)
                    .build();
            repository.save(feature1);
            repository.save(feature2);
            repository.save(feature3);
            repository.save(feature4);
            repository.save(feature5);
        };
    }
}
