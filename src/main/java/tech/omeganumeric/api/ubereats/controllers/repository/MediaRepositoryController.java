package tech.omeganumeric.api.ubereats.controllers.repository;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.omeganumeric.api.ubereats.domains.entities.Media;
import tech.omeganumeric.api.ubereats.repositories.MediaRepository;
import tech.omeganumeric.api.ubereats.rest.media.MediaDtoRequest;
import tech.omeganumeric.api.ubereats.rest.media.MediaDtoResource;
import tech.omeganumeric.api.ubereats.services.MediaRepositoryService;

import java.net.URI;

@CrossOrigin(origins = "*", maxAge = 3600)
@RepositoryRestController
@RequestMapping(
        value = MediaRepository.PATH
)
public class MediaRepositoryController {

    private final MediaRepositoryService mediaRepositoryService;

    public MediaRepositoryController(MediaRepositoryService mediaRepositoryService) {
        this.mediaRepositoryService = mediaRepositoryService;
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET
    )
    public ResponseEntity<MediaDtoResource> get(
            @PathVariable final Long id
    ) {
        Media media = mediaRepositoryService.findMediaById(id);
        return ResponseEntity.ok(new MediaDtoResource(
                this.mediaRepositoryService.convertMediaToDto(media)
        ));
    }


    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity saveMedia(
            @RequestPart MediaDtoRequest mediaDtoRequest,
            @RequestPart MultipartFile file
    ) {
        Media media = mediaRepositoryService.save(file, mediaDtoRequest);
        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .buildAndExpand(media.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(this.mediaRepositoryService.convertMediaToDto(media));
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity updateMedia(
            @RequestPart Long id,
            @RequestPart MediaDtoRequest mediaDtoRequest,
            @RequestPart MultipartFile file
    ) {
        Media media = mediaRepositoryService.update(id, file, mediaDtoRequest);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(this.mediaRepositoryService.convertMediaToDto(media));
    }

    @RequestMapping(
            method = RequestMethod.DELETE
    )
    public ResponseEntity deleteMedia(@RequestParam Long id
    ) {
        mediaRepositoryService.delete(id);
        return ResponseEntity.ok().build();
    }

}
