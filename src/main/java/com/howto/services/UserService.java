package com.howto.services;

import com.howto.models.User;

import java.util.List;

public interface UserService {

    // Returns a list of all the Users
    List<User> findAll();

    //A list of all users whose username contains the given substring
    List<User> findByNameContaining(String username);

    //Returns the user with the given primary key.
    User findUserById(long id);

    // Returns the user with the given name
    User findByName(String name);

    //Deletes the user record and its useremail items from the database based off of the provided primary key
    void delete(long id);

    // Given a complete user object, saves that user object in the database.
    // If a primary key is provided, the record is completely replaced
    // If no primary key is provided, one is automatically generated and the record is added to the database.
    User save(User user);


    // Updates the provided fields in the user record referenced by the primary key.
    // Regarding Role and Useremail items, this process only allows adding those. Deleting and editing those lists
    //is done through a separate endpoint.
    User update(User user, long id);

    public void deleteAll();
}
