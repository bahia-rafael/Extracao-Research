/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.research.automacao;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Rafa
 */
public class Teste {

    public static void main(String[] args) {
        
        HashMap<Integer, String> mapaNomes = new HashMap<>();
        mapaNomes.put(1, "João Delfino");
        mapaNomes.put(2, "Maria do Carmo");
        mapaNomes.put(3, "Claudinei Silva");
        mapaNomes.put(4, "Amélia Mourão");

        for (Integer valor : mapaNomes.keySet()) {
            System.out.println(valor + " " + mapaNomes.get(valor));
        }

    }

}
