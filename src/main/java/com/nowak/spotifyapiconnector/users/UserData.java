package com.nowak.spotifyapiconnector.users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserData {

    @GetMapping("/user")
    public Principal getUserData(Principal principal) {
        return principal;
    }

}
