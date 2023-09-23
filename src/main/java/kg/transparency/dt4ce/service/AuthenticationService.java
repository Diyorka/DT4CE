package kg.transparency.dt4ce.service;

import kg.transparency.dt4ce.dto.auth.AuthRequestDTO;
import kg.transparency.dt4ce.dto.auth.AuthResponseDTO;
import kg.transparency.dt4ce.dto.user.ActivateUserDTO;
import kg.transparency.dt4ce.dto.user.RequestUserDTO;
import kg.transparency.dt4ce.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

public interface AuthenticationService {
    AuthResponseDTO register(RequestUserDTO userDTO);

    AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO);
}
