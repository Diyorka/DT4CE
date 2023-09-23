package kg.transparency.dt4ce.dto.initiative;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.transparency.dt4ce.dto.user.ResponseUserDTO;
import kg.transparency.dt4ce.model.Initiative;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
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

    @JsonProperty("views_count")
    int viewsCount;

    @JsonProperty("for_votes_count")
    int forVotesCount;

    @JsonProperty("against_votes_count")
    int againstVotesCount;

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
