package com.howto.services;

import com.howto.models.HowTo;
import com.howto.models.Step;
import com.howto.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Transactional
@Service(value = "stepService")
public class StepServiceImpl implements StepService {
    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private HowToService howToService;

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

    @Transactional
    @Override
    public Step save(long howtoid, Step step) {
        HowTo howto = howToService.findByHowToId(howtoid);
        Step newStep = new Step();
        newStep.setStep(step.getStep());
        newStep.setHowto(howto);
        return stepRepository.save(newStep);
    }

    @Override
    public Step update(Step step, long stepid) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
