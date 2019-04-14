package Lexical.Tool;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SymbolTable {

    private static List<String> symbolTable=new ArrayList<>();

    public static int addSymbolTable(String s){
        if (symbolTable.contains(s)){
            return symbolTable.indexOf(s);
        }
        else{
            symbolTable.add(s);
            return symbolTable.size()-1;
        }
    }

    public static int getPosition(String s){
        if (symbolTable.contains(s)){
            return symbolTable.indexOf(s);
        }
        return -1;
    }

    public static String getString(int x){
        return symbolTable.get(x);
    }

    public static void WriteFile(String filename) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename)) ;
        for (int i = 0; i <symbolTable.size() ; i++) {
            bw.write(i+"\t\t"+symbolTable.get(i));
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

}
