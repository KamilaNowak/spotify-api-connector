package com.nowak.spotifyapiconnector.json_objects.service;

import com.nowak.spotifyapiconnector.database.entities.UserDataEntity;
import com.nowak.spotifyapiconnector.json_objects.pojos.users.UserDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepo extends JpaRepository<UserDataEntity,String> {
}
