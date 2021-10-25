package com.howto.services;

import com.howto.models.HowTo;
import com.howto.models.Step;
import com.howto.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class StepServiceImpl implements StepService {
    @Autowired
    private StepRepository stepRepository;

    @Override
    public List<Step> findAllStepsForHowTo(HowTo howto) {
        List<Step> list = new ArrayList<>();
        stepRepository.findByHowto(howto).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Step findStepById(long stepid) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Step save(Step step, long howtoid) {
        return null;
    }

    @Override
    public Step update(Step step, long stepid) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
