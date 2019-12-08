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

@RestController
public class AlbumsData {

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
    public String getTracksByArtistId(@PathVariable("artist_id") String artist_id, OAuth2Authentication authentication) {
        String token = ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenValue();
        httpHeaders.add("Authorization", "Bearer " + token);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<String> result = restTemplate.exchange(HTTP_ADDRESS + artist_id + TYPE,
                HttpMethod.GET, httpEntity, String.class);
        return result.getBody();
    }
}
