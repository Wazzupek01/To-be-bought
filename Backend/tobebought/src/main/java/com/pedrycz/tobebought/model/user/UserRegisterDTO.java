package com.pedrycz.tobebought.model.user;

import com.pedrycz.tobebought.validation.Password;
import com.pedrycz.tobebought.validation.UniqueEmail;
import com.pedrycz.tobebought.validation.Username;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    @Username
    private String username;

    @Password
    private String password;

    @NotBlank(message = "Email can't be blank")
    @Email
    @UniqueEmail
    private String email;
}
