package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    @Column(name = "description",columnDefinition = "TEXT")
    String description;
    String tjm;
    String dateDebut;
    String location;
    String type;
    String status;
    String position;
    String experience;
    @ManyToOne
    @JoinColumn(name = "rh_id")
    @JsonBackReference(value = "gest_off")
    private GestionnaireRH gestionnaireRH;
    public Long getId() {
        return id;
    }

    public String getTjm() {
        return tjm;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setTjm(String tjm) {
        this.tjm = tjm;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public GestionnaireRH getGestionnaireRH() {
        return gestionnaireRH;
    }

    public void setGestionnaireRH(GestionnaireRH gestionnaireRH) {
        this.gestionnaireRH = gestionnaireRH;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
