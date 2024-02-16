package se.ju23.typespeeder.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Model.Word;
import se.ju23.typespeeder.Repositories.WordsRepo;

import java.util.ArrayList;
import java.util.List;

@Component
public class WordServiceImpl implements WordService {
@Autowired
private WordsRepo wordsRepo;

    public WordServiceImpl(WordsRepo wordsRepo) {
        this.wordsRepo = wordsRepo;
    }

    public WordServiceImpl() {
    }

    public List<Word> generateRandomWords(int amount) {
        List<Word> words;
        words = wordsRepo.findRandomWords(amount);
        return words;
    }
}
