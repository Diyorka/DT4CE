package kg.transparency.dt4ce.service.Impl;

import kg.transparency.dt4ce.dto.auth.AuthRequestDTO;
import kg.transparency.dt4ce.dto.auth.AuthResponseDTO;
import kg.transparency.dt4ce.dto.user.RequestUserDTO;
import kg.transparency.dt4ce.enums.Role;
import kg.transparency.dt4ce.enums.Status;
import kg.transparency.dt4ce.exception.*;
import kg.transparency.dt4ce.model.User;
import kg.transparency.dt4ce.repository.UserRepository;
import kg.transparency.dt4ce.service.AuthenticationService;
import kg.transparency.dt4ce.service.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;

    @Override
    public AuthResponseDTO register(RequestUserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с такой почтой уже существует");
        }

        if(userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())){
            throw new UserAlreadyExistException("Пользователь с таким номером телефона уже существует");
        }

        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new NotMatchException("Пароли не совпадают");
        }

        var user = userRepository.save(buildUser(userDTO));
        var jwtToken = jwtService.generateToken(user);

        return AuthResponseDTO.builder()
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {
        var user = userRepository.findByEmail(authRequestDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("Пользователь с такой почтой не найден"));

        if(!passwordEncoder.matches(authRequestDTO.getPassword(), user.getPassword())){
            throw new NotMatchException("Вы ввели неверный пароль");
        }

        var jwtToken = jwtService.generateToken(user);

        return AuthResponseDTO.builder()
                .accessToken(jwtToken)
                .build();
    }

    private User buildUser(RequestUserDTO userDTO) {
        return User.builder()
                .email(userDTO.getEmail())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.USER)
                .status(Status.NOT_ACTIVATED)
                .build();
    }
}
