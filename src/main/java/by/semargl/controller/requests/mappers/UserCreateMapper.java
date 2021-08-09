package by.semargl.controller.requests.mappers;

import by.semargl.controller.requests.UserCreateRequest;
import by.semargl.domain.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserCreateMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromUserCreateRequest(UserCreateRequest userCreateRequest, @MappingTarget User user);
}
