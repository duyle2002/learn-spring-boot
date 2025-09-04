package duy.com.learnspringboot.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Table(name = "invalidated_tokens")
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class InvalidatedToken {
    @Id
    UUID id;

    Instant expiryDate;
}
