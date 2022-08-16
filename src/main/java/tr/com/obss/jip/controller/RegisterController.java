package tr.com.obss.jip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/register")
public class RegisterController {
    private final UserService userService;

    @PostMapping("/")
    public Boolean createNewUser(@RequestBody @Valid CreateNewUser createNewUser) {
        userService.createNewUser(createNewUser);
        return Boolean.TRUE;
    }

    @GetMapping("/verify")
    public String verifyToken(@RequestParam("token") String token) {
        return userService.verifyToken(token);
    }
}
