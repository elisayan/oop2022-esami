package a01b.e2;

import java.util.*;

public class LogicsImpl implements Logics {

    private Set<Pair<Integer, Integer>> enabled = new HashSet<>();
    private final int size;
    private boolean over = false;

    public LogicsImpl(int size) {
        this.size = size;
    }

    private boolean toggle(int x, int y) {
        var pair = new Pair<>(x, y);
        if (enabled.contains(pair)) {
            enabled.remove(pair);
            return false;
        }
        enabled.add(pair);
        return true;
    }

    @Override
    public void hit(int x, int y) {
        Map<Boolean, List<Boolean>> toggles = new HashMap<>();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (Math.abs(x - i) == 1 && Math.abs(y - j) == 1 &&
                        !(i == x && j == y)) {
                    boolean result = toggle(i, j);
                    List<Boolean> list = toggles.get(result);
                    if (list == null) {
                        list = new ArrayList<>();
                        toggles.put(result, list);
                    }
                    list.add(result);
                }
            }
        }

        over = toggles.size() == 2 && toggles.get(true).size() == 1 && toggles.get(false).size() == 3;
    }

    @Override
    public boolean isEnabled(int x, int y) {
        return enabled.contains(new Pair<>(x, y));
    }

    @Override
    public boolean isOver() {
        return over;
    }
}
