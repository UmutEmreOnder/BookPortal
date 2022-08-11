package tr.com.obss.jip.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tr.com.obss.jip.exception.UserNotFoundException;
import tr.com.obss.jip.model.BaseUser;
import tr.com.obss.jip.model.Genre;
import tr.com.obss.jip.model.GenreType;
import tr.com.obss.jip.model.Role;
import tr.com.obss.jip.model.RoleType;
import tr.com.obss.jip.service.BaseUserService;
import tr.com.obss.jip.service.GenreService;
import tr.com.obss.jip.service.RoleService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class AppInitConfig {
    private final RoleService roleService;
    private final GenreService genreService;
    private final BaseUserService baseUserService;

    @Bean
    public CommandLineRunner initRoles() {
        return args -> {
            final List<RoleType> allRoles = roleService.getAllRoles().stream().map(Role::getName).toList();

            Arrays.stream(RoleType.values()).filter(roleType -> !allRoles.contains(roleType)).forEach(roleType -> {
                Role role = new Role();
                role.setName(roleType);
                roleService.createNewRole(role);
            });

            final List<GenreType> allGenres = genreService.getAllGenres().stream().map(Genre::getName).toList();

            Arrays.stream(GenreType.values()).filter(genreType -> !allGenres.contains(genreType)).forEach(genreType -> {
                Genre genre = new Genre();
                genre.setName(genreType);
                genreService.createNewGenre(genre);
            });

            try {
                baseUserService.getUserByUsername("sys.admin");
            } catch (UserNotFoundException userNotFoundException) {
                BaseUser adminUser = BaseUser
                        .builder()
                        .name("System")
                        .surname("Admin")
                        .age(0)
                        .email("admin@bookportal.com")
                        .username("sys.admin")
                        .password("admin")
                        .createDate(new Date())
                        .roles(List.of(roleService.findByName(RoleType.ROLE_ADMIN), roleService.findByName(RoleType.ROLE_USER), roleService.findByName(RoleType.ROLE_AUTHOR)))
                        .build();

                baseUserService.createUser(adminUser);
            }
        };
    }
}
