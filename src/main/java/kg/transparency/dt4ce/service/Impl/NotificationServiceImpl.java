package kg.transparency.dt4ce.service.Impl;

import kg.transparency.dt4ce.dto.notification.ResponseNotificationDTO;
import kg.transparency.dt4ce.exception.NotFoundException;
import kg.transparency.dt4ce.model.Notification;
import kg.transparency.dt4ce.model.User;
import kg.transparency.dt4ce.repository.NotificationRepository;
import kg.transparency.dt4ce.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static kg.transparency.dt4ce.dto.notification.ResponseNotificationDTO.toResponseNotificationDTO;
import static kg.transparency.dt4ce.dto.notification.ResponseNotificationDTO.toResponseNotificationDTOs;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    NotificationRepository notificationRepository;

    @Override
    public List<ResponseNotificationDTO> getAllNotificationsOfUser(User user) {
        return toResponseNotificationDTOs(notificationRepository.findAllByUser(user));
    }

    @Override
    public ResponseNotificationDTO getNotificationById(Long id, User user) {
        return toResponseNotificationDTO(notificationRepository.findById(id)
                .filter(n -> n.getUser().equals(user))
                .orElseThrow(() -> new NotFoundException("Уведомление с таким айди не найдено"))
        );
    }

    @Override
    public List<ResponseNotificationDTO> markAllUsersNotificationsAsRead(User user) {
        List<Notification> notifications = notificationRepository.findAllByUser(user);

        for(Notification notification:notifications){
            notification.setRead(true);
            notificationRepository.save(notification);
        }

        return toResponseNotificationDTOs(notifications);
    }

    @Override
    public ResponseNotificationDTO markNotificationAsReadById(Long id, User user) {
        Notification notification = notificationRepository.findById(id)
                        .filter(n -> n.getUser().equals(user))
                                .orElseThrow(() -> new NotFoundException("Уведомление с таким айди не найдено"));


        notification.setRead(true);
        notificationRepository.save(notification);

        return toResponseNotificationDTO(notification);
    }

    @Override
    public ResponseEntity<String> deleteNotificationById(Long id, User user) {
        Notification notification = notificationRepository.findById(id)
                .filter(n -> n.getUser().equals(user))
                .orElseThrow(() -> new NotFoundException("Уведомление с таким айди не найдено"));

        notificationRepository.delete(notification);

        return ResponseEntity.ok("Уведомление удалено");
    }

    @Override
    public ResponseEntity<String> deleteAllNotificationsOfUser(User user) {
        List<Notification> notifications = notificationRepository.findAllByUser(user);
        notificationRepository.deleteAll(notifications);

        return ResponseEntity.ok("Все уведомления удалены");
    }
}
