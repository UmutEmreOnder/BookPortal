package tr.com.obss.jip.mapper;

import org.mapstruct.Mapper;
import tr.com.obss.jip.dto.CommentDto;
import tr.com.obss.jip.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto mapTo(Comment comment);
}
