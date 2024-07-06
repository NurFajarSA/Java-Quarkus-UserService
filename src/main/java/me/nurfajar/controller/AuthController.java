package me.nurfajar.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.nurfajar.dto.request.RegisterUserRequestDTO;
import me.nurfajar.dto.request.LoginUserRequestDTO;
import me.nurfajar.dto.response.BaseResponse;
import me.nurfajar.model.UserModel;
import me.nurfajar.security.JwtUtils;
import me.nurfajar.service.AuthService;
import me.nurfajar.service.UserService;

@PermitAll
@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    UserService userService;

    @Inject
    AuthService authService;

    @Inject
    JwtUtils jwtUtils;

    @POST
    @Path("/register")
    public Response createUser(RegisterUserRequestDTO request) {
        try {
            var tempUser = userService.getUserByEmail(request.getEmail());
            if (tempUser != null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Email already registered").build();
            }

            tempUser = userService.getUserByUsername(request.getUsername());
            if (tempUser != null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Username already registered").build();
            }

            UserModel user = userService.createUser(request);
            return Response.status(Response.Status.CREATED).entity(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/login")
    public Response loginUser(LoginUserRequestDTO request) {
        try {
            UserModel user = userService.getUserByEmail(request.getEmail());
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(
                        new BaseResponse<>("User not found", null)
                ).build();
            }

            if (!user.getPassword().equals(request.getPassword())) {
                return Response.status(Response.Status.BAD_REQUEST).entity(
                        new BaseResponse<>("Invalid password", null)
                ).build();
            }

            String token = authService.login(user);
            return Response.ok(
                    new BaseResponse<>("Login success", token)
            ).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new BaseResponse<>(e.getMessage(), null)
            ).build();
        }
    }

    @GET
    @Path("/claims/{token}")
    public Response getClaims(@PathParam("token") String token) {
        try {
            UserModel user = jwtUtils.parseToken(token);
            return Response.ok(
                    new BaseResponse<>("Success get claims", user)
            ).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new BaseResponse<>(e.getMessage(), null)
            ).build();
        }
    }

}
