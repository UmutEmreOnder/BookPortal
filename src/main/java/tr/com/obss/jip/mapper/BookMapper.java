package tr.com.obss.jip.mapper;

import org.mapstruct.Mapper;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.UserSummaryDto;
import tr.com.obss.jip.dto.create.CreateNewBook;
import tr.com.obss.jip.model.Book;
import tr.com.obss.jip.model.User;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book mapTo(BookDto bookDto);

    BookDto mapTo(Book book);

    UserSummaryDto mapTo(User user);

    Book mapTo(CreateNewBook createNewBook);
}
