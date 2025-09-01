package duy.com.learnspringboot.configuration;

import duy.com.learnspringboot.entity.Role;
import duy.com.learnspringboot.entity.User;
import duy.com.learnspringboot.repository.RoleRepository;
import duy.com.learnspringboot.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "com.mysql.jdbc.Driver"
    )
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
           if (userRepository.findByUsername("admin").isEmpty()) {
               var adminRole = roleRepository.findById("ADMIN")
                       .orElseGet(() -> roleRepository.save(Role.builder().name("ADMIN").build()));
               User user = User.builder()
                       .username("admin".toLowerCase())
                       .password(passwordEncoder.encode("admin"))
                       .roles(Set.of(adminRole))
                       .build();

               userRepository.save(user);
               log.warn("Admin has been created");
           }
        };
    }
}
