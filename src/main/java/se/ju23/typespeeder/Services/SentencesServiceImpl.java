package se.ju23.typespeeder.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Model.Sentence;
import se.ju23.typespeeder.Repositories.SentencesRepo;

import java.util.List;
@Component
public class SentencesServiceImpl implements SentencesService{
    @Autowired
    private SentencesRepo sentencesRepo;
    @Override
    public List<Sentence> getAllSentences() {
        return sentencesRepo.findAll();
    }
    public void saveSentence(Sentence sentence){
        sentencesRepo.save(sentence);
    }
}
