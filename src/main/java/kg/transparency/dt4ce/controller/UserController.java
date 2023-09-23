package kg.transparency.dt4ce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.transparency.dt4ce.dto.user.ActivateUserDTO;
import kg.transparency.dt4ce.model.User;
import kg.transparency.dt4ce.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(
        name = "Контроллер для работы с пользователями",
        description = "В этом контроллере есть возможности получения пользователя, изменения их данных"
)
public class UserController {
    UserService userService;

    @PostMapping(value = "/activate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Отправка заявки на верификацию аккаунта"
    )
    public ResponseEntity<String> activateAccount(@RequestParam String name,
                                                  @RequestParam String surname,
                                                  @RequestParam LocalDate date_of_birth,
                                                  @RequestPart MultipartFile passFront,
                                                  @RequestPart MultipartFile passBack,
                                                  @RequestPart MultipartFile selfie,
                                                  @AuthenticationPrincipal User user) {
        return userService.activateAccount(name, surname, date_of_birth, passFront, passBack, selfie, user);
    }

}
