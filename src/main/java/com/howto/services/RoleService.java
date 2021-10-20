package com.howto.services;


import com.howto.models.Role;

import java.util.List;

public interface RoleService {
    // Returns a list of all Role obj
    List<Role> findAll();

    // Return first Role Matching given primary key
    Role findRoleById(long id);

    // Given a complete Role object, saved that Role object in the database.
    // If a primary key is provided, the record is completely replaced
    // If no primary key is provided, one is automatically generated and the record is added to the database.
    // Note that Users are not added to Roles through this process
    Role save(Role role);

    // Find the first Role object matching the given name
    Role findByName(String name);

    // Deletes all record and their associated records from the database
    public void deleteAll();

    Role update(long id,Role role);
}
