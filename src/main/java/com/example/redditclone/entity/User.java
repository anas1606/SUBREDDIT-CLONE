package com.example.redditclone.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @NotBlank
    @JsonProperty("username")
    private String username;
    @NotBlank
    @JsonProperty("password")
    private String password;
    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;
    private Instant created;
    private boolean enabled;
}
