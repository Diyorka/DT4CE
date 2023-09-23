package kg.transparency.dt4ce.service;

import kg.transparency.dt4ce.model.Initiative;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    String saveImage(MultipartFile image);

    List<String> saveImages(MultipartFile[] images);
}
