package com.howto.services;

import com.howto.exceptions.ResourceNotFoundException;
import com.howto.models.HowTo;
import com.howto.models.Step;
import com.howto.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
        return stepRepository.findById(stepid)
                .orElseThrow(() -> new ResourceNotFoundException("Step with id " + stepid + " not found!"));
    }


    @Transactional
    @Override
    public void delete(long id) {
        stepRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Step with id " + id + " not found!"));
        stepRepository.deleteById(id);
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
        Step newStep = findStepById(stepid);
        if (newStep.getStep() == null) {
            throw new ResourceNotFoundException("Step with id " + stepid + " does not exist!");
        }
        if (step.getStep() != null) {
            newStep.setStep(step.getStep());
        }

        return stepRepository.save(newStep);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteAll() {
        stepRepository.deleteAll();
    }
}
