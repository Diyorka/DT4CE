package kg.transparency.dt4ce.service;

import kg.transparency.dt4ce.dto.notification.ResponseNotificationDTO;
import kg.transparency.dt4ce.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NotificationService {
    List<ResponseNotificationDTO> getAllNotificationsOfUser(User user);

    ResponseNotificationDTO getNotificationById(Long id, User user);

    List<ResponseNotificationDTO> markAllUsersNotificationsAsRead(User user);

    ResponseNotificationDTO markNotificationAsReadById(Long id, User user);

    ResponseEntity<String> deleteNotificationById(Long id, User user);

    ResponseEntity<String> deleteAllNotificationsOfUser(User user);
}
