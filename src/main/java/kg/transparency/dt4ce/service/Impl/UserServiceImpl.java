package kg.transparency.dt4ce.service.Impl;

import kg.transparency.dt4ce.dto.user.ActivateUserDTO;
import kg.transparency.dt4ce.enums.Status;
import kg.transparency.dt4ce.model.User;
import kg.transparency.dt4ce.repository.UserRepository;
import kg.transparency.dt4ce.service.ImageService;
import kg.transparency.dt4ce.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    ImageService imageService;
    @Override
    public ResponseEntity<String> activateAccount(String name,
                                                  String surname,
                                                  LocalDate dateOfBirth,
                                                  MultipartFile passFront,
                                                  MultipartFile passBack,
                                                  MultipartFile selfie,
                                                  User user) {

        user.setName(name);
        user.setSurname(surname);
        user.setDateOfBirth(dateOfBirth);
        user.setPassFrontUrl(imageService.saveImage(passFront));
        user.setPassBackUrl(imageService.saveImage(passBack));
        user.setSelfieUrl(imageService.saveImage(selfie));

        user.setStatus(Status.PENDING);
        userRepository.save(user);

        return ResponseEntity.ok("Аккаунт успешно отправлен на рассмотрение администраторам!");
    }

    @Override
    public ResponseEntity<String> addImage(MultipartFile image, User user) {
        user.setImageUrl(imageService.saveImage(image));
        return ResponseEntity.ok("Изображение успешно добавлено");
    }
}
