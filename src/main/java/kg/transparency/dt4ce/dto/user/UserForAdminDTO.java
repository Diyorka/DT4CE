package kg.transparency.dt4ce.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.transparency.dt4ce.model.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserForAdminDTO {
    Long id;

    String name;

    String surname;

    String email;

    String phoneNumber;

    @JsonProperty("pass_front_url")
    String passFrontUrl;

    @JsonProperty("pass_back_url")
    String passBackUrl;

    @JsonProperty("selfie_url")
    String selfieUrl;

    @JsonProperty("date_of_birth")
    LocalDate dateOfBirth;

    String status;

    public static UserForAdminDTO toUserForAdminDTO(User user){
        return UserForAdminDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .passFrontUrl(user.getPassFrontUrl())
                .passBackUrl(user.getPassBackUrl())
                .selfieUrl(user.getSelfieUrl())
                .dateOfBirth(user.getDateOfBirth())
                .status(user.getStatus().name())
                .build();
    }

    public static List<UserForAdminDTO> toUserForAdminDTOs(List<User> users){
        return users.stream().map(UserForAdminDTO::toUserForAdminDTO).toList();
    }
}
