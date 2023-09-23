package kg.transparency.dt4ce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.transparency.dt4ce.model.User;
import kg.transparency.dt4ce.service.VoteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(
        name = "Контроллер для работы с голосами",
        description = "В этом контроллере есть возможность проголосовать за или против инициативы"
)
public class VoteController {
    VoteService voteService;

    @PostMapping("/{initiative_id}/vote")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Проголосовать за или против инициативы либо удалить голос (в теле отправлять 'За' или 'Против')"
    )
    public ResponseEntity<String> addVote(@PathVariable Long initiative_id,
                                          @RequestParam String vote,
                                          @AuthenticationPrincipal User user){
        return voteService.addVote(initiative_id, vote, user);
    }
}
