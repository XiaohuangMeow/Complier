package Syntax.Structure;

public class Tuple {

    private String input;
    private int type;
    private String attribute;
    private int line;

    public Tuple(String input, int type, String attribute,int line) {
        if (input.contains("_")){
            input=input.substring(1);
        }
        this.input = input;
        this.type = type;
        this.attribute = attribute;
        this.line=line;
    }

    public int getLine() {
        return line;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "input='" + input + '\'' +
                ", type=" + type +
                ", attribute='" + attribute + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple tuple = (Tuple) o;

        if (type != tuple.type) return false;
        if (input != null ? !input.equals(tuple.input) : tuple.input != null) return false;
        return attribute != null ? attribute.equals(tuple.attribute) : tuple.attribute == null;
    }

    @Override
    public int hashCode() {
        int result = input != null ? input.hashCode() : 0;
        result = 31 * result + type;
        result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
        return result;
    }
}
