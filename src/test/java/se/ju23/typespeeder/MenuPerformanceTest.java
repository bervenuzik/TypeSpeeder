package se.ju23.typespeeder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import se.ju23.typespeeder.MENU.MainMenu;
import se.ju23.typespeeder.Model.Challenge;
import se.ju23.typespeeder.Model.Language;
import se.ju23.typespeeder.Model.Menu;
import se.ju23.typespeeder.Repositories.SentencesRepo;
import se.ju23.typespeeder.Repositories.WordsRepo;
import se.ju23.typespeeder.Services.InputService;
import se.ju23.typespeeder.Services.MenuService;
import se.ju23.typespeeder.Services.PrintService;
import se.ju23.typespeeder.Services.PrintServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MenuPerformanceTest {

    private static final int MAX_EXECUTION_TIME_MENU = 15;
    private static final int MAX_EXECUTION_TIME_LANGUAGE_SELECTION = 100;
    private static final int MILLISECONDS_CONVERSION = 1_000_000;

    private WordsRepo wordRepo = mock(WordsRepo.class);

    private PrintService printer = spy(PrintServiceImpl.class);

    private InputService inputService = mock(InputService.class);

    private SentencesRepo sentenceRepo = mock(SentencesRepo.class);

    private MenuService menu = spy(MenuService.class);

    @Test
    public void testGetMenuOptionsExecutionTime() {
        long startTime = System.nanoTime();
        Menu menu = new Menu(printer);
        menu.displayMenu(MainMenu.class);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / MILLISECONDS_CONVERSION;

        assertTrue(duration <= MAX_EXECUTION_TIME_MENU, "Menu display took too long. Execution time: " + duration + " ms.");
    }

    @Test
    public void testUserCanChooseSwedishLanguageAndPerformance() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Challenge challenge = new Challenge(wordRepo,sentenceRepo,printer,inputService,menu);

        long startTime = System.nanoTime();

        when(inputService.getUserChoice(Language.values())).thenReturn(Language.SWEDISH);
        challenge.changeLanguage();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / MILLISECONDS_CONVERSION;

        String consoleOutput = outContent.toString();

        assertTrue(consoleOutput.contains("Choose language: "), "Menu should prompt for language selection.");

        assertTrue(consoleOutput.contains("Svenska valt."), "Menu should confirm Swedish language selection.");


        assertTrue(duration <= MAX_EXECUTION_TIME_LANGUAGE_SELECTION, "Menu display and language selection took too long. Execution time: " + duration + " ms.");
    }
}