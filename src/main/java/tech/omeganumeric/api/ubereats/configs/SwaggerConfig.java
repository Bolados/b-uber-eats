package tech.omeganumeric.api.ubereats.configs;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@EnableSwagger2WebMvc
@Import({SpringDataRestConfiguration.class
        , BeanValidatorPluginsConfiguration.class
})
public class SwaggerConfig {

    private TypeResolver typeResolver;

    private BuildProperties buildProperties;

    @Autowired
    public SwaggerConfig(TypeResolver typeResolver, BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket oubereatsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("oubereats-webservices")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiMetadata())
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
//                .globalResponseMessage(RequestMethod.GET,
//                        CollectionHelper.newArrayList(new ResponseMessageBuilder()
//                                .code(500)
//                                .message("500 message")
//                                .responseModel(new ModelRef("Error"))
//                                .build()))
                .securitySchemes(securitySchemes())
                .securityContexts(securityContext())
                .enableUrlTemplating(true)
//                .globalOperationParameters(
//                        new ArrayList(new ParameterBuilder()
//                                .name("someGlobalParameter")
//                                .description("Description of someGlobalParameter")
//                                .modelRef(new ModelRef("string"))
//                                .parameterType("query")
//                                .required(true)
//                                .build()))
//                .tags(
//                        new Tag("Repositories", "All services relating to repositories"))
////                .additionalModels(typeResolver.resolve(AdditionalModel.class))
                ;
    }

    private List<ResponseMessage> getCustomizedResponseMessages() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(500).message("Server has crashed!!")
                .responseModel(new ModelRef("Error")).build());
        responseMessages.add(new ResponseMessageBuilder().code(403).message("You shall not pass!!")
                .build());
        return responseMessages;
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }


//    @Bean
//    public Docket api() {
//        return new
//                Docket(DocumentationType.SWAGGER_2)
//                .groupName("Example")
//                .directModelSubstitute(XMLGregorianCalendar.class, String.class)
//                .select()
//                .apis(RequestHandlerSelectors.any())
////                        .apis(RequestHandlerSelectors.withMethodAnnotation(RepositoryRestResource.class))
////                        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//                .paths(PathSelectors.any())
//                .build().apiInfo(apiMetadata())
////                    .securitySchemes(this.securitySchemes())
////                    .securityContexts(Arrays.asList(this.securityContext()))
//                ;
//
//    }

    /*
        Security implementation
     */
    public List<SecurityContext> securityContext() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("read", "for read operation"),
                new AuthorizationScope("write", "for write operation")
        };
        List<SecurityReference> securityReferences = Arrays.asList(
                new SecurityReference("User Authentification JsonWebTokenUser", scopes)
        );
        return Arrays.asList(
                SecurityContext.builder().securityReferences(securityReferences)
                        .forPaths(PathSelectors.any())
                        .build()
        );
    }

    public List<SecurityScheme> securitySchemes() {
        SecurityScheme userAuthToken = new ApiKey(
                "User Authentification JsonWebTokenUser",
                "Authorization",
                "header");
        return Arrays.asList(userAuthToken);
    }

    /**
     * Adds meta to Swagger
     *
     * @return ApiInfo
     */
    private ApiInfo apiMetadata() {
        String title = "UBER-EATS WEBSERVICE";
        String description = "Description";
        String company = "OMEGANUMERIC";
        String website = "omeganumeric.tech";
        String email = "contact@omeganumeric.tech";
        String licence = "";
        String licenceUrl = "";
        String termService = "";
        return new ApiInfoBuilder().title(title)
                .description(description)
                .version(buildProperties.getVersion())
                .contact(new Contact(company,
                        website,
                        email))
                .license(licence)
                .licenseUrl(licenceUrl)
                .termsOfServiceUrl(termService).build();
    }
}
