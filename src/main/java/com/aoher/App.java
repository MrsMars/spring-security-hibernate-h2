package com.aoher;

import com.aoher.model.Role;
import com.aoher.model.User;
import com.aoher.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;

import static com.aoher.util.Constants.ADMIN_ROLE;
import static com.aoher.util.Constants.USER_ROLE;

@SpringBootApplication
@ImportResource("classpath:spring-security-config.xml")
public class App {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        User user = new User("Memory",
                "Not Found",
                "info@memorynotfound.com",
                passwordEncoder.encode("password"),
                Arrays.asList(
                        new Role(USER_ROLE),
                        new Role(ADMIN_ROLE)));

        if (userRepository.findByEmail(user.getEmail()) == null) {
            userRepository.save(user);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
