package kg.transparency.dt4ce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.transparency.dt4ce.dto.initiative.RequestInitiativeDTO;
import kg.transparency.dt4ce.dto.initiative.ResponseInitiativeDTO;
import kg.transparency.dt4ce.model.User;
import kg.transparency.dt4ce.service.InitiativeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/initiatives")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(
        name = "Контроллер для работы с инициативами",
        description = "В этом контроллере есть возможности добавления, обновления, получения и удаления инициатив"
)
public class InitiativeController {
    InitiativeService initiativeService;

    @GetMapping("/{initiative_id}/generate-qr-code")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Сгенерировать QR Code по айди инициативы"
    )
    public ResponseEntity<byte[]> generateQR(@PathVariable Long initiative_id){
        return initiativeService.generateQR(initiative_id);
    }

    @GetMapping("/my-initiatives")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение всех инициатив пользователя"
    )
    public Page<ResponseInitiativeDTO> getMyInitiatives(@PageableDefault Pageable pageable,
                                                        @AuthenticationPrincipal User user) {
        return initiativeService.getMyInitiatives(pageable, user);
    }

    @GetMapping("/all")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение всех инициатив"
    )
    public Page<ResponseInitiativeDTO> getAllInitiatives(@PageableDefault Pageable pageable) {
        return initiativeService.getAllInitiatives(pageable);
    }

    @GetMapping("/{initiative_id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение инициативы по айди"
    )
    public ResponseInitiativeDTO getInitiativeById(@PathVariable Long initiative_id) {
        return initiativeService.getInitiativeById(initiative_id);
    }

    @PostMapping("/propose-initiative")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Предложить инициативу"
    )
    public ResponseEntity<Long> proposeInitiative(@RequestBody RequestInitiativeDTO initiativeDTO,
                                                  @AuthenticationPrincipal User user) {
        return initiativeService.addInitiative(initiativeDTO, user);
    }

    @PostMapping(value = "/{initiative_id}/upload-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Добавить фотографии к инициативе"
    )
    public ResponseEntity<String> uploadImagesToInitiative(@PathVariable Long initiative_id,
                                                           @RequestPart MultipartFile[] images,
                                                           @AuthenticationPrincipal User user) {
        return initiativeService.uploadImagesToInitiative(initiative_id, images, user);
    }

    @PutMapping("/{initiative_id}/update-initiative")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Обновить инициативу"
    )
    public ResponseEntity<String> updateInitiative(@PathVariable Long initiative_id,
                                                   @RequestBody RequestInitiativeDTO initiativeDTO,
                                                   @AuthenticationPrincipal User user) {
        return initiativeService.updateInitiative(initiative_id, initiativeDTO, user);
    }

    @DeleteMapping("{initiative_id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удалить инициативу"
    )
    public ResponseEntity<String> deleteInitiative(@PathVariable Long initiative_id,
                                                   @AuthenticationPrincipal User user){
        return initiativeService.deleteInitiative(initiative_id, user);
    }

}
