package com.nowak.spotifyapiconnector.controllers;

import com.nowak.spotifyapiconnector.json_objects.pojos.artists.ArtistsDataModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Controller
public class ArtistsController {
    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private String HTTP_ADDRESS = "https://api.spotify.com/v1/artists/";
    private String TYPE = "&type=track&market=US&limit=1";


    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
    }
    @GetMapping("/artists/{artist_id}")
    public String getArtistData(OAuth2Authentication authentication, @PathVariable("artist_id") String id, Model model){
        String token = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
        httpHeaders.add("Authorization ","Bearer "+token);
        HttpEntity httpEntity = new HttpEntity((httpHeaders));

        ResponseEntity<ArtistsDataModel> responseEntity = restTemplate.exchange(HTTP_ADDRESS+id, HttpMethod.GET,httpEntity,ArtistsDataModel.class);
        ArtistsDataModel adm = responseEntity.getBody();
        System.out.println(responseEntity.getBody().getName());
        model.addAttribute("artistData",adm.getName());
        return "main";

    }
}
