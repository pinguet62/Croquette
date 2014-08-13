package fr.pinguet62.croquette.test.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.pinguet62.croquette.model.PhoneNumber;

/** Test for phone numbers of Google i18 API. */
public final class PhoneNumberTest {

    @Test
    public void equals() {
        PhoneNumber test = new PhoneNumber("0647618122");

        assertFalse(test.equals(null));
        assertFalse(test.equals(new PhoneNumber(null)));
        assertFalse(new PhoneNumber(null).equals(test));
        // Assert.assertFalse(new PhoneNumber(null).equals(new
        // PhoneNumber(null)));
        assertTrue(test.equals(test));

        // Separator
        assertTrue(new PhoneNumber("06 47 61 81 22").equals(test));
        assertTrue(new PhoneNumber("06.47.61.81.22").equals(test));
        assertTrue(new PhoneNumber("06-47-61-81-22").equals(test));

        // Country calling codes
        assertTrue(new PhoneNumber("+211647618122").equals(test));
        assertTrue(new PhoneNumber("+33647618122").equals(test));
        assertTrue(new PhoneNumber("(+33)647618122").equals(test));
    }

}
