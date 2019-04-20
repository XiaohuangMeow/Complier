package Syntax.Analysis;

import Lexical.Analysis.Table;
import Syntax.Method.Method;
import Syntax.Structure.Closure;
import Syntax.Structure.Item;
import Syntax.Structure.Production;
import Syntax.Structure.Tuple;

import java.io.IOException;
import java.util.*;

public class Main {

    public static List<Production> productions=new ArrayList<>();
    public static Set<String> terminals=new HashSet<>();
    public static Set<String> nonterminals=new HashSet<>();
    public static Map<String,Set<String>> First=new HashMap<>();

    public static void GenerateProduction() throws IOException {
        List<String> lines= Method.ReadFile("Grammar2.txt");
        for (int i = 0; i < lines.size(); i++) {
            String line=lines.get(i);
            int position=line.indexOf("->");
            String left=line.substring(0,position);
            String right=line.substring(position+2);
            Production p=new Production(left,right);
            productions.add(p);
        }
    }

    public static void fillingTerminals(){
        for (int i = 0; i < productions.size(); i++) {
            Production production=productions.get(i);
            nonterminals.add(production.getLeft());
        }
        for (int i = 0; i < productions.size(); i++) {
            Production production=productions.get(i);
            for (int j = 0; j < production.getRightList().size(); j++) {
                String s=production.getRightList().get(j);
                if (!nonterminals.contains(s)){
                    terminals.add(s);
                }
            }
        }
    }

    public static void GenerateFirst(){
//        public static Map<String,Set<String>> First=new HashMap<>();
        for (String t:terminals) {
            Set<String> first=new HashSet<>();
            first.add(t);
            First.put(t,first);
        }
        for (String t:nonterminals) {
            Set<String> first=new HashSet<>();
            First.put(t,first);
        }
        boolean flag=true;
        while (flag){
            flag=false;
            for (Production production:productions){
                String left=production.getLeft();
                String right=production.getRightList().get(0);
                Set<String> first=First.get(right);
                for (String s:first){
                    if (!First.get(left).contains(s)){
                        First.get(left).add(s);
                        flag=true;
                    }
                }
            }
        }
    }

    public static Set<String> getFirst(Item item){
        if (item.getPos()+1==item.getCountRight()){
            Set<String> hs=new HashSet<>();
//            hs.addAll(item.getLookahead());
            hs.add(item.getLookahead());
            return hs;
        }
        return First.get(item.getRightList().get(item.getPos()+1));
    }

    public static Closure CalculateClosure(Item item){
        Set<Item> set=new HashSet<>();
        Set<Item> oldset=new HashSet<>();
        Set<Item> newset=new HashSet<>();
        set.add(item);
        oldset.add(item);
        boolean flag=true;
        while (flag){
            flag=false;
            for (Item it:oldset) {
                //获取.X的X
                if (it.getPos()==it.getCountRight()){
                    continue;
                }
                String tmp=it.getRightList().get(it.getPos());
                //属于非终结符
                if (nonterminals.contains(tmp)){
                    for (Production production : productions) {
                        if (production.getLeft().equals(tmp)){
                            Set<String> first=getFirst(it);
                            for (String s:first){
                                Item newItem=new Item(production,s);
                                if (!set.contains(newItem)){
                                    set.add(newItem);
                                    newset.add(newItem);
                                    flag=true;
                                }
                            }
                        }
                    }
                }
            }
            oldset=newset;
            newset=new HashSet<>();
        }

//        Set<Item> c=new HashSet<>();
//        for (Item item1:set){
//            for (Item item2:set){
//                if (item1!=item2&&SameItem(item1,item2)){
//
//                }
//            }
//        }
        return new Closure(set);
//        return new Closure(set);
    }

    public static Closure CalculateClosure(Set<Item> items){
        Set<Item> set=new HashSet<>();
        for (Item item:items){
            Set<Item> temp=CalculateClosure(item).getItems();
            set.addAll(temp);
        }
        if (set.isEmpty()) return null;
        return new Closure(set);
    }


        private static boolean SameItem(Item item1,Item item2){
        return item1.getLeft().equals(item2.getLeft())&&item1.getRight().equals(item2.getRight())&&item1.getPos()==item2.getPos();
    }


    public static Closure Goto(List<Closure> states,Closure closure,String x){
        Set<Item> items=closure.ItemContains(x);
        Set<Item> newitems=new HashSet<>();
        if (items==null)
            return null;
        for (Item item:items){
            Item firstItemInClosure=new Item(item);
            newitems.add(firstItemInClosure);
        }
        for (int i = 0; i < states.size(); i++) {
            boolean flag=false;
            for (Item newitem:newitems){
            if (!states.get(i).contains(newitem)){
                flag=true;
                break;
//                newitems.add(item);
//                    return states.get(i);
            }}
            if (!flag){
                return states.get(i);
            }
        }

        return null;
//        return  CalculateClosure(firstItemInClosure);
    }

    public static List<Closure> cluster(){
        System.out.println();
//        Production begin=productions.get(0);
        Item begin=new Item(productions.get(0),"#");
//        System.out.println(begin);
        Closure start=CalculateClosure(begin);
//        System.out.println(start);
        List<Closure> Cluster=new ArrayList<>();
        List<Closure> oldcluster=new ArrayList<>();
        List<Closure> newcluster=new ArrayList<>();
        Set<String> symbol=new HashSet<>();
        symbol.addAll(terminals);
        symbol.addAll(nonterminals);
        Cluster.add(start);
        oldcluster.add(start);
//        System.out.println();
        boolean flag=true;
        while (flag){
            flag=false;
            for (String s : symbol) {
                for (Closure closure: oldcluster) {
                    Set<Item> items=closure.ItemContains(s);
                    if (items==null){
                        continue;
                    }
                    Set<Item> newItems=new HashSet<>();
                    for (Item item:items){
                        newItems.add(new Item(item));
                    }
                    Closure c=CalculateClosure(newItems);
                    if (c!=null&&!Cluster.contains(c)){
                        Cluster.add(c);
                        newcluster.add(c);
//                        System.out.println(newItems);
//                        System.out.println(c);
                        flag=true;
                    }
                }
            }
            oldcluster=newcluster;
            newcluster=new ArrayList<>();

//            for (Closure closure: oldcluster) {
//                System.out.println();
//                System.out.println("Test:"+closure);
//                for (String s : symbol) {
////                    System.out.println(s);
//                    Item it=closure.ItemContains(s);
////                    System.out.println(it);
//                    if (it==null){
//                        continue;
//                    }
//                    it=new Item(it);
//                    Closure c=CalculateClosure(it);
//                    if (c!=null&&!Cluster.contains(c)){
////                        Cluster.add(c);
//                        System.out.println(it);
//                        System.out.println("newClosure:"+c);
//                        System.out.println();
//                        newcluster.add(c);
//                        flag=true;
//                    }
//                }
//            }
//            Cluster.addAll(newcluster);
//            oldcluster=newcluster;
//            newcluster=new ArrayList<>();
        }
        System.out.println();
        return Cluster;
    }

    public static void ConstructLR1Table(List<Closure> States){
        System.out.println(States.size());
        for (int k=0;k<States.size();k++){
            Closure closure=States.get(k);
//            System.out.println();
//            System.out.println(k+"  "+closure);
//            for (String terminal:terminals) {
//                //action_table
//                for (Item item : closure.getItems()) {
//                    String rightFirst=item.getRFirst();
//                    //reduce
//                    if (rightFirst==null){
//                        String left=item.getLeft();
//                        String right=item.getRight();
//                        for (int i=0;i<productions.size();i++){
//                            Production production=productions.get(i);
//                            //遇到终结符为展望符
//                            if (production.getLeft().equals(left) && production.getRight().equals(right)&&terminal.equals(item.getLookahead())) {
//                                action.add(k,item.getLookahead(),production);
//                                System.out.println("!!  "+k+"  "+item+"  "+action.nextProduction(k,item.getLookahead()));
//                                break;
//                            }
//                        }
//                        continue;
//                    }
//                    else{
//                        Closure next=null;
//                        int j=States.indexOf(next);
//                        action.add(k,terminal,j);
//                        System.out.println("action:"+k+"  "+rightFirst+"  "+action.nextState(k,rightFirst));
//                    }
//                }
//            }

//            for (String nonterminal:nonterminals) {
//                Closure next = Goto(States, closure, nonterminal);
//                if (next == null) {
//                    continue;
//                }
//                for (Item item : closure.getItems()) {
//                    String rightFirst=item.getRFirst();
//                    if (rightFirst!=null&&rightFirst.equals(nonterminal)) {
//                        int j = States.indexOf(next);
//                        gotoState.add(k, rightFirst, j);
//                        System.out.println("goto:" + k + "  " + rightFirst + "  " + gotoState.nextState(k, rightFirst));
//                    }
//                }
//            }
                //action_goto
            for (Item item:closure.getItems()){
                String rightFirst=item.getRFirst();
                Closure next=Goto(States,closure,rightFirst);
//                System.out.println();
//                System.out.println(item);
//                System.out.println(next);
                //reduce
//                if (k==4){
//                    System.out.println("aaa");
//                    System.out.println(item);
//                    System.out.println("aaa");
//                }
                if (rightFirst==null){
                    String left=item.getLeft();
                    String right=item.getRight();
                    for (int i=0;i<productions.size();i++){
                        Production production=productions.get(i);
                        if (production.getLeft().equals(left) && production.getRight().equals(right)) {
                            action.add(k,item.getLookahead(),production);
//                            System.out.println("!!  "+k+"  "+item+"  "+action.nextProduction(k,item.getLookahead()));
                            break;
                        }
                    }
                    continue;
                }
                //action
                else if (terminals.contains(rightFirst)&&next!=null){
                    int j=States.indexOf(next);
//                    if (action.jugde(k,rightFirst)!=0
//                            &&action.nextState(k,rightFirst)!=j){
//                        System.out.println("aaaaaaaaaaaaaaaaaaaa");
//                    }
                    action.add(k,rightFirst,j);
//                    System.out.println("action:"+k+"  "+rightFirst+"  "+action.nextState(k,rightFirst));
                }
                //goto
                if (nonterminals.contains(rightFirst)&&next!=null){
                    int j=States.indexOf(next);
//                    if (action.jugde(k,rightFirst)!=0&&action.nextState(k,rightFirst)!=j){
//                        System.out.println("aaaaaaaaaaaaaaaaaaaa");
//                    }
                    gotoState.add(k,rightFirst,j);
//                    System.out.println("goto:"+k+"  "+rightFirst+"  "+gotoState.nextState(k,rightFirst));
                }

            }
            //goto

            //action_reduce
        }
//        action.add(1,"#",-1);
        Production p=productions.get(0);
        Item item=new Item(new Item(p,"#"));
        for (int i=0;i<States.size();i++){
            Closure c=States.get(i);
            if (c.contains(item)){
                action.add(i,"#",-1);
                return;
            }

        }
    }

    public static void analysis(List<Tuple> tuples,List<Closure> States){
        tuples.add(new Tuple("#",-1,""));
        int read=0;
        Stack<Integer> StatusStack=new Stack<>();
        StatusStack.push(0);
        Stack<String> SymbolStack=new Stack<>();
//        int x=0;



        while (true){
//            System.out.println(x++);
            int nowState=StatusStack.peek();
            String input=tuples.get(read).getInput();
            Closure now=States.get(nowState);

            int judge=action.jugde(nowState,input);
            //移进
            if (judge==1){
//                System.out.println(nowState+"   "+input);
//                System.out.println(StatusStack);
//                System.out.println(SymbolStack);

                int next=action.nextState(nowState,input);
                if (next==-1){
                    break;
                }
                StatusStack.push(next);
                SymbolStack.push(input);

//                System.out.println(StatusStack);
//                System.out.println(SymbolStack);

                read++;
            }
            //规约
            else if (judge==2){
                if (action.contain(nowState,input)){
                    int next=action.nextState(nowState,input);
                    if (next==-1){
                        break;
                    }
                }

//                System.out.println(StatusStack);
//                System.out.println(SymbolStack);

                //reduce
                Production p=action.nextProduction(nowState,input);
                int cnt=p.getRightList().size();
                while (cnt-->0){
                    StatusStack.pop();
                    SymbolStack.pop();
                }
                SymbolStack.push(p.getLeft());

//                System.out.println(StatusStack);
//                System.out.println(SymbolStack);

                //goto
                int next=gotoState.nextState(StatusStack.peek(),SymbolStack.peek());
                StatusStack.push(next);

//                System.out.println(StatusStack);
//                System.out.println(SymbolStack);

            }
            else if (judge==0){
//                System.out.println("aaaaaaaaaaaa");
            }
            //规约
//            for (Item it:now.getItems()){
//                if (it.isReduce()&&){
//
//                }
//            }
//
//            //移进
//            Closure nextStatus=Goto(States,now,input);
//            int seq=States.indexOf(nextStatus);
//            StatusStack.push(seq);
//            SymbolStack.push(input);
        }
    }

    public static void main(String[] args) throws IOException {
        GenerateProduction();

//        for (int i = 0; i < productions.size(); i++) {
//            System.out.println(productions.get(i));
//        }

        fillingTerminals();
//        for (String t : terminals) {
//            System.out.println(t);
//        }
//        System.out.println();
//        for (String t : nonterminals) {
//            System.out.println(t);
//        }

//        System.out.println("abcdefg");
        GenerateFirst();
//        for (Map.Entry entry:First.entrySet()){
//            System.out.println(entry.getKey()+" "+entry.getValue());
//        }

        List<Closure> States=cluster();
//        System.out.println(States.size());
//        for (int i=0;i<States.size();i++){
//            System.out.println(States.get(i));
//        }

        ConstructLR1Table(States);


        Lexical.Analysis.Main.LexicalAnalysis();
        List<String> token=Lexical.Analysis.Main.WriteList;

//        System.out.println(token);

        List<String> table= Method.ReadFile("src/lexical/table.txt");

        List<Tuple> tuples=new ArrayList<>();
        for (int i=0;i<token.size();i++){
            String[] arr=token.get(i).split(" ");
            tuples.add(new Tuple(table.get(Integer.valueOf(arr[1])),Integer.valueOf(arr[1]),arr[3]));
//            System.out.println(tuples.get(i));
        }

        analysis(tuples,States);

//        System.out.println(cluster());
//        for (int i = 0; i < productions.size(); i++) {
//            System.out.println(productions.get(i));
//        }
//        for (String t : terminals) {
//            System.out.println(t);
//        }
//        System.out.println();
//        for (String t : nonterminals) {
//            System.out.println(t);
//        }
//        for (Map.Entry entry:First.entrySet()){
//            System.out.println(entry.getKey()+" "+entry.getValue());
//        }

    }
}
