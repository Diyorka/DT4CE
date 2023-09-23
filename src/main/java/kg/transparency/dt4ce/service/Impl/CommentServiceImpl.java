package kg.transparency.dt4ce.service.Impl;

import kg.transparency.dt4ce.dto.comment.RequestCommentDTO;
import kg.transparency.dt4ce.dto.comment.ResponseCommentDTO;
import kg.transparency.dt4ce.enums.Status;
import kg.transparency.dt4ce.exception.NotFoundException;
import kg.transparency.dt4ce.model.Comment;
import kg.transparency.dt4ce.model.Initiative;
import kg.transparency.dt4ce.model.User;
import kg.transparency.dt4ce.repository.CommentRepository;
import kg.transparency.dt4ce.repository.InitiativeRepository;
import kg.transparency.dt4ce.service.CommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

import static kg.transparency.dt4ce.dto.comment.ResponseCommentDTO.toResponseCommentDTOs;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;
    InitiativeRepository initiativeRepository;

    @Override
    public Page<ResponseCommentDTO> getAllCommentsOfInitiative(Long initiative_id, Pageable pageable) {
        Page<Comment> comments = commentRepository.findAllByInitiativeId(initiative_id, pageable);
        List<ResponseCommentDTO> commentDTOS = toResponseCommentDTOs(comments.toList());
        return new PageImpl<>(commentDTOS, pageable, comments.getTotalElements());
    }

    @Override
    public ResponseEntity<String> addComment(Long initiative_id, RequestCommentDTO commentDTO, User user) {
        Initiative initiative = initiativeRepository.findById(initiative_id)
                .filter(i -> i.getStatus() == Status.ACTIVATED)
                .orElseThrow(() -> new NotFoundException("Инициатива с таким айди не найдена"));

        Comment comment = Comment.builder()
                .text(commentDTO.getText())
                .initiative(initiative)
                .user(user)
                .build();

        commentRepository.save(comment);

        return ResponseEntity.ok("Комментарий успешно добавлен");
    }

    @Override
    public ResponseEntity<String> updateComment(Long comment_id, RequestCommentDTO commentDTO, User user) {
        Comment comment = commentRepository.findById(comment_id)
                .filter(c -> c.getUser().equals(user))
                .orElseThrow(() -> new NotFoundException("Комментарий с таким айди не найден"));

        comment.setText(commentDTO.getText());
        commentRepository.save(comment);

        return ResponseEntity.ok("Комментарий успешно обновлен");
    }

    @Override
    public ResponseEntity<String> deleteComment(Long comment_id, User user) {
        Comment comment = commentRepository.findById(comment_id)
                .filter(c -> c.getUser().equals(user))
                .orElseThrow(() -> new NotFoundException("Комментарий с таким айди не найден"));

        commentRepository.delete(comment);

        return ResponseEntity.ok("Комментарий успешно удален");
    }
}
