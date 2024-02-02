package a01a.e2;

import java.util.*;

public class LogicsImpl implements Logics {

    private List<Pair<Integer, Integer>> selected = new LinkedList<>();
    private List<Pair<Integer, Integer>> allClick = new LinkedList<>();

    @Override
    public boolean hit(int x, int y) {
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(x, y);
        allClick.add(pair);
        if (selected.contains(pair)) {
            selected.remove(pair);
            return false;
        }

        selected.add(pair);
        return true;
    }

    @Override
    public boolean isOver() {
        if (allClick.size() >= 3) {
            List<Pair<Integer, Integer>> lastThree = allClick.subList(allClick.size() - 3, allClick.size());

            if (selected.containsAll(lastThree)) {
                for (int i = 0; i < lastThree.size() - 1; i++) {
                    Pair<Integer, Integer> current = lastThree.get(i);
                    Pair<Integer, Integer> next = lastThree.get(i + 1);

                    int xDiff = Math.abs(current.getX() - next.getX());
                    int yDiff = Math.abs(current.getY() - next.getY());

                    if (xDiff != 1 || yDiff != 1) {
                        return false; // Almeno una coppia non è in diagonale
                    }
                }
                return true;
            }
        }

        return false;
    }

}
