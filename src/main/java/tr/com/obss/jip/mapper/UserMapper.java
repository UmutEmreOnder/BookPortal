package tr.com.obss.jip.mapper;

import org.mapstruct.Mapper;
import tr.com.obss.jip.dto.BaseUserDto;
import tr.com.obss.jip.dto.UserDto;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.BaseUser;
import tr.com.obss.jip.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto mapTo(User user);

    User mapTo(UserDto userDto);

    User mapTo(CreateNewUser createNewUserRequest);

    User mapTo(Author author);
    BaseUserDto mapUserToBase(User user);

    User mapBaseToUser(BaseUser user);
}
