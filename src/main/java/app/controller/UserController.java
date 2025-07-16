package app.controller;

import annotation.Autowired;
import annotation.Controller;
import app.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    public UserController() {

    }

    public void greet(){
        System.out.println("Hello from the user controller");
    }

    public void getUserGreeting(){
        System.out.println(userService.greet());
    }

    @Override
    public String toString() {
        return "UserController";
    }
}
