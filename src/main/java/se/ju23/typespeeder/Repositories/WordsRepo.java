package se.ju23.typespeeder.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.ju23.typespeeder.Model.Word;

import java.util.List;
@Repository
public interface WordsRepo extends JpaRepository<Word , Long>{

    @Query(value = "SELECT * FROM words ORDER BY RAND() where lenght(word) between ?2 and ?3 LIMIT ?1", nativeQuery = true)
    List<Word> findRandomWords(int limit , int minLenght , int maxLenght);
}
