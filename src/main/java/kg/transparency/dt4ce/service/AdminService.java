package kg.transparency.dt4ce.service;

import kg.transparency.dt4ce.dto.initiative.ResponseInitiativeDTO;
import kg.transparency.dt4ce.dto.user.UserForAdminDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    Page<UserForAdminDTO> getAllPendingUsers(Pageable pageable);

    Page<ResponseInitiativeDTO> getAllPendingInitiatives(Pageable pageable);

    ResponseEntity<String> approveUser(Long userId);

    ResponseEntity<String> rejectUser(Long userId, String reason);

    ResponseEntity<String> approveInitiative(Long initiativeId);

    ResponseEntity<String> rejectInitiative(Long initiativeId, String reason);
}
