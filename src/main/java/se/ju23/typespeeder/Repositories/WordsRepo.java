package se.ju23.typespeeder.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.ju23.typespeeder.Model.Word;

import java.util.List;
@Repository
public interface WordsRepo extends JpaRepository<Word , Long>{

    @Query(value = "SELECT * FROM words where length(word) between ? and ? ORDER BY RAND() LIMIT ?", nativeQuery = true)
    List<Word> generateRandomWords(int minLenght , int maxLenght , int limit );
}
