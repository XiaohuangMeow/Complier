package Syntax.Analysis;

import Syntax.Structure.Production;

import java.util.HashMap;
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
}
