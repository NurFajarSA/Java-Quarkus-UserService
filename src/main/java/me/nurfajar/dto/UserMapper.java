package me.nurfajar.dto;

import me.nurfajar.dto.request.RegisterUserRequestDTO;
import me.nurfajar.dto.response.UserResponse;
import me.nurfajar.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "jakarta")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreate", ignore = true)
    @Mapping(target = "dateUpdate", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "loginAttempt", ignore = true)
    UserModel toUserModel(RegisterUserRequestDTO registerUserRequestDTO);

    UserResponse toUserResponse(UserModel userModel);

    List<UserResponse> toUserResponseList(List<UserModel> userModels);
}
