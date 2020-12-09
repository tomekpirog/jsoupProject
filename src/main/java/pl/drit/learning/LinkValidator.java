package pl.drit.learning;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkValidator implements ConstraintValidator<LinkAddress, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("((http?|https|ftp|file)://)?((W|w){3}.)?[a-zA-Z0-9]+\\.[a-zA-Z]+");
        Matcher matcher = pattern.matcher(value);
        try {
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }
}
