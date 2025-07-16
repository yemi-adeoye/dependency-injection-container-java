package app.service;

import annotation.Service;

@Service
public class UserService {

    public UserService(){

    }

    @Override
    public String toString() {
        return "UserService";
    }

    public String greet() {
        return "Greetings from the UserService";
    }
}
