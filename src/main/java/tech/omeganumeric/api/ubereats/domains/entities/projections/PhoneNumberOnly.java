package tech.omeganumeric.api.ubereats.domains.entities.projections;

import org.springframework.data.rest.core.config.Projection;
import tech.omeganumeric.api.ubereats.domains.entities.Phone;

@Projection(name = "phone_number_only", types = {Phone.class})
public interface PhoneNumberOnly {
    String getNumber();
}
