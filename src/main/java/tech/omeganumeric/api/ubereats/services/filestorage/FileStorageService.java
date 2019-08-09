/**
 *
 */
package tech.omeganumeric.api.ubereats.services.filestorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tech.omeganumeric.api.ubereats.configs.FileStorageProperties;
import tech.omeganumeric.api.ubereats.domains.File;
import tech.omeganumeric.api.ubereats.exceptions.FileStorageException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author BSCAKO
 *
 */
@Slf4j
public abstract class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(FileStorageProperties fileStorageProperties, String path) {
        log.debug("Initializing");
        String realPath = fileStorageProperties.getPath() + path;
        try {
            this.fileStorageLocation = Paths.get(
                    ResourceUtils.getFile(realPath).getAbsolutePath()
            ).toAbsolutePath().normalize();
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }


    /**
     *
     * @param multipartFile
     * @return
     */
    public File storeFile(MultipartFile multipartFile, String fileName) {
        File file = null;
        if ((fileName == null) && (multipartFile.getOriginalFilename() != null)) {
            fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        }
        if (fileName != null) {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with
            // the same name)
            try {
                Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                file = File.builder()
                        .name(fileName)
                        .type(multipartFile.getContentType())
                        .data(loadFileAsResource(fileName))
                        .build();
            } catch (IOException e) {
                log.error("Sorry! Can store file : {}", e);
                throw new FileStorageException("Sorry! Can store file", e);
            }
        }
        return file;
    }

    public void removeFile(String fileName) {
        Path targetLocation = this.fileStorageLocation.resolve(fileName).normalize();
        try {
            Files.delete(targetLocation);
        } catch (IOException e) {
            log.error("Sorry! Can store file : {}", e);
            throw new FileStorageException("Sorry! Can remove file", e);
        }
    }

    /**
     *
     * @param fileName
     * @return
     */
    public Resource loadFileAsResource(String fileName) {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            log.error("Sorry! Can find file : {}", e);
            throw new FileStorageException("File not found ", e);
        }
        if (resource.exists()) {
            return resource;
        } else {
            throw new FileStorageException("File not found ");
        }
    }

//	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.asList(files)
//                .stream()
//                .map(file -> uploadFile(file))
//                .collect(Collectors.toList());
//    }


}
