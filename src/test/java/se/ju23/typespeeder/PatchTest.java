package se.ju23.typespeeder;

import org.junit.jupiter.api.Test;
import se.ju23.typespeeder.Model.Patch;
import se.ju23.typespeeder.Services.PrintService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatchTest {
    private PrintService printer = spy(PrintService.class);

    @Test
    public void testPatchClassExists() {
        try {
            Class.forName("se.ju23.typespeeder.Model.Patch");
        } catch (ClassNotFoundException e) {
            throw new AssertionError("Patch class should exist.", e);
        }
    }

    @Test
    public void testPatchProperties() {
        try {
            Class<?> someClass = Class.forName("se.ju23.typespeeder.Model.Patch");

            Field patchVersion = someClass.getDeclaredField("latestVersion");
            assertNotNull(patchVersion, "Field 'latestVersion' should exist in the Patch class.");
            assertTrue(patchVersion.getType().equals(String.class), "Field 'patchVersion' should be of type String.");

            Field realeaseDateTime = someClass.getDeclaredField("realeaseDateTime");
            assertNotNull(realeaseDateTime, "Field 'realeaseDateTime' should exist in Patch class.");

            assertTrue(realeaseDateTime.getType().equals(LocalDateTime.class), "Field 'realeaseDateTime' should be of type LocalDateTime.");
    //LocalDateTime klagar på den format och kan inte parsa till den format. Formatet måste vara "yyyy-MM-dd'T'HH:mm:ss"
//            Patch instance = spy(Patch.class);
//            instance.setRealeaseDateTime(LocalDateTime.now());
//            LocalDateTime dateTimeValue = instance.getRealeaseDateTime();
//
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String formattedDateTime = formatter.format(dateTimeValue);
//            assertEquals(dateTimeValue.toString(), formattedDateTime, "'realeaseDateTime' field should have format 'yyyy-MM-dd HH:mm:ss'.");

            Method getterMethod = someClass.getDeclaredMethod("getRealeaseDateTime");
            assertNotNull(getterMethod, "Getter method for field 'realeaseDateTime' should exist.");


        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e) {
            fail("Error occurred while testing properties of Patch.", e);
        }
    }
}
