package com.howto.services;

import com.howto.models.Useremail;

import java.util.List;

public interface UseremailService {

    //Returns a list of all users and their emails
    List<Useremail> findAll();

    //Returns the user email combination associated with the given id
    Useremail findUseremailById(long id);

    // Remove the user email combination referenced by the given id
    void delete(long id);

    // Replaces the email of the user email combination you seek
    Useremail update(long useremailid, String emailaddress);

    //Add a new User Email combination
    Useremail save(long userid, String emailaddress);
}
