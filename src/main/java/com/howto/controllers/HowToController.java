package com.howto.controllers;


import com.howto.models.HowTo;
import com.howto.services.HowToService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/howtos")
public class HowToController {
    @Autowired
    private HowToService howToService;

    @GetMapping(value = "/howtos", produces = "application/json")
    public ResponseEntity<?> listAllHowTos(){
        List<HowTo> allHowTos = howToService.findAll();
        return new ResponseEntity<>(allHowTos, HttpStatus.OK);
    }

    @GetMapping(value = "/category/{category}", produces = "application/json")
    public ResponseEntity<?> getByCategory(@PathVariable String category){
     List<HowTo> howToList = howToService.findAllHowTosByCategory(category);
     return new ResponseEntity<>(howToList, HttpStatus.OK);
    }

    @GetMapping(value = "/howto/{name}", produces = "application/json")
    public ResponseEntity<?> getHowToByName(@PathVariable String name) {
        HowTo howto = howToService.findByName(name);
        return new ResponseEntity<>(howto, HttpStatus.OK);
    }
}
