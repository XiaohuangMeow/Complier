package Semantic;

public class fourTuple {

    private String op;
    private String arg1;
    private String arg2;
    private String result;

    @Override
    public String toString() {
        if (arg1.equals("")){arg1="_";};
        if (arg2.equals("")){arg2="_";};
        return "("+op+","+arg1+","+arg2+","+result+")";

    }

    public fourTuple(String op, String arg1, String arg2, String result) {
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
