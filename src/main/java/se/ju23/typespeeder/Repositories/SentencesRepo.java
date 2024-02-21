package se.ju23.typespeeder.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.ju23.typespeeder.Model.Sentence;
import se.ju23.typespeeder.Model.SentenceComplexity;

import java.util.List;

@Repository
public interface SentencesRepo  extends JpaRepository<Sentence, Long>{

    List<Sentence> findSentencesByComplexity(SentenceComplexity sentenceComplexity);
}
