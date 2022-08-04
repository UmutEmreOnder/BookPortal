package tr.com.obss.jip.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tr.com.obss.jip.exception.UserNotFoundException;
import tr.com.obss.jip.model.Role;
import tr.com.obss.jip.model.RoleType;
import tr.com.obss.jip.model.User;
import tr.com.obss.jip.service.RoleService;
import tr.com.obss.jip.service.UserService;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class AppInitConfig {
    private final RoleService roleService;
    private final UserService userService;

    @Bean
    public CommandLineRunner initRoles() {
        return args -> {
            final List<RoleType> allRoles = roleService.getAllRoles().stream().map(Role::getName).toList();

            Arrays.stream(RoleType.values()).filter(roleType -> !allRoles.contains(roleType)).forEach(roleType -> {
                Role role = new Role();
                role.setName(roleType);
                roleService.createNewRole(role);
            });

            try {
                userService.findByUsername("sys.admin");
            } catch (UserNotFoundException userNotFoundException) {
                User adminUser = User
                        .builder()
                        .name("System")
                        .surname("Admin")
                        .age(99)
                        .username("sys.admin")
                        .password("admin")
                        .roles(List.of(roleService.findByName(RoleType.ROLE_ADMIN), roleService.findByName(RoleType.ROLE_USER)))
                        .build();

                userService.createUser(adminUser);
            }
        };
    }
}
