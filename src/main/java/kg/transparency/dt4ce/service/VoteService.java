package kg.transparency.dt4ce.service;

import kg.transparency.dt4ce.model.User;
import org.springframework.http.ResponseEntity;

public interface VoteService {
    ResponseEntity<String> addVote(Long initiativeId, String vote, User user);
}
