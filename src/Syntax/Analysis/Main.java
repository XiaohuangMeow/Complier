package Syntax.Analysis;

import Syntax.Method.Method;
import Syntax.Structure.Closure;
import Syntax.Structure.Item;
import Syntax.Structure.Production;
import Syntax.Structure.Tuple;

import java.io.IOException;
import java.util.*;

public class Main {

    public static List<Production> productions = new ArrayList<>();
    public static Set<String> terminals = new HashSet<>();
    public static Set<String> nonterminals = new HashSet<>();
    public static Map<String, Set<String>> First = new HashMap<>();
    public static List<String> outputs = new ArrayList<>();

    public static void GenerateProduction() throws IOException {
        List<String> lines = Method.ReadFile("Grammar.txt");
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.equals("") || line.charAt(0) == '#') {
                continue;
            }
            int position = line.indexOf("->");
            String left = line.substring(0, position);
            String right = line.substring(position + 2);
            Production p = new Production(left, right);
            productions.add(p);
        }
    }

    public static void fillingTerminals() {
        for (int i = 0; i < productions.size(); i++) {
            Production production = productions.get(i);
            nonterminals.add(production.getLeft());
        }
        for (int i = 0; i < productions.size(); i++) {
            Production production = productions.get(i);
            for (int j = 0; j < production.getRightList().size(); j++) {
                String s = production.getRightList().get(j);
                if (!nonterminals.contains(s)&&!s.equals("$")) {
                    terminals.add(s);
                }
            }
        }
    }

    public static void GenerateFirst() {
//        public static Map<String,Set<String>> First=new HashMap<>();
        for (String t : terminals) {
            Set<String> first = new HashSet<>();
            first.add(t);
            First.put(t, first);
        }
        Set<String> nullFirst=new HashSet<>();
        nullFirst.add("$");
        First.put("$",nullFirst);
        for (String t : nonterminals) {
            Set<String> first = new HashSet<>();
            First.put(t, first);
        }
        boolean flag = true;
        while (flag) {
            flag = false;
            for (Production production : productions) {
                String left = production.getLeft();
                String right = production.getRightList().get(0);
                Set<String> first = First.get(right);
                for (String s : first) {
                    if (!First.get(left).contains(s)) {
                        First.get(left).add(s);
                        flag = true;
                    }
                }
            }
        }
    }

    public static Set<String> getFirst(Item item) {
        if (item.getPos() + 1 == item.getCountRight()) {
            Set<String> hs = new HashSet<>();
//            hs.addAll(item.getLookahead());
            hs.add(item.getLookahead());
            return hs;
        }
        return First.get(item.getRightList().get(item.getPos() + 1));
    }

    public static Closure CalculateClosure(Item item) {
        Set<Item> set = new HashSet<>();
        Set<Item> oldset = new HashSet<>();
        Set<Item> newset = new HashSet<>();
        set.add(item);
        oldset.add(item);
        boolean flag = true;
        while (flag) {
            flag = false;
            for (Item it : oldset) {
                //获取.X的X
                if (it.getPos() == it.getCountRight()) {
                    continue;
                }
                String tmp = it.getRightList().get(it.getPos());
                //属于非终结符
                if (nonterminals.contains(tmp)) {
                    for (Production production : productions) {
                        if (production.getLeft().equals(tmp)) {
                            Set<String> first = getFirst(it);
                            for (String s : first) {
                                Item newItem = new Item(production, s);
                                if (!set.contains(newItem)) {
                                    set.add(newItem);
                                    newset.add(newItem);
                                    flag = true;
                                }
                            }
                        }
                    }
                }
            }
            oldset = newset;
            newset = new HashSet<>();
        }
        return new Closure(set);
    }

    public static Closure CalculateClosure(Set<Item> items) {
        Set<Item> set = new HashSet<>();
        for (Item item : items) {
            Set<Item> temp = CalculateClosure(item).getItems();
            set.addAll(temp);
        }
        if (set.isEmpty()) return null;
        return new Closure(set);
    }

    private static boolean SameItem(Item item1, Item item2) {
        return item1.getLeft().equals(item2.getLeft()) && item1.getRight().equals(item2.getRight()) && item1.getPos() == item2.getPos();
    }

    public static Closure Goto(List<Closure> states, Closure closure, String x) {
        Set<Item> items = closure.ItemContains(x);
        Set<Item> newitems = new HashSet<>();
        if (items == null)
            return null;
        for (Item item : items) {
            Item firstItemInClosure = new Item(item);
            newitems.add(firstItemInClosure);
        }
        for (int i = 0; i < states.size(); i++) {
            boolean flag = false;
            for (Item newitem : newitems) {
                if (!states.get(i).contains(newitem)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                return states.get(i);
            }
        }
        return null;
    }

    public static List<Closure> cluster() {
        Item begin = new Item(productions.get(0), "#");
        Closure start = CalculateClosure(begin);
        List<Closure> Cluster = new ArrayList<>();
        List<Closure> oldcluster = new ArrayList<>();
        List<Closure> newcluster = new ArrayList<>();
        Set<String> symbol = new HashSet<>();
        symbol.addAll(terminals);
        symbol.addAll(nonterminals);
        Cluster.add(start);
        oldcluster.add(start);
        boolean flag = true;
        while (flag) {
            flag = false;
            for (String s : symbol) {
                for (Closure closure : oldcluster) {
                    Set<Item> items = closure.ItemContains(s);
                    if (items == null) {
                        continue;
                    }
                    Set<Item> newItems = new HashSet<>();
                    for (Item item : items) {
                        newItems.add(new Item(item));
                    }
                    Closure c = CalculateClosure(newItems);
                    if (c != null && !Cluster.contains(c)) {
                        Cluster.add(c);
                        newcluster.add(c);
                        flag = true;
                    }
                }
            }
            oldcluster = newcluster;
            newcluster = new ArrayList<>();
        }
        return Cluster;
    }

    public static void ConstructLR1Table(List<Closure> States) {
//        System.out.println(States.size());
        for (int k = 0; k < States.size(); k++) {
            Closure closure = States.get(k);
            //action_goto
            //reduce
            for (Item item : closure.getItems()) {
                String rightFirst = item.getRFirst();
                Closure next = Goto(States, closure, rightFirst);
                if (rightFirst == null||rightFirst.equals("$")) {
                    String left = item.getLeft();
                    String right = item.getRight();
                    for (int i = 0; i < productions.size(); i++) {
                        Production production = productions.get(i);
                        if (production.getLeft().equals(left) && production.getRight().equals(right)) {
                            action.add(k, item.getLookahead(), production);
                            break;
                        }
                    }
                    continue;
                }
                //action
                else if (terminals.contains(rightFirst) && next != null) {
                    int j = States.indexOf(next);
                    action.add(k, rightFirst, j);
                }
                //goto
                if (nonterminals.contains(rightFirst) && next != null) {
                    int j = States.indexOf(next);
                    gotoState.add(k, rightFirst, j);
                }
            }
        }
//        action.add(1,"#",-1);
        Production p = productions.get(0);
        Item item = new Item(new Item(p, "#"));
        for (int i = 0; i < States.size(); i++) {
            Closure c = States.get(i);
            if (c.contains(item)) {
                action.add(i, "#", -1);
                return;
            }

        }
    }

    public static void analysis(List<Tuple> tuples, List<Closure> States) {
        int num = 1;
        tuples.add(new Tuple("#", -1, "", -1));
        int read = 0;
        Stack<Integer> StatusStack = new Stack<>();
        StatusStack.push(0);
        Stack<String> SymbolStack = new Stack<>();
        while (true) {
            int nowState = StatusStack.peek();
            String input = tuples.get(read).getInput();
            Closure now = States.get(nowState);

            int judge = action.jugde(nowState, input);
            //移进
            if (judge == 1) {
                num = tuples.get(read).getLine();
                int next = action.nextState(nowState, input);
                if (next == -1) {
                    break;
                }
                StatusStack.push(next);
                SymbolStack.push(input);
                read++;
                System.out.println("shift:"+input);
            }
            //规约
            else if (judge == 2) {
                if (action.contain(nowState, input)) {
                    int next = action.nextState(nowState, input);
                    if (next == -1) {
                        break;
                    }
                }
                //reduce
                Production p = action.nextProduction(nowState, input);
                String output = p + "  Line:" + num;
                outputs.add(output);
                System.out.println(p + "  Line:" + num);

                int cnt = p.getRightList().size();
                if (p.getRight().equals("$")){cnt=0;};
                while (cnt-- > 0) {
                    StatusStack.pop();
                    SymbolStack.pop();
                }
                SymbolStack.push(p.getLeft());
                System.out.println("reduce:"+p);
                //goto
                int next = gotoState.nextState(StatusStack.peek(), SymbolStack.peek());
                StatusStack.push(next);
//                System.out.println("goto:"+next);
            }
            else if (judge == 0) {
                while (!SymbolStack.isEmpty() && !SymbolStack.peek().equals("D") && !SymbolStack.peek().equals("S")) {
                    SymbolStack.pop();
                    StatusStack.pop();
//                    cnt++;
                }
                while (!tuples.get(read).getInput().equals(";")) {
                    read++;
                }
                read++;
                String temp = tuples.get(read).getInput();
//                System.out.println();
                System.err.println("Erroe Line:" + num + "   " + temp);
            }
        }
    }

    public static void SemanticAnalysis(List<Tuple> tuples, List<Closure> States) {
        int num = 1;
        tuples.add(new Tuple("#", -1, "", -1));
        int read = 0;
        Stack<Integer> StatusStack = new Stack<>();
        StatusStack.push(0);
        Stack<String> SymbolStack = new Stack<>();
        while (true) {
            int nowState = StatusStack.peek();
            String input = tuples.get(read).getInput();
            Closure now = States.get(nowState);

            int judge = action.jugde(nowState, input);
            //移进
            if (judge == 1) {
                num = tuples.get(read).getLine();
                int next = action.nextState(nowState, input);
                if (next == -1) {
                    break;
                }
                StatusStack.push(next);
                SymbolStack.push(input);
                read++;
            }
            //规约
            else if (judge == 2) {
                if (action.contain(nowState, input)) {
                    int next = action.nextState(nowState, input);
                    if (next == -1) {
                        break;
                    }
                }
                //reduce
                Production p = action.nextProduction(nowState, input);
//                String output=p+"  Line:"+tuples.get(read).getLine();
                String output = p + "  Line:" + num;
                outputs.add(output);
//                System.out.println(p+"  Line:"+tuples.get(read).getLine());
                System.out.println(p + "  Line:" + num);
                int cnt = p.getRightList().size();
                while (cnt-- > 0) {
                    StatusStack.pop();
                    SymbolStack.pop();
                }
                SymbolStack.push(p.getLeft());
                //goto
                int next = gotoState.nextState(StatusStack.peek(), SymbolStack.peek());
                StatusStack.push(next);
            } else if (judge == 0) {
//                int cnt=0;
//                System.out.println("a:"+SymbolStack.peek());
                while (!SymbolStack.isEmpty() && !SymbolStack.peek().equals("D") && !SymbolStack.peek().equals("S")) {
                    SymbolStack.pop();
                    StatusStack.pop();
//                    cnt++;
                }
//                System.out.println(cnt);
//                System.out.println(SymbolStack.size());
//                System.out.println("b:"+SymbolStack.peek());
                while (!tuples.get(read).getInput().equals(";")) {
                    read++;
                }
                read++;
//                System.out.println(tuples.get(read).getInput());
//                System.out.println(StatusStack.size());
                String temp = tuples.get(read).getInput();
//                System.out.println();
                System.err.println("Erroe Line:" + num + "   " + temp);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        GenerateProduction();
        fillingTerminals();
        GenerateFirst();
        List<Closure> States = cluster();
        ConstructLR1Table(States);
        Lexical.Analysis.Main.LexicalAnalysis();
        List<String> token = Lexical.Analysis.Main.WriteList;
        List<String> table = Method.ReadFile("src/lexical/table.txt");

        List<Tuple> tuples = new ArrayList<>();
        for (int i = 0; i < token.size(); i++) {
            String[] arr = token.get(i).split(" ");
            tuples.add(new Tuple(table.get(Integer.valueOf(arr[1])), Integer.valueOf(arr[1]), arr[3], Integer.valueOf(arr[5])));
            System.out.println(tuples.get(i));
        }

        analysis(tuples, States);
        Method.WriteFile("SyntaxResult.txt", outputs);
        System.out.println("Action_Table");
        action.write(States);
        System.out.println("Goto_Table");
        gotoState.write(States);
    }
}
