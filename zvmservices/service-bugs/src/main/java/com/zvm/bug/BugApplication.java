package com.zvm.bug;

import com.zvm.bug.entities.Bug;
import com.zvm.bug.enums.BugState;
import com.zvm.bug.repositories.BugRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zvm.clients")
public class BugApplication {
    public static void main(String[] args) {
        SpringApplication.run(BugApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(BugRepository repository) {
        return args -> {
            Bug bug1 = Bug.builder()
                    .createdByUserId(1002)
                    .taskId(1008)
                    .name("TestBug1")
                    .description("TestBug1 for TestTask9")
                    .bugState(BugState.NOT_RESOLVED)
                    .build();
            Bug bug2 = Bug.builder()
                    .createdByUserId(1002)
                    .taskId(1008)
                    .name("TestBug2")
                    .description("TestBug2 for TestTask9")
                    .bugState(BugState.RESOLVED)
                    .build();
            Bug bug3 = Bug.builder()
                    .createdByUserId(1002)
                    .taskId(1009)
                    .name("TestBug3")
                    .description("TestBug1 for TestTask10")
                    .bugState(BugState.RESOLVED)
                    .build();
            Bug bug4 = Bug.builder()
                    .createdByUserId(1002)
                    .taskId(1009)
                    .name("TestBug4")
                    .description("TestBug2 for TestTask10")
                    .bugState(BugState.RESOLVED)
                    .build();
            repository.save(bug1);
            repository.save(bug2);
            repository.save(bug3);
            repository.save(bug4);
        };
    }
}
