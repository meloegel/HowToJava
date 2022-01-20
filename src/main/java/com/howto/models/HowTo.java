package com.howto.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.howto.models.Auditable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "howtos")
public class HowTo extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long howtoid;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column
    private String complexity;

    @OneToMany(mappedBy = "howto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "howtoid", allowSetters = true)
    private List<Step> steps = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties(value = {"howTos", "roles", "useremails"}, allowSetters = true)
    private User user;

    public HowTo() {}

    public HowTo(String name, String description, String category, List<Step> steps, User user) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.steps = steps;
        this.user = user;
    }

    public HowTo( String name, String description, String category, String complexity, List<Step> steps, User user) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.complexity = complexity;
        this.steps = steps;
        this.user = user;
    }

    public HowTo( String name, String description, String category, User user) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.user = user;
    }

    public long getHowtoid() {
        return howtoid;
    }

    public void setHowtoid(long howtoid) {
        this.howtoid = howtoid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


