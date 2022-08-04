package tr.com.obss.jip.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.service.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public Boolean createNewUser(@RequestBody @Valid CreateNewUser createNewUser) {
        userService.createNewUser(createNewUser);
        return true;
    }
}
