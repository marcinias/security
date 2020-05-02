package com.example.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@RestController
public class Api {

    private Logger logger = LoggerFactory.getLogger(Api.class);

    private Map<String, Integer> users = new HashMap<>();
    private int counter = 0;

    public Api() {
        users.put("Marcin", counter);
        users.put("Paweł", counter);
    }


    @EventListener
    private void getUsername(AuthenticationSuccessEvent event) {

        String name = ((User) event.getAuthentication().getPrincipal()).getUsername();

        switch (name) {
            case "Marcin":
                users.put("Marcin", ++counter);
                break;
            case "Paweł":
                users.put("Paweł", ++counter);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
    }


    @GetMapping("/getAll")
    public String getAllUser(Principal principal) {
        return "getAll: " + principal.getName() + " ile razy się zalogowaleś " + users.get(principal.getName());

    }


    @GetMapping("/getUser")
    public String getUser(Principal principal) {

        return "Hello User: " + principal.getName() + " ile razy się zalogowaleś " + users.get(principal.getName());

    }

    @GetMapping("/getAdmin")
    public String getAdmin(Principal principal) {

        return "Hello Admin: " + principal.getName() + " ile razy się zalogowaleś " + users.get(principal.getName());

    }

    @GetMapping("/getLogout")
    public String getLogout() {

        return "You have been logout papa! ";

    }

}
