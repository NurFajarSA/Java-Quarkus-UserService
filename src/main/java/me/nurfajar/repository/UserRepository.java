package me.nurfajar.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import me.nurfajar.model.UserModel;

import java.util.UUID;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<UserModel, UUID> {
    
}
