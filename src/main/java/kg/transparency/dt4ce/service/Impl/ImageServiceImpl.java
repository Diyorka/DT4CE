package kg.transparency.dt4ce.service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import kg.transparency.dt4ce.exception.FileEmptyException;
import kg.transparency.dt4ce.service.ImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    String urlKey = "cloudinary://753949556892917:SCszCjA1duCgeAaMxDP-7Qq3dP8@diyor";

    @Override
    @SneakyThrows
    public String saveImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileEmptyException("File can't be empty");
        }

        File saveFile = Files.createTempFile(
                        System.currentTimeMillis() + "",
                        Objects.requireNonNull
                                        (file.getOriginalFilename(), "File should have extension")
                                .substring(file.getOriginalFilename().lastIndexOf("."))
                )
                .toFile();

        file.transferTo(saveFile);

        Cloudinary cloudinary = new Cloudinary((urlKey));

        Map upload = cloudinary.uploader().upload(saveFile, ObjectUtils.emptyMap());

        return (String) upload.get("url");
    }
}
