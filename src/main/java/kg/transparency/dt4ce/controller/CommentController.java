package kg.transparency.dt4ce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.transparency.dt4ce.dto.comment.RequestCommentDTO;
import kg.transparency.dt4ce.dto.comment.ResponseCommentDTO;
import kg.transparency.dt4ce.model.User;
import kg.transparency.dt4ce.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(
        name = "Контроллер для работы с комментариями к инициативам",
        description = "В этом контроллере есть возможности добавления, обновления, получения и удаления комментариев"
)
public class CommentController {
    CommentService commentService;

    @GetMapping("/{initiative_id}/all")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение всех комментариев к инициативе"
    )
    public Page<ResponseCommentDTO> getAllCommentsOfInitiative(@PathVariable Long initiative_id,
                                                               @PageableDefault Pageable pageable){
        return commentService.getAllCommentsOfInitiative(initiative_id, pageable);
    }

    @PostMapping("/{initiative_id}/add")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Добавление комментария к инициативе"
    )
    public ResponseEntity<String> addComment(@PathVariable Long initiative_id,
                                             @RequestBody RequestCommentDTO commentDTO,
                                             @AuthenticationPrincipal User user){
        return commentService.addComment(initiative_id, commentDTO, user);
    }

    @PutMapping("/{comment_id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Обновление комментария"
    )
    public ResponseEntity<String> updateComment(@PathVariable Long comment_id,
                                                @RequestBody RequestCommentDTO commentDTO,
                                                @AuthenticationPrincipal User user){
        return commentService.updateComment(comment_id, commentDTO, user);
    }

    @DeleteMapping("/{comment_id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удаление комментария"
    )
    public ResponseEntity<String> deleteComment(@PathVariable Long comment_id,
                                                @AuthenticationPrincipal User user){
        return commentService.deleteComment(comment_id, user);
    }
}
