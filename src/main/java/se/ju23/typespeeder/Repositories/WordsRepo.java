package se.ju23.typespeeder.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.ju23.typespeeder.Model.Word;

import java.util.List;

public interface WordsRepo extends JpaRepository<Long , Word>{

    @Query(value = "SELECT * FROM words WHERE id >= (SELECT FLOOR(MAX(id) * RAND()) FROM words) ORDER BY id LIMIT ?", nativeQuery = true)
    List<Word> findRandomWords(int limit);
}
