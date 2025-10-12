
package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.user.UserEditRequest;
import com.example.jobsearch.dto.user.UserRegisterRequest;
import com.example.jobsearch.dto.user.UserResponse;
import com.example.jobsearch.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "avatar",source = "avatar")
    UserResponse toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "role", ignore = true)
    User fromRegisterDto(UserRegisterRequest dto);

    void updateUserFromDto(UserEditRequest dto, @MappingTarget User user);
}
