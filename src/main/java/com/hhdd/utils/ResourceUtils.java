package com.hhdd.utils;

import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * @Author HuangLusong
 * @Date 2020/11/18 8:46
 */
public class ResourceUtils {
    /**
     * 加载log文件并转string返回
     *
     * @return
     */
    public static String loadLog2String() throws IOException {
        //日志文件目录暂时写死
        FileInputStream fileInputStream = new FileInputStream("logs/daily.log");
        int size = fileInputStream.available();
        String string = FileUtils.readFileToString(new File("logs/daily.log"), "utf-8");
        return string;
    }

    /**
     * 读取文件内容，并转string返回
     *
     * @return
     */
    public static String file2String(String path) throws IOException {
/*        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line = bufferedReader.readLine();
        StringBuffer res = new StringBuffer();
        while (line != null) {
            res.append(line);
            line = bufferedReader.readLine();
        }
        return res.toString();*/
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(path), "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer();
        String line = bufferedReader.readLine();
        while (line != null) {
            sb.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        String string = file2String("logs/daily.log");
//        String string = loadLog2String();
        System.out.println(string);
    }

}
