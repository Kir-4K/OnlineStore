package by.itacademy.kostusev.controller;

import by.itacademy.kostusev.dto.UserDto;
import by.itacademy.kostusev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static by.itacademy.kostusev.path.UrlPath.USER_URL;
import static by.itacademy.kostusev.path.ViewPath.USER_VIEW;
import static by.itacademy.kostusev.util.AttributeName.USER;
import static by.itacademy.kostusev.util.AttributeName.USERS;

@Controller
@RequestMapping(USER_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping
    public String getUsers(Model model) {
        List<UserDto> users = userService.findAll();
        model.addAttribute(USERS, users);
        return USER_VIEW;
    }

    @GetMapping("/{id}")
    public String getUser(Model model, @PathVariable("id") Long id) {
        UserDto user = userService.findById(id);
        model.addAttribute(USER, user);
        return USER_VIEW;
    }
}
