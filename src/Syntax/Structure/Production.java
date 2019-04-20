package Syntax.Structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Production {

    private String left;
    private String right;
    private List<String> rightList=new ArrayList<>();

    public Production(String left, String right) {
        this.left = left;
        this.right = right;
        String[] rights=right.split(" ");
        for (int i = 0; i < rights.length; i++) {
            rightList.add(rights[i]);
        }
    }

    public String getLeft() {
        return left;
    }

    public List<String> getRightList() {
        return rightList;
    }

    public String getRight() {
        return right;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Production that = (Production) o;

        if (!left.equals(that.left)) return false;
        return right.equals(that.right);
    }

    @Override
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Production{" +
                "left='" + left + '\'' +
                ", right='" + right + '\'' +
                ", rightList=" + rightList +
                '}';
    }
}
