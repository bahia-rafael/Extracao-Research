/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.research.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author rafael.bahia
 */
public class GetProperties {

    public static Properties prop = getProp();

    public static Properties getProp() {
        Properties props = new Properties();
        try {
            FileInputStream file = new FileInputStream("./src/main/java/com/research/config/config.properties");
            props.load(file);
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return props;
    }
}
