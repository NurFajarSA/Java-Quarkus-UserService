package me.nurfajar.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import me.nurfajar.dto.UserMapper;
import me.nurfajar.dto.request.RegisterUserRequestDTO;
import me.nurfajar.dto.request.UpdateUserRequestDTO;
import me.nurfajar.model.Role;
import me.nurfajar.model.UserModel;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    UserMapper userMapper;

    public List<UserModel> listAllUsers() {
        return UserModel.listAll();
    }

    public UserModel getUserById(UUID id) {
        return UserModel.findById(id);
    }

    @Transactional
    public UserModel createUser(RegisterUserRequestDTO request) {
        UserModel user = userMapper.toUserModel(request);
        UserModel.persist(user);
        return user;
    }

    @Transactional
    public UserModel updateUser(UpdateUserRequestDTO request) {
        UserModel existingUser = UserModel.findById(UUID.fromString(request.getId()));
        if (existingUser != null) {
            existingUser.setUsername(request.getUsername());
            existingUser.setEmail(request.getEmail());
            existingUser.setPassword(request.getPassword());
            existingUser.setRole(Role.valueOf(request.getRole()));
            existingUser.setDateUpdate(LocalDateTime.now());
            UserModel.persist(existingUser);
            return existingUser;
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

    public UserModel getUserByUsername(String username) {
        return UserModel.findByUsername(username);
    }
}
