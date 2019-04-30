package Semantic;

import Lexical.Tool.SymbolTable;
import Syntax.Structure.Production;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class attribute {
    private String symbol;
    private String type;
    private String var;
    private String relop;
    private int quad;
    public static functionTable global_functionTable=new functionTable();
    private static functionTable func_functionTable=new functionTable();
    private static functionTable last=global_functionTable;
    private static boolean func=false;
    private static boolean error=false;
    private static String returnType=null;
    private static String funcName;
    public static List<functionTable> tables=new ArrayList<>();
    private static List<fourTuple> funcTuples=new ArrayList<>();
    private static int t_temp=0;
    private List<Integer> boolTrueList=new ArrayList<>();
    private List<Integer> boolFalseList=new ArrayList<>();
    private List<Integer> N_nextList=new ArrayList<>();
    private List<Integer> S_nextList=new ArrayList<>();
    private Stack<Integer> call=new Stack<>();

    public attribute(String symbol,String posInTable) {
        global_functionTable.table="global";
        this.symbol = symbol;
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
        functionTable functemp=global_functionTable;
        int quad1;
        int quad2;
        List<Integer> list;
//        System.out.println();
//        System.out.println(pos+" "+production);
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
                if (func){
                    functemp=func_functionTable;
                }
                if (functemp.hasType(var)){
                    //使用之前的声明类型
                    System.err.println("ERROR at Line:"+Analysis.LineNum+"  重复声明变量");
                }
                else {
                    functemp.add(var,type);
                }
                break;
            case 5:
                type=attributes.get(0).type;
                id=attributes.get(1).symbol;
                var=attributes.get(1).var;
                if (func){
                    functemp=func_functionTable;
                }
                if (functemp.hasType(var)){
                    //使用之前的声明类型
                    System.err.println("ERROR at Line:"+Analysis.LineNum+"  重复声明语句");
                    break;
                }
                else {
                    functemp.add(var,type);
                }
                if (type.equals(attributes.get(3).type)||type.equals("float")){
                    if (!func) {
//                        System.out.println(func);
                        Analysis.fourTuples.add(new fourTuple("=", attributes.get(3).var, "", var));
//                        System.out.println(Analysis.fourTuples);
                        Analysis.mid++;
                    }
                }
                else if (!func){
                    System.err.println("ERROR at Line:"+Analysis.LineNum+"  类型不匹配且无法转换");
                }
                break;
            case 6:
                break;
            case 7:
                if (func){
                    last=func_functionTable;
                    System.err.println("ERROR at Line:"+Analysis.LineNum+"  嵌套声明函数");
                    error=true;
                    break;
                }
                funcName=attributes.get(1).var;
                func_functionTable.table=funcName;
                returnType=attributes.get(0).type;
                global_functionTable.add(funcName,"func");
//                System.out.println(global_functionTable);
                func=true;
                break;
            case 8:
                if (error){
                    error=false;
                    func_functionTable=last;
                    last=global_functionTable;
                    break;
                }
                func=false;
                if (!returnType.equals(attributes.get(6).type)){
                    System.err.println("ERROR at Line:"+Analysis.LineNum+"  函数声明返回值类型不匹配");
                    returnType=attributes.get(6).type;
                }
                tables.add(func_functionTable);
                func_functionTable=new functionTable();
//                global_functionTable.add(funcName,"func");
//                returnType=attributes.get(6).type;
//                int returnPos=call.pop();
//                Analysis.fourTuples.add(new fourTuple("j","","",""));
//                Analysis.mid++;
                break;
            case 9:
                break;
            case 10:
                if (func){
//                    System.out.println(attributes.get(2).type);
//                    System.out.println(attributes.get(3).var);
                    func_functionTable.add(attributes.get(3).var,attributes.get(2).type);
//                    System.out.println(func_functionTable.id);
//                    System.out.println(func_functionTable.hasType(attributes.get(3).var));
                }
                break;
            case 11:
//                pars.add(new par(attributes.get(0).type,attributes.get(1).var));
                if (func){
//                    System.out.println(attributes.get(0).type);
//                    System.out.println(attributes.get(1).var);
                    func_functionTable.add(attributes.get(1).var,attributes.get(0).type);
                }
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
                this.type="void";
                break;
            case 16:
                this.S_nextList=attributes.get(0).S_nextList;
                break;
            case 17:
                break;
            case 18:
                if (error){
                    break;
                }
                id=attributes.get(0).symbol;
                var=attributes.get(0).var;
                if (func){
                    functemp=func_functionTable;
                }
                if (!functemp.hasType(var)){
                    System.err.println("ERROR at Line:"+Analysis.LineNum+"  变量未声明类型");
                    break;
                }
                String Etype=attributes.get(2).type;
                String idtype=functemp.getType(var);
                if (idtype.equals("int")&&Etype.equals("int")){
                    if (!func){
                    Analysis.fourTuples.add(new fourTuple("=",attributes.get(2).var,"",var));
                    Analysis.mid++;
                    }
//                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
//                    System.out.println(Analysis.mid);
                }
                else if (idtype.equals("int")&&Etype.equals("float")){
                    System.err.println("ERROR at Line:"+Analysis.LineNum+"  类型不匹配且无法转换");
                }
                else if (idtype.equals("float")&&Etype.equals("int")){
//                    System.out.println("Error 类型转换");
                    if (!func){
                    Analysis.fourTuples.add(new fourTuple("=",attributes.get(2).var,"",var));
                    Analysis.mid++;}
//                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
//                    System.out.println(Analysis.mid);
                }
                else if (idtype.equals("float")&&Etype.equals("float")){
                    if (!func) {
                        Analysis.fourTuples.add(new fourTuple("=", attributes.get(2).var, "", var));
                        Analysis.mid++;
                    }
//                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
//                    System.out.println(Analysis.mid);
                }
                else if (idtype.equals("func")){
                    System.err.println("ERROR at Line:"+Analysis.LineNum+"  变量之前作为函数名声明");
                }
                break;
            case 19:
                break;
            case 20:
                break;
            case 21:
                quad1=attributes.get(2).quad;
                if (global_functionTable.map.containsKey(attributes.get(1).var)){
                    call.push(quad1);
                    functionTable notuse=new functionTable();
                    notuse.table=attributes.get(1).var;
                    int temppos=tables.indexOf(notuse);
                    Analysis.fourTuples.addAll(tables.get(temppos).fourTuples);
                    Analysis.mid+=tables.get(temppos).fourTuples.size();
                    Analysis.fourTuples.set(Analysis.fourTuples.size()-1,new fourTuple("j","","",quad1+""));
                    Analysis.fourTuples.add(new fourTuple("j","","",global_functionTable.map.get(attributes.get(1).var)+""));
                    Analysis.mid++;
                }
                else {
                    System.err.println("ERROR at Line:"+Analysis.LineNum+"  函数名未声明");
                }
                break;
            case 22:
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
                if (error){
                    break;
                }
                type1=attributes.get(0).type;
                type2=attributes.get(2).type;
                if (type1.equals("int")&&type2.equals("int")){
                    this.type="int";
                    this.var="t"+t_temp++;
                    if (!func) {
                        Analysis.mid++;
                        Analysis.fourTuples.add(new fourTuple("+", attributes.get(0).var, attributes.get(2).var, this.var));
                    }
//                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
//                    System.out.println(Analysis.mid);
                }
                else if (type1.equals("int")&&type2.equals("float")){
                    this.type="float";
                    this.var="t"+t_temp++;
                    if (!func) {
                        Analysis.mid++;
                        Analysis.fourTuples.add(new fourTuple("+", attributes.get(0).var, attributes.get(2).var, this.var));
                    }
//                    Analysis.mid++;
//                    Analysis.fourTuples.add(new fourTuple("+",attributes.get(0).var,attributes.get(2).var,this.var));
//                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
//                    System.out.println(Analysis.mid);
                }
                else if (type1.equals("float")&&type2.equals("int")){
                    this.type="float";
                    this.var="t"+t_temp++;
                    if (!func) {
                        Analysis.mid++;
                        Analysis.fourTuples.add(new fourTuple("+", attributes.get(0).var, attributes.get(2).var, this.var));
                    }
//                    Analysis.mid++;
//                    Analysis.fourTuples.add(new fourTuple("+",attributes.get(0).var,attributes.get(2).var,this.var));
//                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
//                    System.out.println(Analysis.mid);
                }
                else if (type1.equals("float")&&type2.equals("float")){
                    this.type="float";
                    this.var="t"+t_temp++;
                    if (!func) {
                        Analysis.mid++;
                        Analysis.fourTuples.add(new fourTuple("+", attributes.get(0).var, attributes.get(2).var, this.var));
                    }
//                    Analysis.mid++;
//                    Analysis.fourTuples.add(new fourTuple("+",attributes.get(0).var,attributes.get(2).var,this.var));
                }
                break;
            case 28:
                type1=attributes.get(0).type;
                type2=attributes.get(2).type;
                if (type1.equals("int")&&type2.equals("int")){
                    this.type="int";
                    this.var="t"+t_temp++;
                    if (!func) {
                        Analysis.mid++;
                        Analysis.fourTuples.add(new fourTuple("*", attributes.get(0).var, attributes.get(2).var, this.var));
                    }
//                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
//                    System.out.println(Analysis.mid);
                }
                else if (type1.equals("int")&&type2.equals("float")){
                    this.type="float";
                    this.var="t"+t_temp++;
                    if (!func) {
                        Analysis.mid++;
                        Analysis.fourTuples.add(new fourTuple("*", attributes.get(0).var, attributes.get(2).var, this.var));
                    }
                }
                else if (type1.equals("float")&&type2.equals("int")){
                    this.type="float";
                    this.var="t"+t_temp++;
                    if (!func) {
                        Analysis.mid++;
                        Analysis.fourTuples.add(new fourTuple("*", attributes.get(0).var, attributes.get(2).var, this.var));
                    }
//                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
//                    System.out.println(Analysis.mid);
                }
                else if (type1.equals("float")&&type2.equals("float")){
                    this.type="float";
                    this.var="t"+t_temp++;
                    if (!func) {
                        Analysis.mid++;
                        Analysis.fourTuples.add(new fourTuple("*", attributes.get(0).var, attributes.get(2).var, this.var));
                    }
//                    System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
//                    System.out.println(Analysis.mid);
                }
                break;
            case 29:
                this.type=attributes.get(1).type;
                this.var="t"+t_temp++;
                if (!func) {
                    Analysis.mid++;
                    Analysis.fourTuples.add(new fourTuple("minus", attributes.get(1).var, "", this.var));
                }
                break;
            case 30:
                this.type=attributes.get(1).type;
                this.var="t"+t_temp++;
                if (!func) {
                    Analysis.mid++;
                    Analysis.fourTuples.add(new fourTuple("( )", attributes.get(1).var, "", this.var));
                }
//                System.out.println(Analysis   .fourTuples.get(Analysis.fourTuples.size()-1));
//                System.out.println(Analysis.mid);
                break;
            case 31:
                if (error){
                    break;
                }
                id=attributes.get(0).symbol;
                var=attributes.get(0).var;
                if (func){
                    functemp=func_functionTable;
                }
                if (!functemp.hasType(var)){
//                    System.out.println(var);
                    System.err.println("ERROR at Line:"+Analysis.LineNum+"  变量类型未声明");
                }
//                this.type=attributes.get(0).type;
//                System.out.println(var);
                this.type=functemp.getType(var);
//                System.out.println(attributes.get(0).type);
//                System.out.println(var);
                this.var=var;
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
//                this.var="t"+t_temp++;
                break;
            case 35:
//                this.var="t"+t_temp++;
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
                    Analysis.fourTuples.get(list.get(i)).setResult(quad1+"");
                }
                S_nextList=merge(list,attributes.get(6).S_nextList);
                list=attributes.get(2).boolFalseList;
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(list.get(i)).setResult(Analysis.mid+"");
                }
                break;
            case 41:
                quad1=attributes.get(5).quad;
                list=attributes.get(2).boolTrueList;
//                System.out.println(list);
//                System.out.println(quad1);
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(list.get(i)).setResult(quad1+"");
//                    System.out.println(Analysis.fourTuples.get(list.get(i)));
                }
                quad2=attributes.get(11).quad;
//                System.out.println(quad2);
                list=attributes.get(2).boolFalseList;
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(list.get(i)).setResult(quad2+"");
//                    System.out.println(Analysis.fourTuples.get(list.get(i)));
                }
                this.S_nextList=merge(attributes.get(6).S_nextList,merge(attributes.get(8).N_nextList,attributes.get(12).S_nextList));
//                System.out.println(S_nextList);
                list=attributes.get(2).boolFalseList;
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(list.get(i)).setResult(Analysis.mid+"");
                }
                break;
            case 42:
                list=attributes.get(7).S_nextList;
                quad1=attributes.get(2).quad;
//                System.out.println(quad1);
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(list.get(i)).setResult(quad1+"");
//                    System.out.println(Analysis.fourTuples.get(list.get(i)));
                }
                list=attributes.get(3).boolTrueList;
                quad2=attributes.get(6).quad;
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(list.get(i)).setResult(quad2+"");
//                    System.out.println(Analysis.fourTuples.get(list.get(i)));
                }
                this.S_nextList=attributes.get(3).boolFalseList;
                Analysis.fourTuples.add(new fourTuple("j","","",quad1+""));
                Analysis.mid++;
//                int t=attributes.get(3).boolFalseList.get(0);
                list=attributes.get(3).boolFalseList;
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(list.get(i)).setResult(Analysis.mid+"");
                }
                break;
            case 43:
                break;
            case 44:
                this.type="boolean";
                list=attributes.get(0).boolTrueList;
                quad2=attributes.get(2).quad;
//                System.out.println(quad2);
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(list.get(i)).setResult(quad2+"");
//                    System.out.println(Analysis.fourTuples.get(list.get(i)));
                }
                this.boolFalseList=merge(attributes.get(0).boolFalseList,attributes.get(3).boolFalseList);
                this.boolTrueList=attributes.get(3).boolTrueList;
                break;
            case 45:
                this.type="boolean";
                list=attributes.get(0).boolFalseList;
                quad1=attributes.get(2).quad;
                for (int i=0;i<list.size();i++){
                    Analysis.fourTuples.get(list.get(i)).setResult(quad1+"");
//                    System.out.println(Analysis.fourTuples.get(list.get(i)));
                }
                this.boolTrueList=merge(attributes.get(0).boolTrueList,attributes.get(3).boolTrueList);
                this.boolFalseList=attributes.get(3).boolFalseList;
                break;
            case 46:
                this.type="boolean";
//                System.out.println(Analysis.mid);
                this.boolTrueList.add(Analysis.mid);
                this.boolFalseList.add(Analysis.mid+1);
                relop=attributes.get(1).relop;
                Analysis.fourTuples.add(new fourTuple("j"+relop,attributes.get(0).var,attributes.get(2).var,""));
//                System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                Analysis.fourTuples.add(new fourTuple("j","","",""));
//                System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
                Analysis.mid+=2;
//                System.out.println(Analysis.mid);
//                System.out.println(Analysis.mid);
                break;
            case 47:
                this.type="boolean";
                this.boolTrueList.add(Analysis.mid);
                Analysis.fourTuples.add(new fourTuple("j","","",Analysis.mid+""));
//                System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
//                System.out.println(Analysis.mid);
                Analysis.mid++;
                break;
            case 48:
                this.type="boolean";
                this.boolFalseList.add(Analysis.mid);
                Analysis.fourTuples.add(new fourTuple("j","","",Analysis.mid+""));
//                System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
//                System.out.println(Analysis.mid);
                Analysis.mid++;
                break;
            case 49:
                this.type="boolean";
                this.boolTrueList=attributes.get(1).boolFalseList;
                this.boolFalseList=attributes.get(1).boolTrueList;
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
//                System.out.println(Analysis.fourTuples.get(Analysis.fourTuples.size()-1));
//                System.out.println(Analysis.mid);
                break;
        }
    }
}
