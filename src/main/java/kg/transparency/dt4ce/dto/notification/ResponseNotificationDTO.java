package kg.transparency.dt4ce.dto.notification;

import kg.transparency.dt4ce.model.Notification;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseNotificationDTO {
    Long id;

    String header;

    String message;

    boolean read;

    public static ResponseNotificationDTO toResponseNotificationDTO(Notification notification){
        return ResponseNotificationDTO.builder()
                .id(notification.getId())
                .header(notification.getHeader())
                .message(notification.getMessage())
                .read(notification.isRead())
                .build();
    }

    public static List<ResponseNotificationDTO> toResponseNotificationDTOs(List<Notification> notifications){
        return notifications.stream().map(ResponseNotificationDTO::toResponseNotificationDTO).toList();
    }
}
