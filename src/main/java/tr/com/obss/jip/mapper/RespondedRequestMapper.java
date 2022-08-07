package tr.com.obss.jip.mapper;

import org.mapstruct.Mapper;
import tr.com.obss.jip.dto.RespondedRequestDto;
import tr.com.obss.jip.model.AddingBookRequest;
import tr.com.obss.jip.model.RespondedBookRequest;

@Mapper(componentModel = "spring")
public interface RespondedRequestMapper {
    RespondedBookRequest mapTo(AddingBookRequest addingBookRequest);

    RespondedRequestDto mapTo(RespondedBookRequest respondedBookRequest);
}
