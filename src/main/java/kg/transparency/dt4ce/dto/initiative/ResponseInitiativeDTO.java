package kg.transparency.dt4ce.dto.initiative;

import jakarta.persistence.*;
import kg.transparency.dt4ce.dto.user.ResponseUserDTO;
import kg.transparency.dt4ce.model.Initiative;
import kg.transparency.dt4ce.model.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static kg.transparency.dt4ce.dto.user.ResponseUserDTO.toResponseUserDTO;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseInitiativeDTO {
    Long id;

    String title;

    String description;

    int viewsCount;

    List<String> imageUrls;

    ResponseUserDTO user;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    public static ResponseInitiativeDTO toResponseInitiativeDTO(Initiative initiative){
        return ResponseInitiativeDTO.builder()
                .id(initiative.getId())
                .title(initiative.getTitle())
                .description(initiative.getDescription())
                .viewsCount(initiative.getViewsCount())
                .imageUrls(initiative.getImageUrls())
                .user(toResponseUserDTO(initiative.getUser()))
                .createdAt(initiative.getCreatedAt())
                .updatedAt(initiative.getUpdatedAt())
                .build();
    }

    public static List<ResponseInitiativeDTO> toResponseInitiativeDTOs(List<Initiative> initiatives){
        return initiatives.stream().map(ResponseInitiativeDTO::toResponseInitiativeDTO).toList();
    }
}
