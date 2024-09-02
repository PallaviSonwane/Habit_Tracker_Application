package com.dailyhabittrack.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import com.dailyhabittrack.constant.enums.UserResponseMessage;
import com.dailyhabittrack.entity.User;
import com.dailyhabittrack.exception.UserException;
import com.dailyhabittrack.mapper.UserMapper;
import com.dailyhabittrack.repository.UserRepository;
import com.dailyhabittrack.request.UserRequest;
import com.dailyhabittrack.response.UserResponse;
import com.dailyhabittrack.service.UserService;

@Service
public class UserServiceImpl implements UserService {
      private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserResponse createUserAccount(UserRequest userRequest) {
      String workFlow = "UserServiceImpl.CreateUserAccount";
        try {
            User user = UserMapper.INSTANCE
                    .userRequestToUser(userRequest);
            user.setTimestamp(LocalDateTime.now());
                    User createdCustomer = userRepository.save(user);
            return UserMapper.INSTANCE.userToUserResponse(createdCustomer);
        } catch (UserException ex) {
            throw new UserException(
                    UserResponseMessage.FAILED_TO_SAVE_USER.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, workFlow);
        }
    }

    @Override
    public List<UserResponse> getAllCustomer() {
      String workFlow = "UserServiceImpl.getAllCustomer";

      List<User> userList = userRepository.findAll();
      if (userList.isEmpty()) {
          throw new UserException(
                  UserResponseMessage.USER_NOT_EXISTS.getMessage(),
                  HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT, workFlow);
      }
      return UserMapper.INSTANCE.userListToUserResponseList(userList);
    }

    @Override
    public UserResponse getCustomer(Long userId) {
      String workFlow = "UserServiceImpl.getCustomer";

      User user = userRepository.findByUserId(userId);
      if (user == null) {
          throw new UserException(
                  UserResponseMessage.USER_NOT_FOUND.getMessage(userId),
                  HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, workFlow);
      }

      return UserMapper.INSTANCE.userToUserResponse(user);
    }

    @Override
    public void updateCustomer(UserRequest updateUserRequest, Long userId) {
      String workFlow = "UserServiceImpl.updateCustomer";

      UserResponse existingUser = getCustomer(userId);
      if (existingUser == null) {
          throw new UserException(
                  UserResponseMessage.USER_NOT_FOUND.getMessage(userId),
                  HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, workFlow);
      }

      User updateUser = UserMapper.INSTANCE
              .userRequestToUser(updateUserRequest);
      updateUser.setUserId(existingUser.getUserId());
      updateUser.setTimestamp(existingUser.getTimestamp());
      updateUser.setDeleted(existingUser.isDeleted());

      User updatedUser = userRepository.save(updateUser);
      logger.info("Updated core identity {}", updatedUser);
    }

    @Transactional
    @Override
    public void deleteCustomer(Long userId) {
      String workFlow = "UserServiceImpl.deleteCustomer";
      User existingUser = userRepository.findByUserId(userId);
      if (existingUser == null) {
          throw new UserException(
                  UserResponseMessage.USER_NOT_FOUND.getMessage(userId),
                  HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, workFlow);
      }
      if (existingUser.isDeleted()) {
          throw new UserException(
                  UserResponseMessage.USER_ALREADY_DELETED.getMessage(userId),
                  HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE, workFlow);
      }
      userRepository.softDeleteByUserId(userId);
    }


}
