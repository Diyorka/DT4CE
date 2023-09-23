package kg.transparency.dt4ce.service;

import kg.transparency.dt4ce.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface UserService {
    ResponseEntity<String> activateAccount(String name,
                                           String surname,
                                           LocalDate dateOfBirth,
                                           MultipartFile passFront,
                                           MultipartFile passBack,
                                           MultipartFile selfie,
                                           User user);

    ResponseEntity<String> addImage(MultipartFile image, User user);
}
