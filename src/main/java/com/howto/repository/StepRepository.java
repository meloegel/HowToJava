package com.howto.repository;

import com.howto.models.HowTo;
import com.howto.models.Step;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StepRepository extends CrudRepository<Step, Long> {
    List<Step> findByHowto(HowTo howto);
}
