package app.controller;

import annotation.Autowired;
import annotation.Controller;
import app.service.UserService;

@Controller
public class UserController {
    @Autowired
    private final UserService UserService;

    public UserController(UserService userService) {
        UserService = userService;
    }
}
