package se.ju23.typespeeder.Services;

import se.ju23.typespeeder.Model.Word;

import java.util.List;

public interface WordService {
    List<Word> generateRandomWords(int amount);
}
