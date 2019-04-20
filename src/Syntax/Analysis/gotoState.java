package Syntax.Analysis;

import java.util.HashMap;
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



}
