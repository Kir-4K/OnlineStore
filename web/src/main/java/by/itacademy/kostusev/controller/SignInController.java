package by.itacademy.kostusev.controller;

import by.itacademy.kostusev.path.UrlPath;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static by.itacademy.kostusev.path.ViewPath.SIGNIN_VIEW;

@Controller
@RequestMapping(UrlPath.SIGNIN_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SignInController {

    @GetMapping
    public String signIn() {
        return SIGNIN_VIEW;
    }
}
