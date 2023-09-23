package kg.transparency.dt4ce.service;

import kg.transparency.dt4ce.dto.initiative.RequestInitiativeDTO;
import kg.transparency.dt4ce.dto.initiative.ResponseInitiativeDTO;
import kg.transparency.dt4ce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface InitiativeService {
    ResponseInitiativeDTO getInitiativeById(Long id);

    Page<ResponseInitiativeDTO> getAllInitiatives(Pageable pageable);

    Page<ResponseInitiativeDTO> getMyInitiatives(Pageable pageable, User user);

    ResponseEntity<Long> addInitiative(RequestInitiativeDTO initiativeDTO, User user);

    ResponseEntity<String> uploadImagesToInitiative(Long id, MultipartFile[] images, User user);

    ResponseEntity<String> updateInitiative(Long id, RequestInitiativeDTO initiativeDTO, User user);

    ResponseEntity<String> deleteInitiative(Long id, User user);
}
