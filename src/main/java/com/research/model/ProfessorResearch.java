/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.research.model;

import com.research.util.Normalizador;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bahia-rafael
 */
public class ProfessorResearch {

    private String name;
    private String nickName;
    private int qntdArtigos;
    private List<String> skills;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Normalizador.semAcento(name);
    }

    public void setNickName(String nickName) {
        this.nickName = Normalizador.semAcento(nickName);
    }

    public void setQndArtigos(int qndSkills) {
        this.qntdArtigos = qndSkills;
    }

    public void setSkills(List<String> skills) {
        List<String> normalizador = new ArrayList<>();
        skills.forEach((skill) -> {
            normalizador.add(Normalizador.semAcento(skill));
        });
        this.skills = normalizador;
    }
}
