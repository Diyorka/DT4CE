package kg.transparency.dt4ce.dto.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kg.transparency.dt4ce.enums.Role;
import kg.transparency.dt4ce.enums.Status;
import kg.transparency.dt4ce.model.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseUserDTO {
    Long id;

    String name;

    String surname;

    String email;

    String phoneNumber;

    String imageUrl;

    String role;

    String status;

    LocalDateTime createdAt;

    public static ResponseUserDTO toResponseUserDTO(User user){
        return ResponseUserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .imageUrl(user.getImageUrl())
                .role(user.getRole().name())
                .status(user.getStatus().name())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static List<ResponseUserDTO> toResponseUserDTOs(List<User> users){
        return users.stream().map(ResponseUserDTO::toResponseUserDTO).toList();
    }
}
