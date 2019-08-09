package tech.omeganumeric.api.ubereats.controllers.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
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
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@BasePathAwareController
public class MediaRepositoryController {

    private final MediaRepositoryService mediaRepositoryService;

    public MediaRepositoryController(MediaRepositoryService mediaRepositoryService) {
        this.mediaRepositoryService = mediaRepositoryService;
    }

    @RequestMapping(
            path = MediaRepository.PATH + "/search/findById",
            method = RequestMethod.GET
    )
    public ResponseEntity<MediaDtoResource> findMediaById(
            @PathVariable final Long id
    ) {
        log.debug("Controller get media ");
        Media media = mediaRepositoryService.findMediaById(id);
        return ResponseEntity.ok(new MediaDtoResource(
                this.mediaRepositoryService.convertMediaToDto(media)
        ));
    }

    @RequestMapping(
            path = MediaRepository.PATH + "/search/findByName",
            method = RequestMethod.GET
    )
    public ResponseEntity<MediaDtoResource> findByNameMedia(
            @RequestParam final String name
    ) {
        log.debug("Controller find media by name");
        Media media = mediaRepositoryService.findMediaByName(name);
        return ResponseEntity.ok(new MediaDtoResource(
                this.mediaRepositoryService.convertMediaToDto(media)
        ));
    }

    @RequestMapping(
            path = MediaRepository.PATH + "/search/findAll",
            method = RequestMethod.GET
    )
    public ResponseEntity<Resources<MediaDtoResource>> findAllMedia() {
        log.debug("Controller get all media ");
        final List<MediaDtoResource> mediaDtoResources = mediaRepositoryService.findAllMedia()
                .stream()
                .map(media -> new MediaDtoResource(
                        this.mediaRepositoryService.convertMediaToDto(media)
                )).collect(Collectors.toList());
        final Resources<MediaDtoResource> resources = new Resources<>(mediaDtoResources);
        resources.add(
                new Link(
                        ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(),
                        "self"
                )
        );
        return ResponseEntity.ok(resources);
    }

    @RequestMapping(
            path = MediaRepository.PATH,
            method = RequestMethod.GET
    )
    public ResponseEntity<Resources<MediaDtoResource>> allMedia(
            @RequestParam final String page,
            @RequestParam final String sort,
            @RequestParam final String size
    ) {
        log.debug("Controller get all media ");
        final List<MediaDtoResource> mediaDtoResources = mediaRepositoryService.findAllMedia(
                page, sort, size)
                .stream()
                .map(media -> new MediaDtoResource(
                        this.mediaRepositoryService.convertMediaToDto(media)
                )).collect(Collectors.toList());
        final Resources<MediaDtoResource> resources = new Resources<>(mediaDtoResources);
        resources.add(
                new Link(
                        ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString(),
                        "self"
                )
        );
        return ResponseEntity.ok(resources);
    }


    @RequestMapping(
            path = MediaRepository.PATH,
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity saveMedia(
            @RequestPart MediaDtoRequest mediaDtoRequest,
            @RequestPart MultipartFile file
    ) {
        log.debug("Controller save media ");
        Media media = mediaRepositoryService.save(file, mediaDtoRequest);
        final URI uri =
                MvcUriComponentsBuilder.fromController(getClass())
                        .path("/{id}")
                        .buildAndExpand(media.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(this.mediaRepositoryService.convertMediaToDto(media));
    }

    @RequestMapping(
            path = MediaRepository.PATH + "/{id}",
            method = {RequestMethod.PUT, RequestMethod.PATCH},
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity saveMedia(
            @PathVariable Long id,
            @RequestPart MediaDtoRequest mediaDtoRequest,
            @RequestPart MultipartFile file
    ) {
        log.debug("Controller put media ");
        Media media = mediaRepositoryService.update(id, file, mediaDtoRequest);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(this.mediaRepositoryService.convertMediaToDto(media));
    }

    @RequestMapping(
            path = MediaRepository.PATH + "/{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity deleteMedia(@PathVariable Long id
    ) {
        log.debug("Controller delete media ");
        mediaRepositoryService.delete(id);
        return ResponseEntity.ok().build();
    }

}
