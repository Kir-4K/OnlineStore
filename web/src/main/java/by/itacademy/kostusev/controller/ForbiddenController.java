package by.itacademy.kostusev.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static by.itacademy.kostusev.path.UrlPath.FORBIDDEN;
import static by.itacademy.kostusev.path.ViewPath.FORBIDDEN_VIEW;

@Controller
@RequestMapping(FORBIDDEN)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ForbiddenController {

    @GetMapping
    public String forbidden() {
        return FORBIDDEN_VIEW;
    }
}
