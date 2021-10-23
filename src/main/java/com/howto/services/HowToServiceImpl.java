package com.howto.services;

import com.howto.exceptions.ResourceNotFoundException;
import com.howto.models.HowTo;
import com.howto.models.Step;
import com.howto.models.User;
import com.howto.repository.HowToRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "howToService")
public class HowToServiceImpl implements HowToService{
   @Autowired
   private HowToRepository howToRepository;

   @Autowired
   private UserService userService;

//   @Autowired
//   private StepService stepService;

    @Override
    public List<HowTo> findAll() {
        List<HowTo> list = new ArrayList<>();
        howToRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<HowTo> findAllHowTosByCategory(String category) {
        List<HowTo> list = new ArrayList<>();
        howToRepository.findByCategory(category.toLowerCase()).iterator().forEachRemaining(list::add);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("HowTo category " + category + " is empty or does not exist");
        }
        return list;
    }

    @Override
    public HowTo findByHowToId(long id) {
        return howToRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Howto with id " + id + " not found!"));
    }

    @Override
    public HowTo findByName(String name) {
        HowTo howTo = howToRepository.findByName(name.toLowerCase().replaceAll("_", " "));
        if (howTo == null) {
            throw new ResourceNotFoundException("HowTo named " + name + " not found!");
        }
        return howTo;
    }

    @Override
    public List<HowTo> findByNameContaining(String name) {
        return howToRepository.findByNameContainingIgnoreCase(name.toLowerCase().replaceAll("_", " "));
    }

    @Transactional
    @Override
    public void delete(long id) {
        howToRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("How to id " + id + " not found!"));
        howToRepository.deleteById(id);
    }

    @Transactional
    @Override
    public HowTo save(long userid, HowTo howTo) {
        HowTo newHowTo = new HowTo();
        if (howTo.getHowtoid() != 0) {
            howToRepository.findById(howTo.getHowtoid())
                    .orElseThrow(() -> new ResourceNotFoundException("HowTo id " + howTo.getHowtoid() + " not found!"));
            newHowTo.setHowtoid(howTo.getHowtoid());
        }

        newHowTo.setCategory(howTo.getCategory());
        newHowTo.setComplexity(howTo.getComplexity());
        newHowTo.setDescription(howTo.getDescription());
        newHowTo.setName(howTo.getName());

        User userInfo = userService.findUserById(userid);
        newHowTo.setUser(userInfo);

//        newHowTo.setSteps(howTo.getSteps());

//        newHowTo.getSteps().clear();
//        for (Step st : newHowTo.getSteps()) {
//            Step addStep = stepService.
//
//        }


        return howToRepository.save(newHowTo);
    }

    @Transactional
    @Override
    public HowTo update(HowTo howTo, long id) {
        HowTo newHowto = findByHowToId(id);
        if (newHowto.getName() == null ) {
            throw new ResourceNotFoundException("HowTo with id " + id + " does not exist!");
        }
        if (howTo.getName() != null) {
            newHowto.setName(howTo.getName());
        }
        if (howTo.getUser() != null){
            newHowto.setUser(howTo.getUser());
        }
        if (howTo.getDescription() != null) {
            newHowto.setDescription(howTo.getDescription());
        }
        if (howTo.getComplexity() != null) {
            newHowto.setComplexity(howTo.getComplexity());
        }
        if (howTo.getCategory() != null) {
            newHowto.setCategory(howTo.getCategory());
        }
//        if (howTo.getSteps().size() > 0) {
//            for (Step st: newHowto.getSteps()){
//                Step addStep = stepSerivce
//            }
//        }
//        newHowto.setSteps(howTo.getSteps());

        return howToRepository.save(newHowto);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteAll() {
        howToRepository.deleteAll();
    }
}
