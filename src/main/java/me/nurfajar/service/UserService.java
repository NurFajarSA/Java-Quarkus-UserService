package me.nurfajar.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import me.nurfajar.dto.UserMapper;
import me.nurfajar.dto.request.RegisterUserRequestDTO;
import me.nurfajar.dto.request.UpdatePasswordRequestDTO;
import me.nurfajar.dto.request.UpdateUserRequestDTO;
import me.nurfajar.dto.response.UserResponse;
import me.nurfajar.model.Role;
import me.nurfajar.model.UserModel;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    UserMapper userMapper;

    public List<UserResponse> listAllUsers() {
        return userMapper.toUserResponseList(UserModel.listAll());
    }

    public UserResponse getUserById(UUID id) {
        return userMapper.toUserResponse(UserModel.findById(id));
    }

    @Transactional
    public UserResponse createUser(RegisterUserRequestDTO request) {
        UserModel user = userMapper.toUserModel(request);
        user.setPassword(BcryptUtil.bcryptHash(request.getPassword()));
        UserModel.persist(user);
        return userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse updateUser(UpdateUserRequestDTO request) {
        UserModel existingUser = UserModel.findById(UUID.fromString(request.getId()));
        if (existingUser != null) {
            existingUser.setUsername(request.getUsername());
            existingUser.setDateUpdate(LocalDateTime.now());
            UserModel.persist(existingUser);
            return userMapper.toUserResponse(existingUser);
        }
        throw new RuntimeException("User not found");
    }

    @Transactional
    public UserResponse updatePassword(UpdatePasswordRequestDTO request) {
        UserModel existingUser = UserModel.findById(UUID.fromString(request.getId()));
        if (existingUser != null) {
            if (BcryptUtil.matches(request.getOldPassword(), existingUser.getPassword())) {
                existingUser.setPassword(BcryptUtil.bcryptHash(request.getNewPassword()));
                existingUser.setDateUpdate(LocalDateTime.now());
                UserModel.persist(existingUser);
                return userMapper.toUserResponse(existingUser);
            }
            throw new RuntimeException("Old password is incorrect");
        }
        throw new RuntimeException("User not found");
    }

    @Transactional
    public void updateLoginAttempt(UUID userId) {
        UserModel user = UserModel.findById(userId);
        if (user != null) {
            user.setLoginAttempt(user.getLoginAttempt() + 1);
            user.setLastLogin(LocalDateTime.now());
            UserModel.persist(user);
        }
    }

    @Transactional
    public boolean deleteUser(UUID id) {
        return UserModel.deleteById(id);
    }

    public UserModel getUserByEmail(String email) {
        return UserModel.findByEmail(email);
    }

    public long getTotalUser() {
        return UserModel.count("role", Role.USER);
    }

    public long getTotalAdmin() {
        return UserModel.count("role", Role.ADMIN);
    }

    public long getTotalLoginAttempt() {
        return UserModel.totalLoginAttempt();
    }

    public boolean checkPassword(UUID userId, String password) {
        UserModel user = UserModel.findById(userId);
        String passwordHash = user.getPassword();
        return BcryptUtil.matches(password, passwordHash);
    }
}
