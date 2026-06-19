package ge.mziuri.emarket.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class UserRegisterDto {
    @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$", message = "Username must be 8-20 characters and no special characters")
    private String username;

    @Email(message = "Email must be valid")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password must be minimum of 8 character, have lower and upper case letters, numbers and special characters")
    private String password;

    @NotNull(message = "Birthday is required")
    private LocalDate birthday;
}
