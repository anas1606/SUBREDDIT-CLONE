package com.example.redditclone.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @NotBlank(message = "Username is required")
    @JsonProperty("username")
    private String username;
    @NotBlank(message = "Password is required")
    @JsonProperty("password")
    private String password;
    @Email
    @NotEmpty(message = "Email is required")
    @JsonProperty("email")
    private String email;
    private Instant created;
    private boolean enabled;
}
