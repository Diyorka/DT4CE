package kg.transparency.dt4ce.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestUserDTO {
    @Email(message = "Почта введена некорректно")
    String email;

    @Size(min = 4, max = 50, message = "Пароль должен содержать от 4 до 50 символов")
    String password;

    @JsonProperty("confirm_password")
    String confirmPassword;

    @JsonProperty("phone_number")
    @NotEmpty(message = "Номер телефона не может быть пустым")
    String phoneNumber;
}
