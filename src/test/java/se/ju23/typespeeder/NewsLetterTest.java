package se.ju23.typespeeder;

import org.junit.jupiter.api.Test;
import se.ju23.typespeeder.Model.NewsLetter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;

public class NewsLetterTest {

    @Test
    public void testNewsLetterClassExists() {
        try {
            Class.forName("se.ju23.typespeeder.Model.NewsLetter");
        } catch (ClassNotFoundException e) {
            throw new AssertionError("NewsLetter class should exist.", e);
        }
    }
    @Test
    public void testNewsLetterContentLength() {
        try {
            Class<?> newsLetterClass = Class.forName("se.ju23.typespeeder.Model.NewsLetter");

            Field contentField = newsLetterClass.getDeclaredField("content");
            assertNotNull(contentField, "Field 'content' should exist in NewsLetter.");

            assertTrue(contentField.getType().equals(String.class), "Field 'content' should be of type String.");
            Method contentValidator = newsLetterClass.getDeclaredMethod("contentValidation", String.class);
            assertNotNull(contentValidator, "Method 'contentValidation' should exist in NewsLetter.");

            NewsLetter test = new NewsLetter();
            test.setContent("This is a test message");

            assertFalse(test.contentValidation("1nfsiouanfuiesnaufineauifne"), "Content field length should be at least 100 characters.");
            assertFalse(test.contentValidation("1nfsiouanfuiesnaufineauifne1nfsiouanfuiesnaufineauifne1nfsiouanfuiesnaufineauifne1nfsiouanfuiesnaufineauifne1nfsiouanfuiesnaufineauifne1nfsiouanfuiesnaufineauifne1nfsiouanfuiesnaufineauifne1nfsiouanfuiesnaufineauifne1nfsiouanfuiesnaufineauifne1nfsiouanfuiesnaufineauifne1nfsiouanfuiesnaufineauifne1nfsiouanfuiesnaufineauifne"), "Content field length should be at most 200 characters.");

        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e) {
            fail("Error occurred while testing NewsLetter content field length.", e);
        }
    }

    @Test
    public void testNewsLetterPublishDateTime() {
        try {
            Class<?> someClass = Class.forName("se.ju23.typespeeder.Model.NewsLetter");

            Field publishDateTime = someClass.getDeclaredField("publishDateTime");
            assertNotNull(publishDateTime, "Field 'publishDateTime' should exist in NewsLetter class.");
            publishDateTime.setAccessible(true);

            assertTrue(publishDateTime.getType().equals(LocalDateTime.class), "Field 'publishDateTime' should be of type LocalDateTime.");

            NewsLetter test = new NewsLetter();
            LocalDateTime dateTimeValue = LocalDateTime.now();
            test.setPublishDateTime(dateTimeValue);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = dateTimeValue.format(formatter);
            assertEquals(formattedDateTime,test.getPublishDateTime(), "'publishDateTime' field should have format 'yyyy-MM-dd HH:mm:ss'.");

            Method getterMethod = someClass.getDeclaredMethod("getPublishDateTime");
            assertNotNull(getterMethod, "Getter method for the field 'publishDateTime' should exist.");

        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException e) {
            fail("Error occurred while testing properties of NewsLetter.", e);
        }
    }
}
