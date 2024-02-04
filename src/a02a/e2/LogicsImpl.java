package a02a.e2;

import java.util.*;

public class LogicsImpl implements Logics{

    private final int size;
    private List<Pair<Integer, Integer>> bishop = new LinkedList<>();
    private Set<Pair<Integer,Integer>> enabled = new HashSet<>();

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean bishops(int x, int y) {
        var pair = new Pair<Integer,Integer>(x,y);
        if (bishop.contains(pair)) {
            bishop.remove(pair);
            return false;
        }
        bishop.add(pair);
        return true;
    }
    @Override
    public boolean isOver() {
        return enabled.size() + bishop.size() == size * size;
    }

    @Override
    public boolean isEnable(int x, int y) {
        return enabled.contains(new Pair<>(x,y));
    }

    @Override
    public void setEnabled(int x, int y) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Math.abs(x - i) == Math.abs(y - j) && !(x == i && y == j)) {
                    enabled.add(new Pair<Integer,Integer>(i, j));
                }
            }
        }
    }
    
}
