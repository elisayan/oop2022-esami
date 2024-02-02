package a01b.e2;

import java.util.*;

public class LogicsImpl implements Logics{

    private List<Pair<Integer,Integer>> selected = new LinkedList<>();
    private List<Pair<Integer, Integer>> asterisch = new LinkedList<>();

    @Override
    public boolean hit(int x, int y) {
        setAsterisch(x, y);

        if (selected.containsAll(asterisch)) {
            selected.removeAll(asterisch);
            return false;
        }

        selected.addAll(asterisch);
        return true;
    }

    private void setAsterisch(int x, int y){
        asterisch.clear();
        asterisch.add(new Pair<Integer,Integer>(x-1, y-1));
        asterisch.add(new Pair<Integer,Integer>(x+1, y-1));
        asterisch.add(new Pair<Integer,Integer>(x-1, y+1));
        asterisch.add(new Pair<Integer,Integer>(x+1, y+1));
    }

    @Override
    public boolean isOver() {
        List<Pair<Integer,Integer>> subList = asterisch;

        for (int i = 0; i < asterisch.size(); i++) {
            subList.remove(i);

            // if (selected.containsAll(subList)) {
            //     return true;
            // }
        }


        return false;
    }

    @Override
    public boolean isAsterisch(int x, int y) {
        return selected.contains(new Pair<>(x,y));
    }
    
}
