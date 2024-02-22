package se.ju23.typespeeder;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.ju23.typespeeder.Model.Challenge;
import se.ju23.typespeeder.Model.Menu;
import se.ju23.typespeeder.Model.Sentence;
import se.ju23.typespeeder.Model.Word;
import se.ju23.typespeeder.Repositories.SentencesRepo;
import se.ju23.typespeeder.Repositories.WordsRepo;
import se.ju23.typespeeder.Services.InputService;
import se.ju23.typespeeder.Services.MenuService;
import se.ju23.typespeeder.Services.PrintService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ChallengePerformanceTest {
    private static final int MAX_EXECUTION_TIME = 200;
    private static final int MILLISECONDS_CONVERSION = 1_000_000;
    private final WordsRepo wordService = mock(WordsRepo.class);
    private final SentencesRepo sentenceRepo = mock(SentencesRepo.class);
    private final PrintService printService = mock(PrintService.class);
    private final InputService inputService = mock(InputService.class);
    private final MenuService menu = mock(MenuService.class);
    private final ArrayList<Word> preparedWords = new ArrayList<>();
    private final ArrayList<Sentence> preparedSentences = new ArrayList<>();

    @BeforeEach
    public  void setUpBeforeAll() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50; i++){
            preparedWords.add(new Word("test"+i));
        }
        for (Word word : preparedWords) {
            word.wordSetUp();
        }
        for (Word word : preparedWords) {
            sb.append(word.getWord() + " ");
        }
        for (int i = 0; i < 50; i++) {
            preparedSentences.add(new Sentence(sb.toString()));
        }
    }
    @Test
    public void testStartChallengePerformance() {

        Challenge challenge = new Challenge(wordService ,sentenceRepo, printService, inputService,menu);
        when(sentenceRepo.findSentencesByComplexity(any())).thenReturn(preparedSentences);
        when(inputService.getUsersInput()).thenReturn("");
        when(wordService.generateRandomWords(anyInt(),anyInt(),anyInt())).thenReturn(preparedWords);
        long startTime = System.nanoTime();
        challenge.startChallenge();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / MILLISECONDS_CONVERSION;
        assertTrue(duration <= MAX_EXECUTION_TIME, "Starting a challenge took too long. Execution time: " + duration + " ms.");
    }
    @Test
    public void testLettersToTypePerformance() {
        Challenge challenge = new Challenge(wordService,sentenceRepo, printService, inputService,menu);
        when(sentenceRepo.findSentencesByComplexity(any())).thenReturn(preparedSentences);
        when(wordService.generateRandomWords(anyInt(),anyInt(),anyInt())).thenReturn(preparedWords);
        when(inputService.getUsersInput()).thenReturn("");
        long startTime = System.nanoTime();
        challenge.generateWords(50);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / MILLISECONDS_CONVERSION;
        assertTrue(duration <= MAX_EXECUTION_TIME, "Selecting letters to type took too long. Execution time: " + duration + " ms.");
    }
}
