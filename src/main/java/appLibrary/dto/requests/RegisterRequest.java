package appLibrary.dto.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RegisterRequest {
    private String firstName;

    private String lastName;

    @Email
    private String email;

    @NotBlank
    private String password;
    private String repeatPassword;
}
