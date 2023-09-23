package kg.transparency.dt4ce.service.Impl;

import kg.transparency.dt4ce.dto.initiative.ResponseInitiativeDTO;
import kg.transparency.dt4ce.dto.user.UserForAdminDTO;
import kg.transparency.dt4ce.enums.Status;
import kg.transparency.dt4ce.exception.NotFoundException;
import kg.transparency.dt4ce.model.Initiative;
import kg.transparency.dt4ce.model.Notification;
import kg.transparency.dt4ce.model.User;
import kg.transparency.dt4ce.repository.InitiativeRepository;
import kg.transparency.dt4ce.repository.NotificationRepository;
import kg.transparency.dt4ce.repository.UserRepository;
import kg.transparency.dt4ce.service.AdminService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kg.transparency.dt4ce.dto.initiative.ResponseInitiativeDTO.toResponseInitiativeDTOs;
import static kg.transparency.dt4ce.dto.user.UserForAdminDTO.toUserForAdminDTOs;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    UserRepository userRepository;
    NotificationRepository notificationRepository;
    InitiativeRepository initiativeRepository;

    @Override
    public Page<UserForAdminDTO> getAllPendingUsers(Pageable pageable) {
        Page<User> users = userRepository.findAllByStatus(Status.PENDING, pageable);
        List<UserForAdminDTO> userDTOs = toUserForAdminDTOs(users.toList());
        return new PageImpl<>(userDTOs, pageable, users.getTotalElements());
    }

    @Override
    public Page<ResponseInitiativeDTO> getAllPendingInitiatives(Pageable pageable) {
        Page<Initiative> initiatives = initiativeRepository.findAllByStatus(Status.PENDING, pageable);
        List<ResponseInitiativeDTO> initiativeDTOS = toResponseInitiativeDTOs(initiatives.toList());
        return new PageImpl<>(initiativeDTOS, pageable, initiatives.getTotalElements());
    }

    @Override
    public ResponseEntity<String> approveUser(Long userId) {
        User user = userRepository.findById(userId)
                .filter(u -> u.getStatus() == Status.PENDING)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким айди не найден"));

        user.setStatus(Status.ACTIVATED);
        userRepository.save(user);

        Notification notification = Notification.builder()
                .header("Верификация")
                .message("Ваш запрос на верификацию аккаунта был одобрен.")
                .user(user)
                .build();
        notificationRepository.save(notification);

        return ResponseEntity.ok("Аккаунт пользователя верифицирован");
    }

    @Override
    @Transactional
    public ResponseEntity<String> rejectUser(Long userId, String reason) {
        User user = userRepository.findById(userId)
                .filter(u -> u.getStatus() == Status.PENDING)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким айди не найден"));

        user.setStatus(Status.NOT_ACTIVATED);
        user.setPassFrontUrl(null);
        user.setPassBackUrl(null);
        user.setSelfieUrl(null);
        user.setName(null);
        user.setSurname(null);
        user.setDateOfBirth(null);
        userRepository.save(user);

        Notification notification = Notification.builder()
                .header("Верификация")
                .message("Ваш запрос на верификацию аккаунта был отклонён.\nПричина: " + reason)
                .user(user)
                .build();
        notificationRepository.save(notification);

        return ResponseEntity.ok("Запрос пользователя отклонён");
    }

    @Override
    public ResponseEntity<String> approveInitiative(Long initiativeId) {
        Initiative initiative = initiativeRepository.findById(initiativeId)
                .filter(i -> i.getStatus() == Status.PENDING)
                .orElseThrow(() -> new NotFoundException("Инициатива с таким айди не найдена"));

        initiative.setStatus(Status.ACTIVATED);
        initiativeRepository.save(initiative);

        Notification notification = Notification.builder()
                .header("Предложение инициативы")
                .message("Ваша инициатива была опубликована.")
                .user(initiative.getUser())
                .build();
        notificationRepository.save(notification);

        return ResponseEntity.ok("Инициатива одобрена");
    }

    @Override
    public ResponseEntity<String> rejectInitiative(Long initiativeId, String reason) {
        Initiative initiative = initiativeRepository.findById(initiativeId)
                .filter(i -> i.getStatus() == Status.PENDING)
                .orElseThrow(() -> new NotFoundException("Инициатива с таким айди не найдена"));

        initiative.setStatus(Status.NOT_ACTIVATED);
        initiativeRepository.save(initiative);

        Notification notification = Notification.builder()
                .header("Предложение инициативы")
                .message("Ваша инициатива была отклонена.\nПричина: " + reason)
                .user(initiative.getUser())
                .build();
        notificationRepository.save(notification);

        return ResponseEntity.ok("Инициатива отклонена");
    }
}
