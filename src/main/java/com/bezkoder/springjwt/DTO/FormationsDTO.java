package com.bezkoder.springjwt.DTO;

import com.bezkoder.springjwt.models.Consultant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class FormationsDTO {
    private Long id;
    private String degree;
    private String domain_formation;
    private String annee;
    private String ville;
    private String etablissement;
    private Long consultantId;

    public Long getId() {
        return id;
    }

    public Long getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(Long consultantId) {
        this.consultantId = consultantId;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getDomain_formation() {
        return domain_formation;
    }

    public void setDomain_formation(String domain_formation) {
        this.domain_formation = domain_formation;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
