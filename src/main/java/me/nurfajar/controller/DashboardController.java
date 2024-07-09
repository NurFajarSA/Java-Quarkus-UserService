package me.nurfajar.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import me.nurfajar.dto.response.BaseResponse;
import me.nurfajar.service.UserService;

@Path("/api/dashboard")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class DashboardController {

    @Inject
    UserService userService;

    @GET
    @Path("/total/user")
    public Response getTotalUser() {
        return Response.ok(
                new BaseResponse<>("Success get total user", userService.getTotalUser())
        ).build();
    }

    @GET
    @Path("/total/admin")
    public Response getTotalAdmin() {
        return Response.ok(
                new BaseResponse<>("Success get total admin", userService.getTotalAdmin())
        ).build();
    }

    @GET
    @Path("/total/login-attempt")
    public Response getTotalLoginAttempt() {
        return Response.ok(
                new BaseResponse<>("Success get total login attempt", userService.getTotalLoginAttempt())
        ).build();
    }
}
