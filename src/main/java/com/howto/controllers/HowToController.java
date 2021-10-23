package com.howto.controllers;


import com.howto.models.HowTo;
import com.howto.services.HowToService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;



@RestController
@RequestMapping("/howtos")
public class HowToController {
    @Autowired
    private HowToService howToService;

    // Returns a list of all howtos
    // Link: http://localhost:2019/howtos/howtos
    @GetMapping(value = "/howtos", produces = "application/json")
    public ResponseEntity<?> listAllHowTos(){
        List<HowTo> allHowTos = howToService.findAll();
        return new ResponseEntity<>(allHowTos, HttpStatus.OK);
    }

    // Returns a list of all howtos from a category given a valid category
    // Link: http://localhost:2019/howtos/category/cooking
    // @param category - the category you wish to search for
    @GetMapping(value = "/category/{category}", produces = "application/json")
    public ResponseEntity<?> getByCategory(@PathVariable String category){
     List<HowTo> howToList = howToService.findAllHowTosByCategory(category);
     return new ResponseEntity<>(howToList, HttpStatus.OK);
    }

    // Returns a specific howto based of valid name given
    // ** Use underscores (_) as spaces for more than one word names **
    // Link: http://localhost:2019/howtos/howto/cook_fish
    // @param name - the name of the howto that you wish to search for
    @GetMapping(value = "/howto/{name}", produces = "application/json")
    public ResponseEntity<?> getHowToByName(@PathVariable String name) {
        HowTo howto = howToService.findByName(name);
        return new ResponseEntity<>(howto, HttpStatus.OK);
    }

    // Returns a list of howtos whose name contains the given substring
    // ** Use underscores (_) as spaces for more than one word names **
    // Link: http://localhost:2019/howtos/howtos/like/cook_fish
    // @param name - the name or part of name of howto you wish to search for
    @GetMapping(value = "/howtos/like/{name}", produces = "application/json")
    public ResponseEntity<?> getHowToLikeName(@PathVariable String name) {
        List<HowTo> howTos = howToService.findByNameContaining(name);
        return new ResponseEntity<>(howTos, HttpStatus.OK);
    }

    // Deletes a given howto
    // Link: http://localhost:2019/howtos/howto/15
    // @param id - The primary key of the howto you wish to delete
    @DeleteMapping(value = "/howto/{id}")
    public ResponseEntity<?> deleteHotToById(@PathVariable long id) {
        howToService.delete(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    // Given a complete Howto Object, create a new Howto record
    // Link: http://localhost:2019/howtos/{userid}/howto
    // @param newHowTo - A complete new howto (howTo min: name, description, category, userid)
    @PostMapping(value = "/{userid}/howto", consumes = "application/json")
    public ResponseEntity<?> addNewHowTo(@PathVariable long userid, @RequestBody HowTo newHowTo) throws URISyntaxException  {
        newHowTo.setHowtoid(0);
        newHowTo = howToService.save(userid, newHowTo);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/howtos/howto/{howtoid}")
                .buildAndExpand(newHowTo.getHowtoid())
                .toUri();
        responseHeaders.setLocation(newUserURI);
        return new ResponseEntity<>(newHowTo, responseHeaders, HttpStatus.CREATED);
    }
}
