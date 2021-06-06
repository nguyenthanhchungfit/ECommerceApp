/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.common;

import com.ecommerce.crawler.api.TikiApiModel;
import static com.ecommerce.crawler.api.TikiApiModel.INSTANCE;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author chungnt
 */
public class FileUtils {

    public static void writeFile(List<String> data, String pathFile) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(pathFile);
            for (String str : data) {
                writer.write(str + System.lineSeparator());
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(TikiApiModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(TikiApiModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static List<String> readData(String pathFile) {
        List<String> data = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                pathFile));
            String line = reader.readLine();
            while (line != null) {
                data.add(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
