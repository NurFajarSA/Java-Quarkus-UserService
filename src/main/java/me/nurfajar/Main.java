package me.nurfajar;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.Quarkus;
import jakarta.transaction.Transactional;
import me.nurfajar.model.Role;
import me.nurfajar.model.UserModel;

@QuarkusMain
public class Main {

    public static void main(String ... args) {
        Quarkus.run(MyApp.class, args);
    }

    public static class MyApp implements QuarkusApplication {
        @Override
        public int run(String... args) {
            initAdminUser();
            Quarkus.waitForExit();
            return 0;
        }

        @Transactional
        public void initAdminUser() {
            // init admin user
            var adminUser = UserModel.findByEmail("admin@gmail.com");
            if (adminUser == null) {
                adminUser = new UserModel();
                adminUser.setEmail("admin@gmail.com");
                adminUser.setUsername("admin");
                adminUser.setPassword(BcryptUtil.bcryptHash("Admin#1234"));
                adminUser.setRole(Role.ADMIN);
                adminUser.persist();
                System.out.println("Admin user created");
            } else {
                System.out.println("Admin user already exist");
            }
        }
    }
}
