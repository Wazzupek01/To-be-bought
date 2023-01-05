package com.pedrycz.tobebought.model.user;

import com.pedrycz.tobebought.validation.Password;
import com.pedrycz.tobebought.validation.Username;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    private Long Id;

    @Username
    private String username;

    @Password
    private String password;
}
