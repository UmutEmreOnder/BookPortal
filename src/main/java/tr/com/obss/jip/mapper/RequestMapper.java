package tr.com.obss.jip.mapper;

import org.mapstruct.Mapper;
import tr.com.obss.jip.dto.RequestDto;
import tr.com.obss.jip.dto.create.CreateNewRequest;
import tr.com.obss.jip.model.Request;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    Request mapTo(RequestDto requestDto);

    RequestDto mapTo(Request request);

    Request mapTo(CreateNewRequest createNewRequest);
}
