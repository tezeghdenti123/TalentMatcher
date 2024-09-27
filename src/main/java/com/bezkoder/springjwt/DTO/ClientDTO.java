package com.bezkoder.springjwt.DTO;

public class ClientDTO {
    private Long id;
    private String adresse;
    private String contact;
    private String email;
    private String name;
    private String num_siret;
    private String num_tva;
    private String telephone;

    public Long getId() {
        return id;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getContact() {
        return contact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNum_tva() {
        return num_tva;
    }

    public void setNum_tva(String num_tva) {
        this.num_tva = num_tva;
    }

    public String getNum_siret() {
        return num_siret;
    }

    public void setNum_siret(String num_siret) {
        this.num_siret = num_siret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
