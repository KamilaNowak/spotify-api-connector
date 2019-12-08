package com.nowak.spotifyapiconnector.users;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.security.Principal;

@RestController
public class UserData {

    private RestTemplate restTemplate;
    private    HttpHeaders httpHeaders;

    @PostConstruct
    public void init(){
         restTemplate = new RestTemplate();
         httpHeaders = new HttpHeaders();
    }
    @GetMapping("/users/{user_id}")
    public String getUserData(OAuth2Authentication details, @PathVariable("user_id") String id, Principal principal) {

        String token = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        httpHeaders.add("Authorization", "Bearer " + token);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        String SPOTIFY_URI = "https://api.spotify.com/v1/users/";
        ResponseEntity<String> result = restTemplate.exchange(SPOTIFY_URI + id, HttpMethod.GET, httpEntity, String.class);
        return result.getBody();
    }

    @GetMapping("/user")
    public Principal getCurrentUserData(Principal principal, OAuth2Authentication details) {
        String token = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        System.out.println(token);
        return principal;
    }

}
