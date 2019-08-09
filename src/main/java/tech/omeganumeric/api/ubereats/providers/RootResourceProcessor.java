package tech.omeganumeric.api.ubereats.providers;

import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;
import tech.omeganumeric.api.ubereats.controllers.OTPResourceController;

@Component
public class RootResourceProcessor implements ResourceProcessor<RepositoryLinksResource> {

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
//        resource.add(
//                ControllerLinkBuilder.linkTo(
//                        ControllerLinkBuilder.methodOn(
//                                MediaRepositoryController.class
//                        ).all()
//                ).withRel("medias")
//        );
        resource.add(
                ControllerLinkBuilder.linkTo(
                        ControllerLinkBuilder.methodOn(
                                OTPResourceController.class
                        ).generateOTP(null)
                ).withRel("generate_otp")
        );
        resource.add(
                ControllerLinkBuilder.linkTo(
                        ControllerLinkBuilder.methodOn(
                                OTPResourceController.class
                        ).validateOTP(null, null)
                ).withRel("validate_otp")
        );
        return resource;
    }
}
