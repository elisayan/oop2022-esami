package a02a.e2;

import java.util.*;

public class LogicsImpl implements Logics {
    private final int size;
    private Set<Pair<Integer, Integer>> bishops = new HashSet<>();
    private Set<Pair<Integer, Integer>> enabled = new HashSet<>();

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public boolean setBishop(int x, int y) {
        bishops.add(new Pair<Integer, Integer>(x, y));
        setEnabled(x, y);
        return true;
    }

    private void setEnabled(int x, int y) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!(i == x && j == y)) {
                    if (Math.abs(x - i) == Math.abs(y - j)) {
                        enabled.add(new Pair<>(i, j));
                    }
                }
            }
        }
    }

    @Override
    public boolean isEnabled(int x, int y) {
        return !enabled.contains(new Pair<>(x, y));
    }

    @Override
    public boolean isOver() {
        return bishops.size() + enabled.size() == size * size;
    }

}
