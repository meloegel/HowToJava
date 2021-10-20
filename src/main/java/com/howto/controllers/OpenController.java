package com.howto.controllers;


import com.howto.models.User;
import com.howto.models.UserMinimum;
import com.howto.models.UserRoles;
import com.howto.services.RoleService;
import com.howto.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// The class allows access to endpoints that are open to all users regardless of authentication status.
// Its most important function is to allow a person to create their own username
@RestController
public class OpenController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    // This endpoint always anyone to create an account with the default role of USER.
    //      That role is hardcoded in this method.
    //  @param httpServletRequest - The request that comes in for creating the new user
    //  @param newminuser - A special minimum set of data that is needed to create a new user
    @PostMapping(value = "/createNewUser", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addSelf(HttpServletRequest httpServletRequest,
                                     @Valid @RequestBody UserMinimum newMinUser)
            throws URISyntaxException {

        User newUser = new User();

        newUser.setUsername(newMinUser.getUsername());
        newUser.setPassword(newMinUser.getPassword());
        newUser.setPrimaryemail(newMinUser.getPrimaryemail());

        Set<UserRoles> newRoles = new HashSet<>();
        newRoles.add(new UserRoles(newUser, roleService.findByName("user")));
        newUser.setRoles(newRoles);

        newUser = userService.save(newUser);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromUriString(httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/users/user/{userId}")
                .buildAndExpand(newUser.getUserid()).toUri();
        responseHeaders.setLocation(newUserURI);

        RestTemplate restTemplate = new RestTemplate();
        String requestURI = "http://localhost" + ":" + httpServletRequest.getLocalPort() + "/login";

        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(acceptableMediaTypes);
        headers.setBasicAuth(System.getenv("OAUTHCLIENTID"), System.getenv("OAUTHCLIENTSECRET"));

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("scope", "read write trust");
        map.add("username", newMinUser.getUsername());
        map.add("password", newMinUser.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        String theToken = restTemplate.postForObject(requestURI, request, String.class);

        return new ResponseEntity<>(theToken, responseHeaders, HttpStatus.CREATED);
    }

    // Prevents no favicon.ico warning from appearing in the logs.
    //       @ApiIgnore tells Swagger to ignore documenting this as an endpoint.
    @ApiIgnore
    @GetMapping("favicon.ico")
    public void returnNoFavicon() {}
}
