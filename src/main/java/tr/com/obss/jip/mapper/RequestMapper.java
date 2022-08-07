package tr.com.obss.jip.mapper;

import org.mapstruct.Mapper;
import tr.com.obss.jip.dto.RequestDto;
import tr.com.obss.jip.dto.create.CreateNewRequest;
import tr.com.obss.jip.model.AddingBookRequest;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    AddingBookRequest mapTo(RequestDto requestDto);

    RequestDto mapTo(AddingBookRequest addingBookRequest);

    AddingBookRequest mapTo(CreateNewRequest createNewRequest);
}
