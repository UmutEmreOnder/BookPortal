package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.exception.GenreNotFoundException;
import tr.com.obss.jip.model.Genre;
import tr.com.obss.jip.model.GenreType;
import tr.com.obss.jip.repository.GenreRepository;
import tr.com.obss.jip.service.GenreService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenres() {
        return (List<Genre>) genreRepository.findAll();
    }

    @Override
    public Genre findByName(GenreType type) {
        return genreRepository.findGenreByName(type).orElseThrow(() -> new GenreNotFoundException(type.name()));
    }

    @Override
    public Genre findByName(String name) {
        try {
            return findByName(GenreType.valueOf(name));
        } catch (IllegalArgumentException exception) {
            throw new GenreNotFoundException(name);
        }
    }

    @Override
    public void createNewGenre(Genre genre) {
        genreRepository.save(genre);
    }
}
