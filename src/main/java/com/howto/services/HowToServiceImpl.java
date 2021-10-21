package com.howto.services;

import com.howto.exceptions.ResourceNotFoundException;
import com.howto.models.HowTo;
import com.howto.repository.HowToRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "howToService")
public class HowToServiceImpl implements HowToService{
   @Autowired
   private HowToRepository howToRepository;


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
    public HowTo findByName(String name) {
        HowTo howTo = howToRepository.findByName(name.toLowerCase());
        if (howTo == null) {
            throw new ResourceNotFoundException("HowTo name " + name + " not found!");
        }
        return howTo;
    }

    @Override
    public List<HowTo> findByNameContaining(String name) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public HowTo update(HowTo howTo, long id) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
