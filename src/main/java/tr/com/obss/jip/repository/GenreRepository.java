package tr.com.obss.jip.repository;

import org.springframework.data.repository.CrudRepository;
import tr.com.obss.jip.model.Genre;
import tr.com.obss.jip.model.GenreType;

import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    Optional<Genre> findGenreByName(GenreType name);
}
