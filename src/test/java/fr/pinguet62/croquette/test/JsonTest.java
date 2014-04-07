package fr.pinguet62.croquette.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.junit.Test;

public final class JsonTest {

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
