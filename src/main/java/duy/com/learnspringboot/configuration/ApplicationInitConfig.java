package duy.com.learnspringboot.configuration;

import duy.com.learnspringboot.entity.User;
import duy.com.learnspringboot.enums.Role;
import duy.com.learnspringboot.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
           if (userRepository.findByUsername("admin").isEmpty()) {
               var roles = new HashSet<String>();
               roles.add(Role.ADMIN.name());
               User user = User.builder()
                       .username("admin".toLowerCase())
                       .password(passwordEncoder.encode("admin"))
                       .roles(roles)
                       .build();

               userRepository.save(user);
               log.warn("Admin has been created");
           }
        };
    }
}
