package com.nowak.spotifyapiconnector.json_objects.service;

import com.nowak.spotifyapiconnector.database.entities.UserDataEntity;
import com.nowak.spotifyapiconnector.json_objects.pojos.users.UserDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class SpotifyDataServiceImpl implements SpotifyDataService {

   @Autowired
   UserDataRepo userDataRepo;

    @Override
    public void saveUserData(UserDataEntity entity) {
        userDataRepo.save(entity);
    }
}
