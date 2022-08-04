package tr.com.obss.jip.mapper;

import org.mapstruct.Mapper;
import tr.com.obss.jip.dto.AuthorDto;
import tr.com.obss.jip.dto.create.CreateNewAuthor;
import tr.com.obss.jip.model.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author mapTo(AuthorDto authorDto);

    AuthorDto mapTo(Author author);

    Author mapTo(CreateNewAuthor createNewAuthor);
}
