package com.example.demo.util;

public class FileUtils {

    public static String getExtension(String fileName) {
        return fileName.contains(".") ? fileName.substring(fileName.indexOf('.')) : null;
    }
}
