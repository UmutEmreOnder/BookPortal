package tr.com.obss.jip.mapper;

import org.mapstruct.Mapper;
import tr.com.obss.jip.dto.RespondedRequestDto;
import tr.com.obss.jip.model.Request;
import tr.com.obss.jip.model.RespondedRequest;

@Mapper(componentModel = "spring")
public interface RespondedRequestMapper {
    RespondedRequest mapTo(Request request);

    RespondedRequestDto mapTo(RespondedRequest respondedRequest);
}
