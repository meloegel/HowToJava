package com.howto.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "steps")
public class Step extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long stepid;

    @Column(nullable = false)
    private String step;

    @ManyToOne
    @JoinColumn(name = "howtoid", nullable = false)
    @JsonIgnoreProperties(value = {"steps", "user", "complexity", "category", "description"}, allowSetters = true)
    private HowTo howto;

    public Step() {}

    public Step(String step, HowTo howto) {
        this.step = step;
        this.howto = howto;
    }

    public long getStepid() {
        return stepid;
    }

    public void setStepid(long stepid) {
        this.stepid = stepid;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public HowTo getHowto() {
        return howto;
    }

    public void setHowto(HowTo howto) {
        this.howto = howto;
    }
}
