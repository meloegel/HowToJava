package com.howto.controllers;


import com.howto.models.Useremail;
import com.howto.services.UseremailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

// The entry point for client to access user, email combinations
@RestController
@RequestMapping("/useremails")
public class UseremailController {

    @Autowired
    UseremailService useremailService;

    // List of all users emails
    // Link: http://localhost:2019/useremails/useremails
    @GetMapping(value = "/useremails", produces = "application/json")
    public ResponseEntity<?> listallUseremails() {
        List<Useremail> allUserEmails = useremailService.findAll();
        return new ResponseEntity<>(allUserEmails, HttpStatus.OK);
    }

    // Return the user email combination referenced by the given primary key
    // Link: http://localhost:2019/useremails/useremail/8
    // @param useremailId the primary key of the user email combination you seek
    @GetMapping(value = "/useremail/{useremailId}", produces = "application/json")
    public ResponseEntity<?> getUserEmailById(@PathVariable Long useremailId) {
        Useremail useremail = useremailService.findUseremailById(useremailId);
        return new ResponseEntity<>(useremail, HttpStatus.OK);
    }

    // Removes the given user email combination
    // Link: http://localhost:2019/useremails/useremail/8
    // @param useremailid the primary key of the user email combination you wish to remove
    @DeleteMapping(value = "/useremail/{useremailid}")
    public ResponseEntity<?> deleteUserEmailById(@PathVariable long useremailid) {
        useremailService.delete(useremailid);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    // Change the email associated with the given user email combination
    // Link: http://localhost:2019/useremails/useremail/9/email/favbun@hops.local
    // @param useremailid - The primary key of the user email combination you wish to change
    // @param emailaddress - The new email (String)
    @PutMapping(value ="/useremail/{useremailid}/email/{emailaddress}")
    public ResponseEntity<?> updateUserEmail(@PathVariable long useremailid, @PathVariable String emailaddress) {
        useremailService.update(useremailid, emailaddress);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    // Adds a new user email combination
    // @param userid - The user id of the new user email combination
    // @param emailaddress - the email address of the new user email combination
    // Link: http://localhost:2019/user/9/email/newEmail@hops.local
    @PostMapping(value = "/user/{userid}/email/{emailaddress}")
    public ResponseEntity<?> addNewUserEmail(@PathVariable long userid, @PathVariable String emailaddress)
            throws URISyntaxException {
        Useremail newUserEmail = useremailService.save(userid, emailaddress);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserEmailURI = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/useremails/useremail/{useremailid}")
                .buildAndExpand(newUserEmail.getUseremailid()).toUri();
        responseHeaders.setLocation(newUserEmailURI);

        return new ResponseEntity<>("Success", responseHeaders, HttpStatus.CREATED);
    }
}
