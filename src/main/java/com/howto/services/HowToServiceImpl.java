package com.howto.services;

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
    public List<HowTo> findAllHowTosForUser(long id) {
        return null;
    }

    @Override
    public List<HowTo> findAllHowTosByCategory(String category) {
        return null;
    }

    @Override
    public HowTo findByName(String name) {
        return null;
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
