package tech.omeganumeric.api.ubereats.validators.entities;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tech.omeganumeric.api.ubereats.domains.entities.Phone;

@Component("beforeCreatePhoneValidator")
public class PhoneValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Phone.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Phone entity = (Phone) o;
        if (checkInputString(entity.getNumber())) {
            errors.rejectValue("number", "number.empty");
        }
    }

    private boolean checkInputString(String input) {
        return (input == null || input.trim().length() == 0);
    }
}
