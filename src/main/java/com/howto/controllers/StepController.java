package com.howto.controllers;

import com.howto.models.HowTo;
import com.howto.models.Step;
import com.howto.services.HowToService;
import com.howto.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/steps")
public class StepController {
    @Autowired
    private StepService stepService;

    @Autowired
    private HowToService howToService;

    @GetMapping(value = "/steps/{howtoid}", produces = "application/json")
    public ResponseEntity<?> listAllStepsForHowto(@PathVariable long howtoid){
        HowTo howto = howToService.findByHowToId(howtoid);
        List<Step> allSteps = stepService.findAllStepsForHowTo(howto);
        return new ResponseEntity<>(allSteps, HttpStatus.OK);
    }
}
