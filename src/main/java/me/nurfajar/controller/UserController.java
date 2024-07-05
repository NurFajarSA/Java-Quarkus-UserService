package me.nurfajar.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.nurfajar.dto.request.CreateUserRequestDTO;
import me.nurfajar.dto.request.UpdateUserRequestDTO;
import me.nurfajar.model.UserModel;
import me.nurfajar.service.UserService;

import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @GET
    public Response listAllUsers() {
        try {
            return Response.ok(userService.listAllUsers()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") UUID id) {
        try {
            UserModel user = userService.getUserById(id);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build();
            }
            return Response.ok(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response createUser(CreateUserRequestDTO request) {
        try {
            UserModel user = userService.createUser(request);
            return Response.status(Response.Status.CREATED).entity(user).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    public Response updateUser(UpdateUserRequestDTO request) {
        try {
            UserModel updatedUser = userService.updateUser(request);
            if (updatedUser != null) {
                return Response.ok(updatedUser).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") UUID id) {
        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
