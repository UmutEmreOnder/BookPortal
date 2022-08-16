package tr.com.obss.jip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.service.RegisterService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/register")
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping("/")
    public Boolean createNewUser(@RequestBody @Valid CreateNewUser createNewUser) {
        registerService.createNewUser(createNewUser);
        return Boolean.TRUE;
    }

    @GetMapping("/verify")
    public Boolean verifyToken(@RequestParam("token") String token) {
        return registerService.verifyToken(token);
    }

    @PostMapping("/verify")
    public Boolean sendToken(@RequestParam("email") String email) {
        registerService.sendNewEmail(email);
        return Boolean.TRUE;
    }
}
