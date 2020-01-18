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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        if(id=="?" || id=="" || id.equals("?") || id.equals("")){
            model.addAttribute("msg", "Fill the gap!");
            return "main";
        }
        String token = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        httpHeaders.add("Authorization", "Bearer " + token);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<UserDataModel> result = restTemplate.exchange(SPOTIFY_URI + id,
                                        HttpMethod.GET, httpEntity, UserDataModel.class);
        String imguri ="none";
        if(result.getBody().getImages().size()!=0){
            imguri= result.getBody().getImages().get(0).getClass().toString();
        }
        model.addAttribute("name",result.getBody().getDisplayName());
        model.addAttribute("id",result.getBody().getId());
        model.addAttribute("href",result.getBody().getHref());
        model.addAttribute("uri",result.getBody().getUri());
        model.addAttribute("followers",result.getBody().getFollowers().getTotal());
        model.addAttribute("type",result.getBody().getType());
        model.addAttribute("externals",result.getBody().getExternalUrls().getSpotify());
        model.addAttribute("images",imguri);
        return "main";
    }

    @GetMapping("/save/users/{user_id}")
    public String saveUser(OAuth2Authentication details, @PathVariable("user_id") String id,Model model){
        if(id.equals("?")){
            model.addAttribute("msg", "Fill the gap!");
            return "main";
        }
        String token=null;
        try {
             token = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        }
        catch(Exception e){
            model.addAttribute("msg", "Invalid token");
        }
        httpHeaders.add("Authorization", "Bearer " + token);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<UserDataModel> userDataModel = null;
        try {
           userDataModel = restTemplate.exchange(SPOTIFY_URI + id,
                    HttpMethod.GET, httpEntity,
                    UserDataModel.class);
        }
        catch(Exception e){
            model.addAttribute("msg", "Cannot find object!");

        }
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
        model.addAttribute("userName", id);
            return "main";

    }

    @GetMapping("/user")
    public Principal getCurrentUserData(Principal principal, OAuth2Authentication details) {
        String token = ((OAuth2AuthenticationDetails) details.getDetails()).getTokenValue();
        System.out.println(token);
        return principal;
    }
    @GetMapping("/database")
    public String showDBPage(Model model){
        List<UserDataEntity> userDataEntities = new ArrayList<>();
        userDataEntities=spotifyDataService.findAll();
        model.addAttribute("userDataEntities",userDataEntities);
        return "database";
    }
}
