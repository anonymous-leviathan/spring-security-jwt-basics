package com.mgunawardhana.microservices.spring.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotNull(message = "First Name is required!")
    @Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 characters!")
    private String firstName;

    @NotNull(message = "Last Name is required!")
    @Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 characters!")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Password is required!")
    private String password;
}
