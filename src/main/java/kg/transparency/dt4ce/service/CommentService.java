package kg.transparency.dt4ce.service;

import kg.transparency.dt4ce.dto.comment.RequestCommentDTO;
import kg.transparency.dt4ce.dto.comment.ResponseCommentDTO;
import kg.transparency.dt4ce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    Page<ResponseCommentDTO> getAllCommentsOfInitiative(Long initiative_id, Pageable pageable);

    ResponseEntity<String> addComment(Long initiative_id, RequestCommentDTO commentDTO, User user);

    ResponseEntity<String> updateComment(Long comment_id, RequestCommentDTO commentDTO, User user);

    ResponseEntity<String> deleteComment(Long comment_id, User user);
}
