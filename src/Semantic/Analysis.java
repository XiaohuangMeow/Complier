package Semantic;

import Syntax.Analysis.action;
import Syntax.Analysis.gotoState;
import Syntax.Method.Method;
import Syntax.Structure.Closure;
import Syntax.Structure.Production;
import Syntax.Structure.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static Syntax.Analysis.Main.*;

public class Analysis {

    public static List<fourTuple> fourTuples = new ArrayList<>();
    public static int mid = 0;
    public static List<Integer> trueList = new ArrayList<>();
    public static List<Integer> falseList = new ArrayList<>();
//    public static List<Tuple> tuples;

    public static void SemanticAnalysis(List<Tuple> tuples, List<Closure> States) {
        int num = 1;
        tuples.add(new Tuple("#", -1, "", -1));
        int read = 0;
        Stack<Integer> StatusStack = new Stack<>();
        StatusStack.push(0);
        Stack<String> SymbolStack = new Stack<>();
        Stack<attribute> SymbolAttributeStack = new Stack<>();
        while (true) {
            int nowState = StatusStack.peek();
            String input = tuples.get(read).getInput();
            String posInTable=tuples.get(read).getAttribute();
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
                SymbolAttributeStack.push(new attribute(input,posInTable));
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
//                System.out.println(p + "  Line:" + num);
                int cnt = p.getRightList().size();
                List<attribute> attributes = new ArrayList<>();
                if (p.getRight().equals("$")){cnt=0;};
                while (cnt-- > 0) {
                    StatusStack.pop();
                    SymbolStack.pop();
                    attributes.add(SymbolAttributeStack.pop());
                }
                Collections.reverse(attributes);
                SymbolStack.push(p.getLeft());

                SymbolAttributeStack.push(new attribute(p.getLeft(), p, attributes, productions));
                //goto
                int next = gotoState.nextState(StatusStack.peek(), SymbolStack.peek());
                StatusStack.push(next);
            } else if (judge == 0) {
//                int cnt=0;
//                System.out.println("a:"+SymbolStack.peek());
                while (!SymbolStack.isEmpty() && !SymbolStack.peek().equals("D") && !SymbolStack.peek().equals("S")) {
                    SymbolStack.pop();
                    StatusStack.pop();
                    SymbolAttributeStack.pop();
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

    //    public static void SymbolStackOperation(Production p,Stack<attribute> SymbolAttributeStack){
//        int pos=productions.indexOf(p);
//        switch (pos){
//            case 32:
//                SymbolAttributeStack;
//                break;
//
//        }
//    }
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
//            System.out.println(tuples.get(i));
        }

        SemanticAnalysis(tuples, States);
        Method.WriteFile("SyntaxResult.txt", outputs);
        System.out.println("Action_Table");
        action.write(States);
        System.out.println("Goto_Table");
        gotoState.write(States);

        for (int i=0;i<fourTuples.size();i++){
            System.out.println(i+"\t"+fourTuples.get(i));
        }
    }

}
