package com.bezkoder.springjwt.DTO;

import java.time.LocalDate;

public class ExperienceDTO {
    private Long affectationId;
    private Long projectId;
    private Long clientId;
    private String titre;
    private LocalDate date_debut;
    private LocalDate date_fin;
    private Double tjm;
    private String clientName;

    public Long getAffectationId() {
        return affectationId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setAffectationId(Long affectationId) {
        this.affectationId = affectationId;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public Double getTjm() {
        return tjm;
    }

    public void setTjm(Double tjm) {
        this.tjm = tjm;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
