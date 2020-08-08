package me.zhengjie.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Authoor: witt
 * @Decsription:
 * @Date: Created in 8:24 2020/8/6
 * @Modified:
 **/
public class JjUtil {
    /**
     * 获取基金实时动态
     *
     * @author witt
     * @description
     * @date 8:24 2020/8/6
     */
    public static String getJjRealTimeDynamic(String urlString) {
        String res = "";
        try {
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedWriter writer = new BufferedWriter(new FileWriter("index.html"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                res = line;
                writer.write(line);
                writer.newLine();
            }
            reader.close();
            writer.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
