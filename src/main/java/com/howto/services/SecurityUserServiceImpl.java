package com.howto.services;


import com.howto.exceptions.ResourceNotFoundException;
import com.howto.models.User;
import com.howto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// This implements User Details Service that allows us to authenticate a user.
@Service(value = "securityUserService")
public class SecurityUserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    // Verifies that the user is correct and if so creates the authenticated user
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String uname) throws ResourceNotFoundException {
        User user = userRepository.findByUsername(uname.toLowerCase());
        if (user == null){
            throw new ResourceNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthority());
    }
}
