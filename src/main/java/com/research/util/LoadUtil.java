/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.research.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafael.bahia
 */
public class LoadUtil {
    
    public static <T extends Object> T loadObjectFromJson(String id, Class<T> classOfT ) throws IOException{
        return new Gson().fromJson(FileUtil.readFile(id+".json", Charset.defaultCharset()), classOfT);
    }
    
     public static <T extends Object> List<T> loadListObjectsFromJson(String fileName, Class<T[]> classOfT) {
        List<T> objects = null;

        try {
            objects = Arrays.asList(new Gson().fromJson(FileUtil.readFile(fileName+".json", Charset.defaultCharset()), classOfT));
        } catch (JsonSyntaxException | IOException ex) {
            Logger.getLogger(Class.class.getName()).log(Level.SEVERE, null, ex);
            return objects;
        }

        return objects;
    }
    
}
