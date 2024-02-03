package a01a.e2;

import java.util.*;
import java.util.stream.Collectors;

public class LogicsImpl implements Logics {

    private final int size;
    private List<Pair<Integer, Integer>> selected = new LinkedList<>();
    private List<Pair<Integer, Integer>> allclick = new LinkedList<>();

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean hit(int x, int y) {
        var pair = new Pair<Integer, Integer>(x, y);
        allclick.add(pair);
        if (selected.contains(pair)) {
            selected.remove(pair);
            return false;
        }
        selected.add(pair);
        return true;
    }

    @Override
    public boolean isOver() {
        if (allclick.size() >= 3) {
            var subList = allclick.subList(allclick.size() - 3, allclick.size());
            if (selected.containsAll(subList)) {
                int minX = subList.stream().map(Pair::getX).mapToInt(x -> x).min().getAsInt();
                int minY = subList.stream().map(Pair::getY).mapToInt(y -> y).min().getAsInt();
                List<Integer> sx = new ArrayList<>(subList.stream().map(Pair::getX).toList());
                List<Integer> sy = new ArrayList<>(subList.stream().map(Pair::getY).toList());
                sx.replaceAll(x->x-minX);
                sy.replaceAll(y->y-minY);
                return inDiagonal(sx) && inDiagonal(sy);
            }
        }
        return false;
    }

    private boolean inDiagonal(List<Integer> list) {
        return list.equals(List.of(0, 1, 2)) || list.equals(List.of(2, 1, 0));
    }

}
