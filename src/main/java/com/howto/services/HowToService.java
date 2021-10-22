package com.howto.services;

import com.howto.models.HowTo;

import java.util.List;

public interface HowToService {
    List<HowTo> findAll();

    List<HowTo> findAllHowTosByCategory(String category);

    HowTo findByName(String name);

    List<HowTo> findByNameContaining(String name);

    void delete(long id);

    HowTo save(HowTo howTo);

    HowTo update(HowTo howTo,long id);

    public void deleteAll();

}
