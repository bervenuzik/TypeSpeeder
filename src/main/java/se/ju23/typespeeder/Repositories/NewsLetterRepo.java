package se.ju23.typespeeder.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.ju23.typespeeder.Model.NewsLetter;

@Repository
public interface NewsLetterRepo extends JpaRepository<NewsLetter, Long> {
}
