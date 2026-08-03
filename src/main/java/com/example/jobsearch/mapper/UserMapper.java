
package com.example.jobsearch.mapper;

import com.example.jobsearch.dto.user.UserEditRequest;
import com.example.jobsearch.dto.user.UserResponse;
import com.example.jobsearch.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "avatar",source = "avatar")
    @Mapping(target = "roleName", expression = "java(user.getRole().getName().name())")
    UserResponse toDto(User user);

    void updateUserFromDto(UserEditRequest dto, @MappingTarget User user);
}
