package com.dailyhabittrack.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.dailyhabittrack.entity.User;
import com.dailyhabittrack.request.UserRequest;
import com.dailyhabittrack.response.UserResponse;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userRequestToUser(UserRequest request);

    List<UserResponse> userListToUserResponseList(List<User> userList);

    UserResponse userToUserResponse(User data);

}
