package Semantic;

import Lexical.Tool.SymbolTable;
import Syntax.Structure.Production;

import java.util.ArrayList;
import java.util.List;

public class attribute {
    private String symbol;
    private String type;
    private String var;
    private String relop;
    private String temp;
    private String bool;
    private int quad;
    private String posInTable;
    private static int t_temp=0;
    private List<Integer> boolTrueList=new ArrayList<>();
    private List<Integer> boolFalseList=new ArrayList<>();
    private List<Integer> N_nextList=new ArrayList<>();
    private List<Integer> S_nextList=new ArrayList<>();

//    private static List<String> fourTuple=new ArrayList<>();
//    private int IntE;
//    private float FloatE;

    public attribute(String symbol,String posInTable) {
        this.symbol = symbol;
        this.posInTable=posInTable;
        if (symbol.equals("id")){this.var=SymbolTable.getString(Integer.valueOf(posInTable));}
    }

    public List<Integer> merge(List<Integer> list1,List<Integer> list2){
        List<Integer> list=new ArrayList<>();
        list.addAll(list1);
        list.addAll(list2);
        return list;
    }

    @Override
    public String toString() {
        return "attribute{" +
                "symbol='" + symbol + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public attribute(String symbol, Production production, List<attribute> attributes, List<Production> productions) {
        this.symbol = symbol;
        int pos=productions.indexOf(production);
        String type;
        String id;
        String E;
        String op;
        String relop;
        String type1;
        String type2;
        String var;
        int quad1;
        int quad2;
        List<Integer> list;
        System.out.println();
        System.out.println(pos+" "+production);
        switch (pos){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                type=attributes.get(0).type;
                id=attributes.get(1).symbol;
                var=attributes.get(1).var;
                if (SymbolTable.hasType(var)){
                    System.out.println("重复声明");
                }
                else {
                    SymbolTable.addIDtype(var,type);
                }
                break;
            case 5:
//                System.out.println(attributes.get(0).symbol);

                type=attributes.get(0).type;
                id=attributes.get(1).symbol;
                var=attributes.get(1).var;
                if (SymbolTable.hasType(var)){
                    System.out.println("重复声明");
                }
                else {
                    SymbolTable.addIDtype(var,type);
                }

                if (type.equals(attributes.get(3).type)){
                    Analysis.fourTuples.add(new fourTuple("=",attributes.get(3).var,"",var));
                    Analysis.mid++;
                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                    System.out.println(Analysis.mid);
                }
                else {
                    System.out.println("不匹配");
                }
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                this.type=attributes.get(0).type;
                break;
            case 13:
                this.type="int";
                break;
            case 14:
                this.type="float";
                break;
            case 15:
                this.type="char";
                break;
            case 16:
                this.S_nextList=attributes.get(0).S_nextList;
                break;
            case 17:
                break;
            case 18:
                id=attributes.get(0).symbol;
                var=attributes.get(0).var;
                if (!SymbolTable.hasType(var)){
                    System.out.println("ERROR");
                    System.out.println("没有类型的变量");
                }
                String Etype=attributes.get(2).type;
                String idtype=SymbolTable.getType(var);
                if (idtype.equals("int")&&Etype.equals("int")){
//                    SymbolTable.addValue(id,attributes.get(2).value);
//                    this.temp=attributes.get(2).temp;
                    Analysis.fourTuples.add(new fourTuple("=",attributes.get(2).var,"",var));
                    Analysis.mid++;
                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                    System.out.println(Analysis.mid);
                }
                else if (idtype.equals("int")&&Etype.equals("float")){
                    System.out.println("Error 类型出错");
                }
                else if (idtype.equals("float")&&Etype.equals("int")){
//                    float temp=attributes.get(2).value;
                    System.out.println("Error 类型转换");
//                    this.temp=attributes.get(2).temp;
                    Analysis.fourTuples.add(new fourTuple("=",attributes.get(2).var,"",var));
                    Analysis.mid++;
                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                    System.out.println(Analysis.mid);
//                    SymbolTable.addValue(id,attributes.get(2).value);
                }
                else if (idtype.equals("float")&&Etype.equals("float")){
//                    this.temp=attributes.get(2).temp;
                    Analysis.fourTuples.add(new fourTuple("=",attributes.get(2).var,"",var));
                    Analysis.mid++;
                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                    System.out.println(Analysis.mid);
//                    SymbolTable.addValue(id,attributes.get(2).value2);
                }
                break;
            case 19:
                break;
            case 20:
                break;
            case 21:
                break;
            case 23:
                break;
            case 24:
                break;
            case 25:
                break;
            case 26:
                break;
            case 27:
                type1=attributes.get(0).type;
                type2=attributes.get(2).type;
                if (type1.equals("int")&&type2.equals("int")){
                    this.type="int";
                    this.var="t"+t_temp++;
                    Analysis.mid++;
                    Analysis.fourTuples.add(new fourTuple("+",attributes.get(0).var,attributes.get(2).var,this.var));
                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                    System.out.println(Analysis.mid);
                }
                else if (type1.equals("int")&&type2.equals("float")){
                    this.type="float";
                    this.var="t"+t_temp++;
                    Analysis.mid++;
                    Analysis.fourTuples.add(new fourTuple("+",attributes.get(0).var,attributes.get(2).var,this.var));
                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                    System.out.println(Analysis.mid);
                }
                else if (type1.equals("float")&&type2.equals("int")){
                    this.type="float";
                    this.var="t"+t_temp++;
                    Analysis.mid++;
                    Analysis.fourTuples.add(new fourTuple("+",attributes.get(0).var,attributes.get(2).var,this.var));
                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                    System.out.println(Analysis.mid);
                }
                else if (type1.equals("float")&&type2.equals("float")){
                    this.type="float";
                    this.var="t"+t_temp++;
                    Analysis.mid++;
                    Analysis.fourTuples.add(new fourTuple("+",attributes.get(0).var,attributes.get(2).var,this.var));
                }
                break;
            case 28:
                type1=attributes.get(0).type;
                type2=attributes.get(2).type;
                if (type1.equals("int")&&type2.equals("int")){
                    this.type="int";
                    this.var="t"+t_temp++;
                    Analysis.mid++;
                    Analysis.fourTuples.add(new fourTuple("*",attributes.get(0).var,attributes.get(2).var,this.var));
                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                    System.out.println(Analysis.mid);
                }
                else if (type1.equals("int")&&type2.equals("float")){
                    this.type="float";
                    this.var="t"+t_temp++;
                    Analysis.mid++;
                    Analysis.fourTuples.add(new fourTuple("*",attributes.get(0).var,attributes.get(2).var,this.var));
                }
                else if (type1.equals("float")&&type2.equals("int")){
                    this.type="float";
                    this.var="t"+t_temp++;
                    Analysis.mid++;
                    Analysis.fourTuples.add(new fourTuple("*",attributes.get(0).var,attributes.get(2).var,this.var));
                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                    System.out.println(Analysis.mid);
                }
                else if (type1.equals("float")&&type2.equals("float")){
                    this.type="float";
                    this.var="t"+t_temp++;
                    Analysis.mid++;
                    Analysis.fourTuples.add(new fourTuple("*",attributes.get(0).var,attributes.get(2).var,this.var));
                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                    System.out.println(Analysis.mid);
                }
                break;
            case 29:
                this.type=attributes.get(1).type;
                this.var="t"+t_temp++;
                Analysis.mid++;
                Analysis.fourTuples.add(new fourTuple("minus",attributes.get(1).var,"",this.var));
//                if (this.type.equals("int")){
//                    this.value=-attributes.get(1).value;
//                }
//                else if (this.type.equals("float")){
//                    this.value2=-attributes.get(1).value2;
//                }
                break;
            case 30:
                this.type=attributes.get(1).type;
                this.var="t"+t_temp++;
                Analysis.mid++;
                Analysis.fourTuples.add(new fourTuple("( )",attributes.get(1).var,"",this.var));
                System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                System.out.println(Analysis.mid);
                break;
            case 31:
                id=attributes.get(0).symbol;
                var=attributes.get(0).var;
                if (!SymbolTable.hasType(var)){
                    System.out.println("ERROR 未声明");
                }
                this.type=attributes.get(0).type;
                this.type=SymbolTable.getType(var);
                this.var=var;
//                Analysis.fourTuples.add(new fourTuple("",attributes.get(1).temp,"",this.temp));
//                if (this.type.equals("int")){
//                    this.value=attributes.get(0).value;
//                }
//                else if (this.type.equals("float")){
//                    this.value=attributes.get(0).value;
//                }
                break;
            case 32:
                this.type="int";
                this.var=SymbolTable.constInt.get(SymbolTable.intConstNum++);
                break;
            case 33:
                this.type="float";
                this.var=SymbolTable.constFloat.get(SymbolTable.floatConstNum++);
                break;
            case 34:
                break;
            case 35:
                break;
            case 36:
                break;
            case 37:
                break;
            case 38:
                this.S_nextList=attributes.get(0).S_nextList;
                break;
            case 39:
                this.S_nextList=attributes.get(0).S_nextList;
                break;
            case 40:
                list=attributes.get(2).boolTrueList;
                quad1=attributes.get(5).quad;
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(i).setResult(quad1+"");
                }
                S_nextList=merge(list,attributes.get(6).S_nextList);
                break;
            case 41:
                quad1=attributes.get(5).quad;
                list=attributes.get(2).boolTrueList;
                System.out.println(list);
                System.out.println(quad1);
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(list.get(i)).setResult(quad1+"");
                    System.out.println(Analysis.fourTuples.get(list.get(i)));
                }
                quad2=attributes.get(11).quad;
                System.out.println(quad2);
                list=attributes.get(2).boolFalseList;
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(list.get(i)).setResult(quad2+"");
                    System.out.println(Analysis.fourTuples.get(list.get(i)));
                }
                this.S_nextList=merge(attributes.get(6).S_nextList,merge(attributes.get(8).N_nextList,attributes.get(12).S_nextList));
                System.out.println(S_nextList);
                break;
            case 42:
                list=attributes.get(7).S_nextList;
                quad1=attributes.get(2).quad;
                System.out.println(quad1);
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(list.get(i)).setResult(quad1+"");
                    System.out.println(Analysis.fourTuples.get(list.get(i)));
                }
                list=attributes.get(3).boolTrueList;
                quad2=attributes.get(6).quad;
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(list.get(i)).setResult(quad2+"");
                    System.out.println(Analysis.fourTuples.get(list.get(i)));
                }
                this.S_nextList=attributes.get(3).boolFalseList;
                Analysis.fourTuples.add(new fourTuple("j","","",quad1+""));
                Analysis.mid++;
                break;
            case 43:
                break;
            case 44:
                this.type="boolean";
                this.bool=attributes.get(0).bool+"&&"+attributes.get(2).bool;
                list=attributes.get(0).boolTrueList;
                quad2=attributes.get(2).quad;
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(i).setResult(quad2+"");
                    System.out.println(Analysis.fourTuples.get(i));
                }
                this.boolFalseList=merge(attributes.get(0).boolTrueList,attributes.get(3).boolTrueList);
                this.boolTrueList=attributes.get(3).boolFalseList;
                break;
            case 45:
                this.type="boolean";
                this.bool=attributes.get(0).bool+"||"+attributes.get(2).bool;
                list=attributes.get(0).boolFalseList;
                quad1=attributes.get(2).quad;
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(i).setResult(quad1+"");
                    System.out.println(Analysis.fourTuples.get(i));
                }
                this.boolTrueList=merge(attributes.get(0).boolTrueList,attributes.get(3).boolTrueList);
                this.boolFalseList=attributes.get(3).boolFalseList;
                break;
            case 46:
                this.type="boolean";
//                this.temp="t"+Analysis.mid;
                this.bool=attributes.get(0).bool+attributes.get(1).relop+attributes.get(2).bool;
                System.out.println(Analysis.mid);
                this.boolTrueList.add(Analysis.mid);
                this.boolFalseList.add(Analysis.mid+1);
                relop=attributes.get(1).relop;
                Analysis.fourTuples.add(new fourTuple("j"+relop,attributes.get(0).var,attributes.get(2).var,""));
                System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                Analysis.fourTuples.add(new fourTuple("j","","",""));
                System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                Analysis.mid+=2;
                System.out.println(Analysis.mid);
                System.out.println(Analysis.mid);
//                Analysis.mid++;
//                Analysis.fourTuples.add(new fourTuple(attributes.get(1).relop,attributes.get(0).temp,attributes.get(2).temp,this.temp));
                break;
            case 47:
                this.type="boolean";
                this.temp="true";
                this.bool="true";
                this.boolTrueList.add(Analysis.mid);
                Analysis.fourTuples.add(new fourTuple("j","","",Analysis.mid+""));
                System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                System.out.println(Analysis.mid);
                Analysis.mid++;
                break;
            case 48:
                this.type="boolean";
                this.temp="false";
                this.bool="false";
                this.boolFalseList.add(Analysis.mid);
                Analysis.fourTuples.add(new fourTuple("j","","",Analysis.mid+""));
                System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                System.out.println(Analysis.mid);
                Analysis.mid++;
                break;
            case 49:
                this.type="boolean";
                this.bool="!"+attributes.get(1).bool;
                this.boolTrueList=attributes.get(1).boolFalseList;
                this.boolFalseList=attributes.get(1).boolTrueList;
//                Analysis.mid++;
//                Analysis.fourTuples.add(new fourTuple("!",attributes.get(1).temp,"",this.temp));
                break;
            case 50:
                this.relop="<";
                break;
            case 51:
                this.relop="<=";
                break;
            case 52:
                this.relop="==";
                break;
            case 53:
                this.relop="!=";
                break;
            case 54:
                this.relop=">";
                break;
            case 55:
                this.relop=">=";
                break;
            case 56:
                this.quad=Analysis.mid;
                break;
            case 57:
                this.N_nextList.add(Analysis.mid);
                Analysis.fourTuples.add(new fourTuple("j","","",""));
                Analysis.mid++;
                System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                System.out.println(Analysis.mid);
                break;
        }
    }
}
