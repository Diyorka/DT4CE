package kg.transparency.dt4ce.service.Impl;

import kg.transparency.dt4ce.dto.initiative.RequestInitiativeDTO;
import kg.transparency.dt4ce.dto.initiative.ResponseInitiativeDTO;
import kg.transparency.dt4ce.enums.Status;
import kg.transparency.dt4ce.exception.NotFoundException;
import kg.transparency.dt4ce.model.Initiative;
import kg.transparency.dt4ce.model.User;
import kg.transparency.dt4ce.repository.InitiativeRepository;
import kg.transparency.dt4ce.service.ImageService;
import kg.transparency.dt4ce.service.InitiativeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static kg.transparency.dt4ce.dto.initiative.ResponseInitiativeDTO.toResponseInitiativeDTO;
import static kg.transparency.dt4ce.dto.initiative.ResponseInitiativeDTO.toResponseInitiativeDTOs;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InitiativeServiceImpl implements InitiativeService {
    InitiativeRepository initiativeRepository;
    ImageService imageService;

    @Override
    public ResponseInitiativeDTO getInitiativeById(Long id) {
        Initiative initiative = initiativeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Инициатива не найдена"));

        initiative.setViewsCount(initiative.getViewsCount() + 1);
        initiativeRepository.save(initiative);

        return toResponseInitiativeDTO(initiative);
    }

    @Override
    public Page<ResponseInitiativeDTO> getAllInitiatives(Pageable pageable) {
        Page<Initiative> initiatives = initiativeRepository.findAllByStatus(Status.ACTIVATED, pageable);

        List<Initiative> initiativesToUpdate = new ArrayList<>();
        for(Initiative initiative : initiatives){
            initiative.setViewsCount(initiative.getViewsCount() + 1);
            initiativesToUpdate.add(initiative);
        }
        initiativeRepository.saveAll(initiativesToUpdate);

        List<ResponseInitiativeDTO> initiativeDTOS = toResponseInitiativeDTOs(initiatives.toList());
        return new PageImpl<>(initiativeDTOS, pageable, initiatives.getTotalElements());
    }

    @Override
    public Page<ResponseInitiativeDTO> getMyInitiatives(Pageable pageable, User user) {
        Page<Initiative> initiatives = initiativeRepository.findAllByUserAndStatusIsNot(user, Status.DELETED, pageable);
        List<ResponseInitiativeDTO> initiativeDTOS = toResponseInitiativeDTOs(initiatives.toList());
        return new PageImpl<>(initiativeDTOS, pageable, initiatives.getTotalElements());
    }

    @Override
    public ResponseEntity<Long> addInitiative(RequestInitiativeDTO initiativeDTO, User user) {
        Initiative initiative = Initiative.builder()
                .title(initiativeDTO.getTitle())
                .description(initiativeDTO.getDescription())
                .status(Status.PENDING)
                .viewsCount(0)
                .user(user)
                .build();

        initiative = initiativeRepository.save(initiative);

        return ResponseEntity.ok(initiative.getId());
    }

    @Override
    public ResponseEntity<String> uploadImagesToInitiative(Long id, MultipartFile[] images, User user) {
        Initiative initiative = initiativeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Инициатива с таким айди не найдена"));

        initiative.setImageUrls(imageService.saveImages(images));
        initiativeRepository.save(initiative);

        return ResponseEntity.ok("Фотографии успешно добавлены");
    }

    @Override
    public ResponseEntity<String> updateInitiative(Long id, RequestInitiativeDTO initiativeDTO, User user) {
        Initiative initiative = initiativeRepository.findById(id)
                .filter(i -> i.getUser().equals(user))
                .orElseThrow(() -> new NotFoundException("Инициатива не найдена"));

        initiative.setTitle(initiativeDTO.getTitle());
        initiative.setDescription(initiativeDTO.getDescription());
        initiativeRepository.save(initiative);

        return ResponseEntity.ok("Инициатива обновлена");
    }

    @Override
    public ResponseEntity<String> deleteInitiative(Long id, User user) {
        Initiative initiative = initiativeRepository.findById(id)
                .filter(i -> i.getUser().equals(user))
                .orElseThrow(() -> new NotFoundException("Инициатива с таким айди не найдена"));

        initiative.setStatus(Status.DELETED);
        initiativeRepository.save(initiative);

        return ResponseEntity.ok("Инициатива удалена");
    }
}
