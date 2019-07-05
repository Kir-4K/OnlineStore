package by.itacademy.kostusev.controller;

import by.itacademy.kostusev.dto.UserDto;
import by.itacademy.kostusev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static by.itacademy.kostusev.path.UrlPath.REDIRECT;
import static by.itacademy.kostusev.path.UrlPath.SIGNIN_URL;
import static by.itacademy.kostusev.path.UrlPath.SIGNUP_URL;
import static by.itacademy.kostusev.path.ViewPath.SIGNUP_VIEW;

@Controller
@RequestMapping(SIGNUP_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SignUpController {

    private final UserService userService;

    @GetMapping
    public String signUp() {
        return SIGNUP_VIEW;
    }

    @PostMapping
    public String registerNewAccount(UserDto user) {
        userService.saveOrUpdate(user);
        return REDIRECT + SIGNIN_URL;
    }
}
