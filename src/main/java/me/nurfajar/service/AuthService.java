package me.nurfajar.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import me.nurfajar.model.UserModel;
import me.nurfajar.security.JwtUtils;

@ApplicationScoped
public class AuthService {

    @Inject
    UserService userService;

    @Inject
    JwtUtils jwtUtils;

    public String login(UserModel user) {
        userService.updateLoginAttempt(user.getId());
        return "Bearer " + jwtUtils.generateToken(user);
    }
}
