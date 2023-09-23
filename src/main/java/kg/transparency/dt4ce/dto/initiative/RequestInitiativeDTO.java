package kg.transparency.dt4ce.dto.initiative;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestInitiativeDTO {
    String title;

    String description;
}
