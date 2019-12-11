package com.nowak.spotifyapiconnector.controllers;

import com.nowak.spotifyapiconnector.json_objects.pojos.users.UserDataModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import java.security.Principal;

@Controller
public class UserController {

    private RestTemplate restTemplate;
    private    HttpHeaders httpHeaders;

    @PostConstruct
    public void init(){
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
    }

    @GetMapping("/users/{user_id}")
    public String getUserData(OAuth2Authentication details, @PathVariable("user_id") String id, Principal principal, Model model)
    {

        String token = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        httpHeaders.add("Authorization", "Bearer " + token);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        String SPOTIFY_URI = "https://api.spotify.com/v1/users/";
        ResponseEntity<String> result = restTemplate.exchange(SPOTIFY_URI + id, HttpMethod.GET, httpEntity, String.class);
        ResponseEntity<UserDataModel> userDataModel = restTemplate.exchange(SPOTIFY_URI + id, HttpMethod.GET, httpEntity, UserDataModel.class);

        System.out.println(userDataModel.getBody().getDisplayName()+"   "+ userDataModel.getBody().getFollowers().getTotal());

        UserDataModel udm =userDataModel.getBody();

        model.addAttribute("userData", udm.getDisplayName());
        //return result.getBody();
        return "main";
    }

    @GetMapping("/user")
    public Principal getCurrentUserData(Principal principal, OAuth2Authentication details) {
        String token = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        System.out.println(token);
        return principal;
    }
}
