package com.acme.test.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class IOUtil {
    public static String getContents(String aFile) {
        StringBuilder contents = new StringBuilder();

        try {
            BufferedReader input = new BufferedReader(new FileReader(new File(aFile)));
            try {
                String line = null;
                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return contents.toString();
    }
}
