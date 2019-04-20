package Syntax.Structure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Item {

    private String left;
    private String right;
    private List<String> rightList=new ArrayList<>();
    private int pos;
    private int countRight;
    private String lookahead;
//    private Set<String> lookahead=new HashSet<>();

    public int getCountRight() {
        return countRight;
    }

    public boolean isReduce() {
        return pos==countRight;
    }

    public String getRFirst(){
        if (countRight==pos)
            return null;
        return rightList.get(pos);
    }

    public String getLookahead() {
        return lookahead;
    }


//    public Set<String> getLookahead() {
//        return lookahead;
//    }

//    public void addLookahead(String lookahead){
//        this.lookahead.add(lookahead);
//    }
//
//    public void addLookahead(Set<String> lookahead){
//        this.lookahead.addAll(lookahead);
//    }

    public Item(Production production, String lookahead) {
        this.left = production.getLeft();
        this.right=production.getRight();
        this.lookahead=lookahead;
//        this.lookahead.add(lookahead);
        this.rightList=production.getRightList();
        countRight=rightList.size();
        this.pos=0;
    }

//    public Item(Production production, Set<String> lookahead) {
//        this.left = production.getLeft();
//        this.right=production.getRight();
////        this.lookahead=lookahead;
//        this.lookahead=lookahead;
//        this.rightList=production.getRightList();
//        countRight=rightList.size();
//        this.pos=0;
//    }

    public String getRight() {
        return right;
    }

    public Item(Item item) {
        this.left = item.getLeft();
        this.right=item.getRight();
        this.lookahead=item.getLookahead();
        this.rightList=item.getRightList();
        countRight=rightList.size();
        this.pos=item.getPos()+1;
    }

    public List<String> getRightList() {
        return rightList;
    }

    public void setRightList(List<String> rightList) {
        this.rightList = rightList;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (pos != item.pos) return false;
        if (!left.equals(item.left)) return false;
        if (!right.equals(item.right)) return false;
        return lookahead.equals(item.lookahead);
    }

    @Override
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        result = 31 * result + pos;
        result = 31 * result + lookahead.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "left='" + left + '\'' +
                ", right='" + right + '\'' +
                ", rightList=" + rightList +
                ", pos=" + pos +
                ", countRight=" + countRight +
                ", lookahead='" + lookahead + '\'' +
                '}';
    }
}
