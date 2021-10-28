package com.howto.controllers;

import com.howto.models.HowTo;
import com.howto.models.Step;
import com.howto.services.HowToService;
import com.howto.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/steps")
public class StepController {
    @Autowired
    private StepService stepService;

    @Autowired
    private HowToService howToService;

    // Returns a list of all steps for howto, given valid howto id
    // Link: http://localhost:2019/steps/steps/5
    // @param howtoid - the id of the howto that you wish to retrieve steps for
    @GetMapping(value = "/steps/{howtoid}", produces = "application/json")
    public ResponseEntity<?> listAllStepsForHowto(@PathVariable long howtoid){
        HowTo howto = howToService.findByHowToId(howtoid);
        List<Step> allSteps = stepService.findAllStepsForHowTo(howto);
        return new ResponseEntity<>(allSteps, HttpStatus.OK);
    }

    // Returns a specific step based of valid id given
    // Link: http://localhost:2019/steps/step/20
    // @param id - the id of the step that you wish to search for
    @GetMapping(value = "/step/{id}", produces = "application/json")
    public ResponseEntity<?> getStepById(@PathVariable long id) {
        Step step = stepService.findStepById(id);
        return new ResponseEntity<>(step, HttpStatus.OK);
    }

    // Given a complete Step Object, create a new Step record
    // Link: http://localhost:2019/steps/step/19
    // @param newStep - A complete new step (step min: step, howtoid)
    @PostMapping(value = "/step/{howtoid}", consumes = "application/json")
    public ResponseEntity<?> addNewStep(@PathVariable long howtoid, @Valid @RequestBody Step newStep) throws URISyntaxException {
        newStep.setStepid(0);
        newStep = stepService.save(howtoid, newStep);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newStepURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/step/{stepid}")
                .buildAndExpand(newStep.getStepid())
                .toUri();
        responseHeaders.setLocation(newStepURI);
        return new ResponseEntity<>(newStep, responseHeaders, HttpStatus.CREATED);
    }

    // Deletes a given step
    // Link: http://localhost:2019/steps/step/20
    // @param id - the id of the step that you wish to delete
    @DeleteMapping(value = "/step/{id}")
    public ResponseEntity<?> deleteStepById(@PathVariable long id) {
        stepService.delete(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
