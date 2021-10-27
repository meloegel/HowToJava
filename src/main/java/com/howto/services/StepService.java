package com.howto.services;

import com.howto.models.HowTo;
import com.howto.models.Step;

import java.util.List;

public interface StepService {
    List<Step> findAllStepsForHowTo(HowTo howto);

    Step findStepById(long stepid);

    void delete(long id);

    Step save( long howtoid, Step step);

    Step update(Step step, long stepid);

    public void deleteAll();
}
