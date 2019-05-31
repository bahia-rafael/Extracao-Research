/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.research.model;

/**
 *
 * @author RAFAELDEOLIVEIRABAHI
 */
public class ProfessorSearchFirst {

    private String nomePlanilha;
    private String nomeEncontrado;
    private String rotulo;
    private String link;

    public ProfessorSearchFirst(String nomePlanilha, String nomeEncontrado, String rotulo, String link) {
        this.nomePlanilha = nomePlanilha;
        this.nomeEncontrado = nomeEncontrado;
        this.rotulo = rotulo;
        this.link = link;
    }

    public String getNomePlanilha() {
        return nomePlanilha;
    }

    public void setNomePlanilha(String nomePlanilha) {
        this.nomePlanilha = nomePlanilha;
    }

    public String getNomeEncontrado() {
        return nomeEncontrado;
    }

    public void setNomeEncontrado(String nomeEncontrado) {
        this.nomeEncontrado = nomeEncontrado;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
