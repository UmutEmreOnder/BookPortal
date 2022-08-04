package tr.com.obss.jip.mapper;

import org.mapstruct.Mapper;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.create.CreateNewBook;
import tr.com.obss.jip.model.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book mapTo(BookDto bookDto);

    BookDto mapTo(Book book);

    Book mapTo(CreateNewBook createNewBook);
}
