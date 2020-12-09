package pl.drit.learning;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkValidationTest {

    @Test
    void test() {
        LinkValidator validator = new LinkValidator();
        assertTrue(validator.isValid("www.wp.pl", null));
        assertFalse(validator.isValid("012931u429", null));
        assertFalse(validator.isValid("Andrzej lubi placki węgierskie", null));
    }
}
