/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.research.validacao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
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
}
