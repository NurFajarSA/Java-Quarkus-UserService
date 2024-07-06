package me.nurfajar.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.nurfajar.dto.request.UpdateUserRequestDTO;
import me.nurfajar.dto.response.BaseResponse;
import me.nurfajar.model.UserModel;
import me.nurfajar.service.UserService;

import java.util.UUID;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @GET
    @RolesAllowed("ADMIN")
    public Response listAllUsers() {
        try {
            return Response.ok(
                    new BaseResponse<>("Success get all users", userService.listAllUsers())
            ).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new BaseResponse<>(e.getMessage(), null)
            ).build();
        }
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    @Path("/{id}")
    public Response getUserById(@PathParam("id") UUID id) {
        try {
            UserModel user = userService.getUserById(id);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(
                        new BaseResponse<>("User not found", null)
                ).build();
            }
            return Response.ok(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new BaseResponse<>(e.getMessage(), null)
            ).build();
        }
    }

    @PUT
    @RolesAllowed({"ADMIN", "USER"})
    public Response updateUser(UpdateUserRequestDTO request) {
        try {
            UserModel updatedUser = userService.updateUser(request);
            if (updatedUser != null) {
                return Response.ok(updatedUser).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity(
                        new BaseResponse<>("User not found", null)
                ).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new BaseResponse<>(e.getMessage(), null)
            ).build();
        }
    }

    @DELETE
    @RolesAllowed("ADMIN")
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") UUID id) {
        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity(
                        new BaseResponse<>("User not found", null)
                ).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new BaseResponse<>(e.getMessage(), null)
            ).build();
        }
    }
}
