package com.nowak.spotifyapiconnector.controllers;

import com.nowak.spotifyapiconnector.json_objects.pojos.tracks.TrackDataModel;
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
public class TracksController {

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private String HTTP_ADDRESS = "https://api.spotify.com/v1/search?q=";
    private String TYPE = "&type=track&market=US&limit=1";


    @PostConstruct
    public void init() {
        restTemplate = new RestTemplate();
        httpHeaders = new HttpHeaders();
    }

    @GetMapping("/tracks/{artist_id}")
    public String getTracksByArtistId(@PathVariable("artist_id") String artist_id, OAuth2Authentication authentication, Model model) {
        String token = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
        httpHeaders.add("Authorization", "Bearer " + token);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<TrackDataModel> result = restTemplate.exchange(HTTP_ADDRESS + artist_id + TYPE,
                HttpMethod.GET, httpEntity, TrackDataModel.class);
        //return result.getBody();
        TrackDataModel tdm = result.getBody();
        System.out.println(result.getBody().getTracks().getHref()+" FOR "+artist_id);
        model.addAttribute("tracksData", tdm.getTracks().getHref());
        return "main";
    }

}
