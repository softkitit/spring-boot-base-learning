package com.softkit.dto;

import com.softkit.annotation.ValidPassword;
import com.softkit.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDTO {

    private String username;
    @Email(message = "Email not valid")
    private String email;
    @ValidPassword
    private String password;
    private List<Role> roles;

}
