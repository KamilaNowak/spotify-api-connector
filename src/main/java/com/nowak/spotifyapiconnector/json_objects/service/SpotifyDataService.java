package com.nowak.spotifyapiconnector.json_objects.service;

import com.nowak.spotifyapiconnector.database.entities.UserDataEntity;
import com.nowak.spotifyapiconnector.json_objects.pojos.users.UserDataModel;

public interface SpotifyDataService {

    void saveUserData(UserDataEntity entity);
}
