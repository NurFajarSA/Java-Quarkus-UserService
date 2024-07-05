package me.nurfajar.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import me.nurfajar.dto.UserMapper;
import me.nurfajar.dto.request.CreateUserRequestDTO;
import me.nurfajar.dto.request.UpdateUserRequestDTO;
import me.nurfajar.model.Role;
import me.nurfajar.model.UserModel;
import me.nurfajar.repository.UserRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    UserMapper userMapper;

    public List<UserModel> listAllUsers() {
        return userRepository.listAll();
    }

    public UserModel getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Transactional
    public UserModel createUser(CreateUserRequestDTO request) {
        UserModel user = userMapper.toUserModel(request);
        userRepository.persist(user);
        return user;
    }

    @Transactional
    public UserModel updateUser(UpdateUserRequestDTO request) {
        UserModel existingUser = userRepository.findById(UUID.fromString(request.getId()));
        if (existingUser != null) {
            existingUser.setUsername(request.getUsername());
            existingUser.setEmail(request.getEmail());
            existingUser.setPassword(request.getPassword());
            existingUser.setRole(Role.valueOf(request.getRole()));
            existingUser.setDateUpdate(LocalDate.now());
            userRepository.persist(existingUser);
            return existingUser;
        }
        throw new RuntimeException("User not found");
    }

    @Transactional
    public boolean deleteUser(UUID id) {
        return userRepository.deleteById(id);
    }
}
