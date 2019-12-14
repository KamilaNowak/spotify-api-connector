package com.nowak.spotifyapiconnector.controllers;

import com.nowak.spotifyapiconnector.converters.Converter;
import com.nowak.spotifyapiconnector.database.entities.UserDataEntity;
import com.nowak.spotifyapiconnector.json_objects.pojos.users.UserDataModel;
import com.nowak.spotifyapiconnector.json_objects.service.SpotifyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
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

    String SPOTIFY_URI = "https://api.spotify.com/v1/users/";

    @Autowired
    private SpotifyDataService spotifyDataService;

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private Converter converter = new Converter();
    private UserDataModel userDataModel;

    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
        userDataModel= new UserDataModel();
    }

    @GetMapping("/users/{user_id}")
    public String getUserData(OAuth2Authentication details, @PathVariable("user_id") String id, Principal principal, Model model) {

        String token = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        httpHeaders.add("Authorization", "Bearer " + token);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<UserDataModel> result = restTemplate.exchange(SPOTIFY_URI + id,
                                        HttpMethod.GET, httpEntity, UserDataModel.class);
        String imguri ="none";
        if(result.getBody().getImages().size()!=0){
            imguri= result.getBody().getImages().get(0).getClass().toString();
        }
        model.addAttribute("userData","Name : "
                +result.getBody().getDisplayName()+"\nId : "
                +result.getBody().getId()+"\nHref : "
                +result.getBody().getHref()+"\nType :  "
                +result.getBody().getType()+"\nUri : "
                +result.getBody().getUri()+"\nImages : "
                +imguri+"\nFollowers : "
                +result.getBody().getFollowers().getTotal()+"\nExternals : "
                +result.getBody().getExternalUrls().getSpotify());
        return "main";
    }

    @GetMapping("/save/users/{user_id}")
    public String saveUser(OAuth2Authentication details, @PathVariable("user_id") String id,Model model){
        String token = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        httpHeaders.add("Authorization", "Bearer " + token);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<UserDataModel> userDataModel = restTemplate.exchange(SPOTIFY_URI + id,
                HttpMethod.GET, httpEntity,
                UserDataModel.class);
        try {
            UserDataModel modelToSave = userDataModel.getBody();
            UserDataEntity userDataEntity = converter.convert(modelToSave);
            spotifyDataService.saveUserData(userDataEntity);
            model.addAttribute("msg", "Object saved");
        }
        catch(Exception e){
            e.printStackTrace();
            model.addAttribute("msg", "Something went wrong.");
        }
        return "main";
    }

    @GetMapping("/user")
    public Principal getCurrentUserData(Principal principal, OAuth2Authentication details) {
        String token = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        System.out.println(token);
        return principal;
    }
}
