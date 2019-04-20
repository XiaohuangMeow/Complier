package Syntax.Structure;

import java.util.HashSet;
import java.util.Set;

public class Closure {


    private Set<Item> items;

    public Closure(Set<Item> items) {
        this.items = items;
    }

    public Set<Item> ItemContains(String x){
        Set<Item> set=new HashSet<>();
        for (Item item:items) {
            if (item.getRFirst()!=null&&item.getRFirst().equals(x)){
                set.add(item);
            }
        }
        if (set.isEmpty()) return null;
        return set;
    }

    public boolean contains(Item item){
        return items.contains(item);
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Closure closure = (Closure) o;

        return items != null ? items.equals(closure.items) : closure.items == null;
    }

    @Override
    public int hashCode() {
        return items != null ? items.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Closure{" +
                "items=" + items +
                '}';
    }
}
