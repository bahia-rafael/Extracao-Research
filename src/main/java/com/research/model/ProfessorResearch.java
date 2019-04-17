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
 * @author rafael
 */
public class ProfessorResearch {

    private String name;
    private String nickName;
    private int qntdArtigos;
    private List<String> skills;
    private List<String> artigos;

    @Override
    public String toString() {
        return "ProfessorResearch{" + "name=" + name + ", nickName=" + nickName + ", skills=" + skills + ", qndArtigos=" + qntdArtigos + ", artigos=" + artigos + '}';
    }

    public ProfessorResearch() {
    }

    public ProfessorResearch(String nickName, int qndSkills, List<String> skills, List<String> artigos) {

        this.nickName = Normalizador.semAcento(nickName);
        this.qntdArtigos = qndSkills;
        this.skills = skills;
        this.artigos = artigos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Normalizador.semAcento(name);
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = Normalizador.semAcento(nickName);
    }

    public int getQndArtigos() {
        return qntdArtigos;
    }

    public void setQndArtigos(int qndSkills) {
        this.qntdArtigos = qndSkills;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {

        List<String> normalizador = new ArrayList<>();

        for (String skill : skills) {

            normalizador.add(Normalizador.semAcento(skill));

        }
        this.skills = normalizador;
    }

    public List<String> getArtigos() {
        return artigos;
    }

    public void setArtigos(List<String> artigos) {

        List<String> normalizador = new ArrayList<>();

        for (String skill : artigos) {

            normalizador.add(Normalizador.semAcento(skill));

        }
        
        this.artigos = normalizador;
    }

}
