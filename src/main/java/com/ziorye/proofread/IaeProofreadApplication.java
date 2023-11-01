package com.ziorye.proofread;

import com.ziorye.proofread.entity.Permission;
import com.ziorye.proofread.entity.Role;
import com.ziorye.proofread.entity.User;
import com.ziorye.proofread.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class IaeProofreadApplication {

    public static void main(String[] args) {
        SpringApplication.run(IaeProofreadApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return (args) -> {
            // user: admin
            User admin = new User();
            admin.setName("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setEmail("admin@example.com");
            admin.setCreatedAt(LocalDateTime.now());
            admin.setEnabled(true);
            // user: user
            User user = new User();
            user.setName("user");
            user.setPassword(passwordEncoder.encode("password"));
            admin.setEmail("user@example.com");
            admin.setCreatedAt(LocalDateTime.now());
            user.setEnabled(true);

            List<Role> roleSet = new ArrayList<>();
            // role: admin
            Role adminRole = new Role();
            adminRole.setName("admin");
            adminRole.setDescription("管理员");
            roleSet.add(adminRole);
            // role: user
            Role userRole = new Role();
            userRole.setName("user");
            userRole.setDescription("普通用户");
            roleSet.add(userRole);

            List<Permission> permissionSet = new ArrayList<>();
            // permission: dashboard
            Permission dashboard = new Permission();
            dashboard.setName("Dashboard");
            dashboard.setDescription("backend/dashboard");
            permissionSet.add(dashboard);
            // permission: empty
            Permission empty = new Permission();
            empty.setName("Empty");
            empty.setDescription("backend/empty");
            permissionSet.add(empty);

            // relationship
            adminRole.setPermissions(permissionSet);
            admin.setRoles(roleSet);

            userRepository.save(admin);
            userRepository.save(user);
        };
    }
}
