package kg.transparency.dt4ce.dto.comment;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kg.transparency.dt4ce.dto.initiative.ResponseInitiativeDTO;
import kg.transparency.dt4ce.dto.user.ResponseUserDTO;
import kg.transparency.dt4ce.model.Comment;
import kg.transparency.dt4ce.model.Initiative;
import kg.transparency.dt4ce.model.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

import static kg.transparency.dt4ce.dto.initiative.ResponseInitiativeDTO.toResponseInitiativeDTO;
import static kg.transparency.dt4ce.dto.user.ResponseUserDTO.toResponseUserDTO;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseCommentDTO {
    Long id;

    String text;

    LocalDateTime createdAt;

    ResponseUserDTO user;

    public static ResponseCommentDTO toResponseCommentDTO(Comment comment){
        return ResponseCommentDTO.builder()
                .id(comment.getId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .user(toResponseUserDTO(comment.getUser()))
                .build();
    }

    public static List<ResponseCommentDTO> toResponseCommentDTOs(List<Comment> comments){
        return comments.stream().map(ResponseCommentDTO::toResponseCommentDTO).toList();
    }
}
