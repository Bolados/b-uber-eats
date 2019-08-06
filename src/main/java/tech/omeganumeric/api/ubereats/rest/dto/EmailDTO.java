package tech.omeganumeric.api.ubereats.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Description;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Description(value = "EmailDTO DTO class.")
public class EmailDTO {

    private List<String> recipients;
    private List<String> ccList;
    private List<String> bccList;
    private String subject;
    private String body;
    private Boolean isHtml;
    private String attachmentPath;

}
