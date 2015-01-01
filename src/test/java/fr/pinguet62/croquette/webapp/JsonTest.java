package fr.pinguet62.croquette.webapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.junit.Test;

import fr.pinguet62.croquette.webapp.action.IAction;

public final class JsonTest {

    /** Format and parse {@link Date}. */
    @Test
    public void date() {
        Calendar calendar = Calendar.getInstance();
        // calendar.setTimeInMillis(0);
        calendar.set(Calendar.YEAR, 1989);
        calendar.set(Calendar.MONTH, 5);
        calendar.set(Calendar.DAY_OF_MONTH, 14);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 15);
        calendar.set(Calendar.SECOND, 49);
        String formattedDate = IAction.FORMATTER.format(calendar.getTime());
        assertEquals("1989-06-14T23:15:49", formattedDate);
    }

    /** {@link NullPointerException} thrown if the key is not found. */
    @Test
    public void keyNotFound() {
        try (JsonReader jsonReader = Json.createReader(new StringReader(
                "{\"key\": \"value\"}"))) {
            JsonObject jsonObject = jsonReader.readObject();
            assertEquals("value", jsonObject.getString("other"));
            fail(NullPointerException.class.getSimpleName() + " not thrown.");
        } catch (NullPointerException exception) {
            // OK
        } catch (Exception exception) {
            fail(exception.getMessage());
        }
    }

    /** Read a simple JSON message. */
    @Test
    public void parse() {
        try (JsonReader jsonReader = Json.createReader(new StringReader(
                "{\"key\": \"value\"}"))) {
            JsonObject jsonObject = jsonReader.readObject();
            assertEquals("value", jsonObject.getString("key"));
        } catch (Exception exception) {
            fail(exception.getMessage());
        }
    }

}
