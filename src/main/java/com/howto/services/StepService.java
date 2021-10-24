package com.howto.services;

import com.howto.models.Step;

import java.util.List;

public interface StepService {
    List<Step> findAllStepsForHowTo(long howtoid);

    Step findStepById(long stepid);

    void delete(long id);

    Step save(Step step, long howtoid);

    Step update(Step step, long stepid);

    public void deleteAll();
}