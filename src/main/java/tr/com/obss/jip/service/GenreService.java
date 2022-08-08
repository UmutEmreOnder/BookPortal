package tr.com.obss.jip.service;


import tr.com.obss.jip.model.Genre;
import tr.com.obss.jip.model.GenreType;

import java.util.List;

public interface GenreService {
    List<Genre> getAllGenres();

    Genre findByName(GenreType type);
    Genre findByName(String name);

    void createNewGenre(Genre genre);
}
