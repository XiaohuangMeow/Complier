package Syntax.Analysis;

import Syntax.Method.Method;
import Syntax.Structure.Closure;
import Syntax.Structure.Production;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ac{

    public ac(int status, String terminal) {
        this.status = status;
        this.terminal = terminal;
    }

    int status;
    String terminal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ac ac = (ac) o;

        if (status != ac.status) return false;
        return terminal.equals(ac.terminal);
    }

    @Override
    public int hashCode() {
        int result = status;
        result = 31 * result + terminal.hashCode();
        return result;
    }
}

public class action {
    //转移状态
    private static Map<ac,Integer> map=new HashMap<>();

    //转移产生式
    private static Map<ac, Production> map2=new HashMap<>();

    public static void add(int status,String terminal,int g){
        map.put(new ac(status,terminal),g);
    }

    public static void add(int status,String terminal,Production p){
        map2.put(new ac(status,terminal),p);
    }

    public static int nextState(int status,String terminal){
        return map.get(new ac(status,terminal));
    }

    public static Production nextProduction(int status,String terminal){
        return map2.get(new ac(status,terminal));
    }

    public static boolean contain(int status,String terminal){
        return map.containsKey(new ac(status,terminal));
    }

    public static Map<ac, Integer> getMap() {
        return map;
    }

    public static void setMap(Map<ac, Integer> map) {
        action.map = map;
    }

    public static Map<ac, Production> getMap2() {
        return map2;
    }

    public static void setMap2(Map<ac, Production> map2) {
        action.map2 = map2;
    }

    public static int jugde(int status, String terminal){
        ac a=new ac(status,terminal);

        boolean isreduce=map2.containsKey(a);
        boolean isshift=map.containsKey(a);
        if (isreduce&&isshift){
            Production p=action.nextProduction(status,terminal);
            if (p.getPrior()!=null&&terminal.equals(p.getPrior())){
                return 1;
            }
        }

        if (isreduce){
            return 2;
        }
        if (isshift){
            return 1;
        }
        return 0;
    }

    public static void write(List<Closure> States) throws IOException {
        List<String> outputs=new ArrayList<>();
        String output="";
        for (String terminal:Main.terminals){
            output+="\t\t"+terminal;
//            System.out.print(output);
        }
        outputs.add(output);
        System.out.println();
        outputs.add("");
        output="";
        for (int i=0;i<States.size();i++){
//            System.out.print(i+"\t\t");
            output+=i+"\t\t";
            for (String terminal:Main.terminals){
                int judge=jugde(i,terminal);
                if (judge==1){
                    output+="s"+map.get(new ac(i,terminal))+"\t\t";
//                    System.out.print(output);
                }
                else if (judge==2){
                    if (Main.productions.indexOf(map2.get(new ac(i,terminal)))==0){
                        output+="acc\t\t";
//                        System.out.print("acc\t\t");

                    }
                    else {
                        output+="r"+Main.productions.indexOf(map2.get(new ac(i,terminal)))+"\t\t";
//                        System.out.print("r"+Main.productions.indexOf(map2.get(new ac(i,terminal)))+"\t\t");
                    }
                }
                else {
                    output+="\t\t";
//                    System.out.print("\t\t");
                }
            }
            outputs.add(output);
            output="";
            outputs.add("");
//            System.out.println();
        }
        Method.WriteFile("Action_Table.txt",outputs);
    }
}
