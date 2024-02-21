package se.ju23.typespeeder.Services;

import se.ju23.typespeeder.Model.Sentence;

import java.util.List;

public interface SentencesService {
    void saveSentence(Sentence sentence);
    List<Sentence> getAllSentences();

}
