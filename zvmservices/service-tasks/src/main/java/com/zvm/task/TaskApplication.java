package com.zvm.task;

import com.zvm.task.entities.Task;
import com.zvm.task.enums.TaskState;
import com.zvm.task.repositries.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zvm.clients")
public class TaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(TaskRepository repository) {
        return args -> {
            Task task1 = Task.builder()
                    .createdByUserId(1000)
                    .name("TestTask1")
                    .description("Test Task assigned to no one")
                    .taskState(TaskState.OPEN)
                    .build();
            Task task2 = Task.builder()
                    .createdByUserId(1000)
                    .name("TestTask2")
                    .description("Test Task assigned to no one")
                    .taskState(TaskState.OPEN)
                    .build();
            Task task3 = Task.builder()
                    .createdByUserId(1000)
                    .featureId(1001)
                    .name("TestTask3")
                    .description("Test Task assigned to no one")
                    .taskState(TaskState.OPEN)
                    .build();
            Task task4 = Task.builder()
                    .createdByUserId(1000)
                    .featureId(1001)
                    .name("TestTask4")
                    .description("Test Task assigned to no one")
                    .taskState(TaskState.OPEN)
                    .build();
            Task task5 = Task.builder()
                    .createdByUserId(1000)
                    .featureId(1002)
                    .devId(1001)
                    .name("TestTask5")
                    .description("Test Task assigned to TestUser2 (id 1001)")
                    .taskState(TaskState.IN_PROGRESS)
                    .build();
            Task task6 = Task.builder()
                    .createdByUserId(1000)
                    .featureId(1002)
                    .devId(1001)
                    .name("TestTask6")
                    .description("Test Task assigned to TestUser2 (id 1001)")
                    .taskState(TaskState.IN_PROGRESS)
                    .build();
            Task task7 = Task.builder()
                    .createdByUserId(1000)
                    .featureId(1003)
                    .devId(1001)
                    .name("TestTask7")
                    .description("Test Task assigned to TestUser2 (id 1001)")
                    .taskState(TaskState.RESOLVED)
                    .build();
            Task task8 = Task.builder()
                    .createdByUserId(1000)
                    .featureId(1003)
                    .devId(1001)
                    .testerId(1002)
                    .name("TestTask8")
                    .description("Test Task assigned to TestUser2 (id 1001) and TestUser2(tester id 1002)")
                    .taskState(TaskState.RESOLVED)
                    .build();
            Task task9 = Task.builder()
                    .createdByUserId(1000)
                    .featureId(1003)
                    .devId(1001)
                    .testerId(1002)
                    .name("TestTask9")
                    .description("Test Task assigned to TestUser2 (id 1001) and TestUser2(tester id 1002)")
                    .taskState(TaskState.IN_PROGRESS)
                    .build();
            Task task10 = Task.builder()
                    .createdByUserId(1000)
                    .featureId(1004)
                    .devId(1001)
                    .testerId(1002)
                    .name("TestTask10")
                    .description("Test Task assigned to TestUser2 (id 1001) and TestUser2(tester id 1002)")
                    .taskState(TaskState.COMPLETED)
                    .build();

            repository.save(task1);
            repository.save(task2);
            repository.save(task3);
            repository.save(task4);
            repository.save(task5);
            repository.save(task6);
            repository.save(task7);
            repository.save(task8);
            repository.save(task9);
            repository.save(task10);
        };
    }
}
