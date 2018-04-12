package com.example.jackypeng.swangyimusic.util;

import com.example.jackypeng.swangyimusic.lrc.DefaultLrcParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jackypeng on 2018/3/12.
 */

public class FileUtil {
    public static void writeToFile(String content, String path) {
        File lrc_file = new File(path);
        if (!lrc_file.exists()) {
            try {
                lrc_file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(lrc_file);
                outputStream.write(content.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileString(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            StringBuilder sb = new StringBuilder();
            try {
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
