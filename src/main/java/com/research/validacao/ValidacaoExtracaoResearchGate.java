/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.research.validacao;

import com.research.model.ProfessorResearch;
import com.research.util.LoadUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafa
 */
public class ValidacaoExtracaoResearchGate {

    /**
     * Metodo para retirar nomes duplicados
     *
     * @param filename, o nome do arquivo que será verificado
     * @return da lista que não haverá duplicatas
     */
    public static Set<String> retirarRepeticao(String filename) {

        Set<String> nomeSemRepeticao = new HashSet<>();

        String linha;
        FileReader in;
        try {
            in = new FileReader(filename);
            BufferedReader br = new BufferedReader(in);

            while ((linha = br.readLine()) != null) {

                nomeSemRepeticao.add(linha);

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ValidacaoExtracaoResearchGate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ValidacaoExtracaoResearchGate.class.getName()).log(Level.SEVERE, null, ex);
        }

        /**
         * Leio os nomes, linha por linha, até finalizar a lista.
         */
        return nomeSemRepeticao;
    }

    /**
     * Metodo para verificar os restantes que não foram validados
     *
     * @param fileNameTotal, o nome do arquivo onde possui todos os nomes
     * @param fileNameProgress o nome do arquivo onde possui alguns registros
     * que já foram validados
     * @param fileNameNewArq o nome do arquivo que deseja gravar contendo os
     * restantes dos nome
     */
    public static void verificaoRestantes(String fileNameTotal, String fileNameProgress, String fileNameNewArq) {

        Set<String> nomeSemRepeticao = ValidacaoExtracaoResearchGate.retirarRepeticao(fileNameTotal);

        List<ProfessorResearch> professores = LoadUtil.loadListObjectsFromJson(fileNameProgress, ProfessorResearch[].class);

        FileWriter arq;
        try {
            arq = new FileWriter("C:/Users/Rafa/Documents/NetBeansProjects/ReSearch/" + fileNameNewArq + ".txt");
            BufferedWriter escritor = new BufferedWriter(arq);

            for (ProfessorResearch professor : professores) {

                nomeSemRepeticao.remove(professor.getName());

            }

            for (String nome : nomeSemRepeticao) {

                System.out.println(nome);
                escritor.write(nome);
                escritor.newLine();

            }

            escritor.close();
            arq.close();
        } catch (IOException ex) {
            Logger.getLogger(ValidacaoExtracaoResearchGate.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
