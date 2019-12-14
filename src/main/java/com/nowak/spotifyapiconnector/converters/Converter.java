package com.nowak.spotifyapiconnector.converters;



import com.nowak.spotifyapiconnector.database.entities.UserDataEntity;

import com.nowak.spotifyapiconnector.json_objects.pojos.users.UserDataModel;

public class Converter {

    public UserDataEntity convert(UserDataModel userDataModel) {
        String imageURI = "";
        UserDataEntity userDataEntity = null;
        if (userDataModel.getImages().size() != 0) {
            imageURI = userDataModel.getImages().get(0).toString();
        }
        try {
            userDataEntity =
                    new UserDataEntity(userDataModel.getId(),
                            userDataModel.getDisplayName(),
                            userDataModel.getHref(),
                            userDataModel.getType(),
                            userDataModel.getUri(),
                            imageURI,
                            userDataModel.getFollowers().getTotal(),
                            userDataModel.getExternalUrls().getSpotify());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDataEntity;
    }

}
