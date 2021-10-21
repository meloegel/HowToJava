package com.howto.repository;

import com.howto.models.HowTo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HowToRepository extends CrudRepository<HowTo, Long> {
    HowTo findByName(String name);

    List<HowTo> findByCategory(String category);

}
