package com.bezkoder.springjwt.DTO;

import com.bezkoder.springjwt.models.Consultant;

public class SkillDTO {
    private Long id;
    private String name;
    private Long consultantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(Long consultantId) {
        this.consultantId = consultantId;
    }
}
