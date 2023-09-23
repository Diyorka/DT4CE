package kg.transparency.dt4ce.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivateUserDTO {
    @NotEmpty(message = "Имя не должно быть пустым")
    String name;

    @NotEmpty(message = "Фамилия не должна быть пустой")
    String surname;

    @JsonProperty("date_of_birth")
    @NotNull(message = "Дата рождения не должна быть пустой")
    LocalDate dateOfBirth;
}
