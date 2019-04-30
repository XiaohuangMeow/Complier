package Semantic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class functionTable {
    String table;
    int next;
    List<String> id=new ArrayList<>();
    List<String> type=new ArrayList<>();
    List<Integer> offsets=new ArrayList<>();
    int offset=0;
    Map<String,Integer> map=new HashMap<>();
    List<fourTuple> fourTuples=new ArrayList<>();

    public void add(String id,String type){
        this.id.add(id);
        this.type.add(type);
        this.offsets.add(this.offset);
        if (type.equals("int")||type.equals("float")){
            offset+=4;
        }
        else if (type.equals("func")){
            offset+=1;
        }
    }

//    public void insert(String id,String type){
//        this.id.add(0,id);
//        this.type.add(0,type);
//        int off=0;
//        if (type.equals("int")||type.equals("float")){
//            off=4;
//        }
//        else if (type.equals("func")){
//            off=1;
//        }
//        this.offsets.add(0,0);
//        for (int i=1;i<offsets.size();i++){
//            offsets.set(i,offsets.get(i)+off);
//        }
//    }

    public String getType(String id){
        int pos=this.id.indexOf(id);
        return type.get(pos);
    }

    public boolean hasType(String id){
        return this.id.contains(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        functionTable that = (functionTable) o;

        return table != null ? table.equals(that.table) : that.table == null;
    }

    @Override
    public String toString() {
        String s="";
        s+=table+":\n";
        s+="var\t\ttype\t\toffset\n";
        for (int i=0;i<id.size();i++){
            s+=id.get(i)+"\t\t"+type.get(i)+"\t\t"+offsets.get(i)+"\n";
        }
        return s;
    }

    @Override
    public int hashCode() {
        return table != null ? table.hashCode() : 0;
    }
}
