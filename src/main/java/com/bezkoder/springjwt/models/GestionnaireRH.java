package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class GestionnaireRH extends Consultant{
    @OneToMany(mappedBy = "gestionnaireRH", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "cons_gest")
    private List<Consultant> consultantList;

    @OneToMany(mappedBy = "gestionnaireRH", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "gest_off")
    private List<Offre> offreList ;

    public List<Offre> getOffreList() {
        return offreList;
    }

    public void setOffreList(List<Offre> offreList) {
        this.offreList = offreList;
    }

    public List<Consultant> getConsultantList() {
        return consultantList;
    }



    public void setConsultantList(List<Consultant> consultantList) {
        this.consultantList = consultantList;
    }
}
