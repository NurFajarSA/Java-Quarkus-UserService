package me.nurfajar.dto;

import me.nurfajar.dto.request.CreateUserRequestDTO;
import me.nurfajar.dto.request.UpdateUserRequestDTO;
import me.nurfajar.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jakarta")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreate", ignore = true)
    @Mapping(target = "dateUpdate", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "loginAttempt", ignore = true)
    UserModel toUserModel(CreateUserRequestDTO createUserRequestDTO);
}
