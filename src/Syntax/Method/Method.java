package Syntax.Method;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Method {
    public static List<String> ReadFile(String filename) throws IOException {
        FileInputStream inputStream = new FileInputStream(filename);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> list=new ArrayList<>();
        String str=null;
        while ((str=bufferedReader.readLine())!=null){
            list.add(str);
        }
        inputStream.close();
        bufferedReader.close();
        return list;
    }

    public static void WriteFile(String filename,List<String> WriteList) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename)) ;
        for (int i = 0; i <WriteList.size() ; i++) {
            bw.write(WriteList.get(i));
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }


}
