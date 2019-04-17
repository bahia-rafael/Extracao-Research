package com.research.util;

import static com.research.model.GetProperties.prop;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rafael.bahia
 */
public class IdentificadorSO {

    public static String identificarSO() {
        String retorno;
        if (System.getProperty("os.name").contains("Windows")) {
            retorno = prop.getProperty("CAMINHO_DRIVER_WIN");
        } else {
           retorno = prop.getProperty("CAMINHO_DRIVER_LINUX");
        }
        
        return retorno;
    }

}
