package kg.transparency.dt4ce.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestCommentDTO {
    @NotEmpty(message = "Текст комментария не может быть пустым")
    String text;
}
