package Syntax.Analysis;

import Syntax.Method.Method;
import Syntax.Structure.Closure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class gt{

    int status;
    String nonterminal;

    public gt(int status, String nonterminal) {
        this.status = status;
        this.nonterminal = nonterminal;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        gt gt = (gt) o;

        if (status != gt.status) return false;
        return nonterminal != null ? nonterminal.equals(gt.nonterminal) : gt.nonterminal == null;
    }

    @Override
    public int hashCode() {
        int result = status;
        result = 31 * result + (nonterminal != null ? nonterminal.hashCode() : 0);
        return result;
    }
}

public class gotoState {

    private static Map<gt,Integer> map=new HashMap<>();

    public static void add(int status,String nonterminal,int g){
        map.put(new gt(status, nonterminal),g);
    }

    public static int nextState(int status,String nonterminal){
        return map.get(new gt(status,nonterminal));
    }

    public static void write(List<Closure> States) throws IOException {
        List<String> outputs=new ArrayList<>();
        String output="";
        for (String nonterminal:Main.nonterminals){
//            System.out.print("\t\t"+nonterminal);
            output+="\t\t"+nonterminal;
        }
        outputs.add(output);
        outputs.add("");
        output="";
//        System.out.println();
        for (int i=0;i<States.size();i++){
//            System.out.print(i+"\t\t");
            output+=i+"\t\t";
            for (String nonterminal:Main.nonterminals){
                gt g=new gt(i,nonterminal);
                if (map.containsKey(g)){
                    output+=map.get(g)+"\t\t";
//                    System.out.print(map.get(g)+"\t\t");
                }
                else {
                    output+="\t\t";
//                    System.out.print("\t\t");
                }
            }
            outputs.add(output);
            outputs.add("");
            output="";
//            System.out.println();
        }
        Method.WriteFile("Goto_Table.txt",outputs);
    }



}
