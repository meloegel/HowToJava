package com.howto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;


@Controller
public class LogoutController {

    @Autowired
    private TokenStore tokenStore;

    // Removes the token for the signed on user. The signed user will lose access to the application.
    //       They would have to sign on again.
    // Link: http://localhost:2019/logout
    // @param request - The Http request from which we find the authorization header
    //        which includes the token to be removed
    @GetMapping(value = {"/oauth/revoke-token", "/logout"}, produces = "application/json")
    public ResponseEntity<?> logoutSelf(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
