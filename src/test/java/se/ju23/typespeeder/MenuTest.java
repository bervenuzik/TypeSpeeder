package se.ju23.typespeeder;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.mockito.Mockito;
import se.ju23.typespeeder.MENU.MainMenu;
import se.ju23.typespeeder.Model.Language;
import se.ju23.typespeeder.Model.Menu;
import se.ju23.typespeeder.Services.MenuService;
import se.ju23.typespeeder.Services.PrintService;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {
    /*
    Visa teste som controlerar output av consolen funkar inte. Jag vet inte än varför.
    Förmodligen de scannar olicka streams
     */

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final  PrintService printer = spy(PrintService.class);
    private Menu menu;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        menu = new Menu(printer);
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testClassExists() {
        try {
            Class<?> clazz = Class.forName("se.ju23.typespeeder.Model.Menu");
            assertNotNull(clazz, "The class 'Menu' should exist.");
        } catch (ClassNotFoundException e) {
            fail("The class 'Menu' does not exist.", e);
        }
    }

    @Test
    public void testMethodExists() {
        try {
            Class<?> clazz = Class.forName("se.ju23.typespeeder.Model.Menu");
            Method method = clazz.getMethod("displayMenu");
            assertNotNull(method, "The method 'displayMenu()' should exist in the class 'Menu'.");
        } catch (ClassNotFoundException e) {
            fail("The class 'Menu' does not exist.", e);
        } catch (NoSuchMethodException e) {
            fail("The method 'displayMenu()' does not exist in the class 'Menu'.", e);
        }
    }

    @Test
    public void testMenuImplementsInterface() {
        try {
            Class<?> menuClass = Class.forName("se.ju23.typespeeder.Model.Menu");
            boolean implementsInterface = false;

            Class<?>[] interfaces = menuClass.getInterfaces();
            for (Class<?> iface : interfaces) {
                if (iface.equals(MenuService.class)) {
                    implementsInterface = true;
                    break;
                }
            }

            assertTrue(implementsInterface, "The class 'Menu' should implement the interface 'MenuService'.");
        } catch (ClassNotFoundException e) {
            fail("The class 'Menu' could not be found", e);
        }
    }

    @Test
    public void testDisplayMenuCallsGetMenuOptionsAndReturnsAtLeastFive() {
       // Menu menuMock = Mockito.spy(new Menu(printer));
        assertTrue(menu.displayMenu(MainMenu.class).size() >= 5, "'getMenuOptions()' should return at least 5 alternatives.");
    }

    @Test
    public void menuShouldHaveAtLeastFiveOptions() {
        //Menu menu = new Menu(printer);
        List<String> options = menu.displayMenu(MainMenu.class);
        assertTrue(options.size() >= 5, "The menu should contain at least 5 alternatives.");
    }

    @Test
    public void menuShouldPrintAtLeastFiveOptions() {
        //Menu menuMock = Mockito.spy(new Menu(printer));
        menu.displayMenu(MainMenu.class);
        long count = outContent.toString().lines().count();
        assertTrue(count >= 5, "The menu should print out at least 5 alternatives.");
    }

    @Test
    public void testUserCanChooseSwedishLanguage() {
        String input = "svenska\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Menu menu = new Menu(printer);
        menu.displayMenu(Language.class);
        String consoleOutput = outContent.toString();
        assertTrue(consoleOutput.contains("Svenska"), "Menu should prompt for language selection.");
        assertTrue(consoleOutput.contains("English"), "Menu should prompt for language selection.");
        /*Jag ändrade denna testen för att den functionen har ingentin att göra med Meny classen.
        den metoden måste testas in testerna för Challenge class.
        Meny class (eller MenuService) har ingenting att göra med språk val.
        Den jobbar bara med menyer och skriver ut dem.
         */

    }



}