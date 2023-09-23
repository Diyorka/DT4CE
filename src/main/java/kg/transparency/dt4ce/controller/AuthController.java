package kg.transparency.dt4ce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.transparency.dt4ce.dto.auth.AuthRequestDTO;
import kg.transparency.dt4ce.dto.auth.AuthResponseDTO;
import kg.transparency.dt4ce.dto.user.RequestUserDTO;
import kg.transparency.dt4ce.exception.UserAlreadyExistException;
import kg.transparency.dt4ce.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(
        name = "Контроллер для авторизации/регистрации",
        description = "В этом контроллере есть возможности авторизации, регистрации и подтверждения аккаунта"
)
public class AuthController {
    AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(
            summary = "Регистрация нового аккаунта"
    )
    public AuthResponseDTO register(@Valid @RequestBody RequestUserDTO userDTO) throws UserAlreadyExistException {
        return authenticationService.register(userDTO);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Авторизация активированного аккаунта"
    )
    public AuthResponseDTO authenticate(@RequestBody AuthRequestDTO authRequestDTO) {
        return authenticationService.authenticate(authRequestDTO);
    }
}
