package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Commerciale extends Consultant{
    @OneToMany(mappedBy = "commerciale", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "cons_com")
    private List<Consultant> consultantList;

    public List<Consultant> getConsultantList() {
        return consultantList;
    }

    public void setConsultantList(List<Consultant> consultantList) {
        this.consultantList = consultantList;
    }
}
