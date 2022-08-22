package com.zvm.user.services;

import com.zvm.clients.user.UserNotFoundException;
import com.zvm.user.dto.CredentialsDto;
import com.zvm.user.dto.UserCreationRequest;
import com.zvm.user.dto.UserDto;
import com.zvm.user.entities.ApplicationUser;
import com.zvm.user.enums.UserRole;
import com.zvm.user.exceptions.ApplicationException;
import com.zvm.user.mappers.UserMapper;
import com.zvm.user.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.CharBuffer;
import java.util.Base64;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Override
    public Boolean isPresent(Integer userId) {
        Boolean isPresent = userRepository.existsById(userId);
        log.info("Checking user existence user isPresent - {}", isPresent);
        return isPresent;
    }

    @Override
    public UserDto createUser(UserCreationRequest userCreationRequest) {
        if(userRepository.findByLogin(userCreationRequest.login()).isPresent()){
            log.error("User with login {} is already in database", userCreationRequest.login());
            throw new ApplicationException("User is already in database", HttpStatus.BAD_REQUEST);
        }
        ApplicationUser user = ApplicationUser.builder()
                .login(userCreationRequest.login())
                .password(passwordEncoder.encode(CharBuffer.wrap(userCreationRequest.password())))
                .userRole(UserRole.valueOf(userCreationRequest.role()))
                .build();
        log.info("New user is created {}", user.getLogin());
        userRepository.saveAndFlush(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto getUser(Integer userId) {
        ApplicationUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No such user!", HttpStatus.NOT_FOUND));
        log.info("User {} is retrieved from database", user.getLogin());
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto signIn(CredentialsDto credentialsDto) {
        ApplicationUser user = userRepository.findByLogin(credentialsDto.login())
                .orElseThrow(() -> new UserNotFoundException("User not found!", HttpStatus.NOT_FOUND));

        log.info("User {} is retrieved by login from database", user.getLogin());

        if(!passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())){
            log.error("User {} password doesn't match", user.getLogin());
            throw new ApplicationException("Invalid password", HttpStatus.BAD_REQUEST);
        }

        return userMapper.toUserDto(user, createToken(user));
    }

    @Override
    public UserDto validateToken(String token) {
        String login = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        ApplicationUser user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User not found!", HttpStatus.NOT_FOUND));

        return userMapper.toUserDto(user, createToken(user));
    }

    private String createToken(ApplicationUser user) {
        Claims claims = Jwts.claims().setSubject(user.getLogin());
        claims.put("role", user.getUserRole());

        Date now = new Date();
        Date validity = new Date(now.getTime() + 1800000); // 30 min

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }

}
