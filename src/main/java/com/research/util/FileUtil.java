/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.research.util;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

/**
 *
 * @author rafael.bahia
 */
public class FileUtil {

    public static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static <E> void writerFile(String nomeArquivo, Collection<E> lista) throws IOException {
        Gson gson = new Gson();
        FileWriter writeFile = null;
        String json = gson.toJson(lista);
        try {
            writeFile = new FileWriter(nomeArquivo + ".json");
            //Escreve no arquivo conteudo do Objeto JSON
            writeFile.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeFile.close();
    }
}
