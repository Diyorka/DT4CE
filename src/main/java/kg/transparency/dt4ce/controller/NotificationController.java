package kg.transparency.dt4ce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.transparency.dt4ce.dto.notification.ResponseNotificationDTO;
import kg.transparency.dt4ce.model.User;
import kg.transparency.dt4ce.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(
        name = "Контроллер для работы с уведомлениями",
        description = "В этом контроллере есть возможности получения, удаления уведомлений"
)
public class NotificationController {
    NotificationService notificationService;

    @GetMapping("/all")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение всех уведомлений авторизованного пользователя"
    )
    public List<ResponseNotificationDTO> getAllNotificationsByUser(@AuthenticationPrincipal User user){
        return notificationService.getAllNotificationsOfUser(user);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение уведомления по айди"
    )
    public ResponseNotificationDTO getNotificationById(@PathVariable Long id,
                                                       @AuthenticationPrincipal User user){
        return notificationService.getNotificationById(id, user);
    }

    @PutMapping("/all-read")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Отметить все уведомления как прочитанное"
    )
    public List<ResponseNotificationDTO> markAllNotificationsAsReadByUser(@AuthenticationPrincipal User user){
        return notificationService.markAllUsersNotificationsAsRead(user);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Отметить уведомление как прочитанное"
    )
    public ResponseNotificationDTO markNotificationAsReadById(@PathVariable Long id,
                                                              @AuthenticationPrincipal User user){
        return notificationService.markNotificationAsReadById(id, user);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удалить уведомление по айди"
    )
    public ResponseEntity<String> deleteNotificationById(@PathVariable Long id,
                                                         @AuthenticationPrincipal User user){
        return notificationService.deleteNotificationById(id, user);
    }

    @DeleteMapping("/all")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удалить все уведомления авторизованного пользователя"
    )
    public ResponseEntity<String> deleteAllNotificationsOfUser(@AuthenticationPrincipal User user){
        return notificationService.deleteAllNotificationsOfUser(user);
    }
}
