package tech.omeganumeric.api.ubereats.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration repositoryRestConfiguration) {
//        repositoryRestConfiguration.getProjectionConfiguration().addProjection(Region.class);
//        ExposureConfiguration config = repositoryRestConfiguration.getExposureConfiguration();
//        config.withItemExposure((meta, httpMethods) -> httpMethods.disable(HttpMethod.PATCH));
//        config.withAssociationExposure((meta, httpMethods) -> httpMethods.disable(HttpMethod.PATCH));
//        config.withCollectionExposure((meta, httpMethods) -> httpMethods.disable(HttpMethod.PATCH));

//        config.forDomainType(Country.class).withItemExposure((meta, httpMethods) -> httpMethods.disable(HttpMethod.PATCH));
//        config.forDomainType(LanguageCountryName.class).withItemExposure((meta, httpMethods) -> httpMethods.disable(HttpMethod.PATCH));

    }
}
