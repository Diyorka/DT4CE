package kg.transparency.dt4ce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.transparency.dt4ce.dto.user.UserForAdminDTO;
import kg.transparency.dt4ce.service.AdminService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(
        name = "Админ панель",
        description = "В этом контроллере есть возможности получения, подтверждения и отклонения заявок"
)
public class AdminController {
    AdminService adminService;

    @GetMapping("/pending-users")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение всех пользователей, ожидающих верификации"
    )
    public Page<UserForAdminDTO> getAllPendingUsers(@PageableDefault Pageable pageable){
        return adminService.getAllPendingUsers(pageable);
    }

    @PostMapping("/approve-user/{user_id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Подтверждение верификации пользователя"
    )
    public ResponseEntity<String> approveUser(@PathVariable Long user_id){
        return adminService.approveUser(user_id);
    }

    @PostMapping("/reject-user/{user_id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Отклонение верификации пользователя"
    )
    public ResponseEntity<String> approveUser(@PathVariable Long user_id,
                                              @RequestParam String reason){
        return adminService.rejectUser(user_id, reason);
    }
}
