package tr.com.obss.jip.mapper;

import org.mapstruct.Mapper;
import tr.com.obss.jip.dto.BaseUserDto;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.model.BaseUser;

@Mapper(componentModel = "spring")
public interface BaseUserMapper {
    BaseUserDto mapTo(BaseUser baseUser);

    BaseUser mapTo(CreateNewUser createNewUser);
}
