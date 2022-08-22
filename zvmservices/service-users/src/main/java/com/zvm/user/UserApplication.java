package com.zvm.user;

import com.zvm.user.entities.ApplicationUser;
import com.zvm.user.enums.UserRole;
import com.zvm.user.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zvm.clients")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserRepository repository, PasswordEncoder passwordEncoder){
        return args -> {
            ApplicationUser user1 = ApplicationUser.builder()
                    .login("TestUser1")
                    .password(passwordEncoder.encode(CharBuffer.wrap("1234".toCharArray())))
                    .userRole(UserRole.MANAGER)
                    .build();
            ApplicationUser user2 = ApplicationUser.builder()
                    .login("TestUser2")
                    .password(passwordEncoder.encode(CharBuffer.wrap("1234".toCharArray())))
                    .userRole(UserRole.DEVELOPER)
                    .build();
            ApplicationUser user3 = ApplicationUser.builder()
                    .login("TestUser3")
                    .password(passwordEncoder.encode(CharBuffer.wrap("1234".toCharArray())))
                    .userRole(UserRole.TESTER)
                    .build();
            repository.save(user1);
            repository.save(user2);
            repository.save(user3);
        };
    }
}
