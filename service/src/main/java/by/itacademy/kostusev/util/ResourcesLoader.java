package by.itacademy.kostusev.util;

import lombok.SneakyThrows;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class ResourcesLoader {

    @SneakyThrows
    public Resource loadAsResource(String filename) {
        Resource resource = new UrlResource(getFileUri(filename));
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Could not read file: " + filename);
        }
    }

    @SneakyThrows
    private URI getFileUri(String filename) {
        return new FileSystemResource(filename).getURI();
    }
}
