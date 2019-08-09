package tech.omeganumeric.api.ubereats.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.omeganumeric.api.ubereats.configs.FileStorageProperties;
import tech.omeganumeric.api.ubereats.domains.File;
import tech.omeganumeric.api.ubereats.domains.entities.Media;
import tech.omeganumeric.api.ubereats.repositories.MediaRepository;
import tech.omeganumeric.api.ubereats.rest.media.MediaDtoRequest;
import tech.omeganumeric.api.ubereats.rest.media.MediaDtoResponse;
import tech.omeganumeric.api.ubereats.services.filestorage.FileStorageService;

import javax.persistence.EntityNotFoundException;

@Service
public class MediaRepositoryService extends FileStorageService {

    private static final String PATH = "Media";

    private final MediaRepository mediaRepository;

    public MediaRepositoryService(FileStorageProperties fileStorageProperties, MediaRepository mediaRepository) {
        super(fileStorageProperties, PATH);
        this.mediaRepository = mediaRepository;
    }

    private Media convertFileToMedia(File file, MediaDtoRequest mediaDto) {
        return Media.builder()
                .name(file.getName())
                .type(file.getType())
                .menu(mediaDto.getMenu())
                .build();
    }

    public MediaDtoResponse convertMediaToDto(Media media) {
        return MediaDtoResponse.builder()
                .id(media.getId())
                .name(media.getName())
                .type(media.getType())
                .data(super.loadFileAsResource(media.getName()))
                .menu(media.getMenu())
                .build();
    }

    public Media findMediaById(Long id) {
        return this.mediaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("media not found")
        );
    }

    public Media save(MultipartFile multipartFile, MediaDtoRequest mediaDto) {
        File file = super.storeFile(multipartFile, mediaDto.getName());
        return this.mediaRepository.save(convertFileToMedia(file, mediaDto));
    }

    public Media update(Long id, MultipartFile multipartFile, MediaDtoRequest mediaDto) {
        Media media = this.findMediaById(id);
        File file = super.storeFile(multipartFile, media.getName());
        return this.mediaRepository.save(convertFileToMedia(file, mediaDto));
    }

    public void delete(Long id) {
        Media media = this.findMediaById(id);
        super.removeFile(media.getName());
        this.mediaRepository.deleteById(media.getId());
    }

}
