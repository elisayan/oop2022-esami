package a03b.e2;

import java.util.*;

public class LogicsImpl implements Logics {
    private final int size;
    private final Random random = new Random();
    private Map<Pair<Integer, Integer>, Player> players = new HashMap<>();

    enum Player {
        HUMAN, COMPUTER
    }

    public LogicsImpl(int size) {
        this.size = size;
        initialize();
    }

    public void initialize() {
        players.clear();
        for (int i = 0; i < size; i++) {
            players.put(new Pair<Integer, Integer>(i, size - 1), Player.HUMAN);
            players.put(new Pair<Integer, Integer>(i, random.nextInt(2)), Player.COMPUTER);
        }
    }

    @Override
    public Player getPosition(Pair<Integer, Integer> position) {
        return players.get(position);
    }

    private List<Pair<Integer, Integer>> attackSet(int x, int y) {
        List<Pair<Integer, Integer>> attackSet = new LinkedList<>();
        Pair<Integer, Integer> sx = new Pair<>(x - 1, y - 1);
        Pair<Integer, Integer> dx = new Pair<>(x + 1, y - 1);
        if (players.containsKey(sx) || players.containsKey(dx)) {
            if (getPosition(dx) == Player.COMPUTER && getPosition(sx) == Player.COMPUTER) {
                attackSet.add(dx);
                attackSet.add(sx);
            } else if (getPosition(dx) == Player.COMPUTER) {
                attackSet.add(dx);

            } else if (getPosition(sx) == Player.COMPUTER) {
                attackSet.add(sx);
            }
        }
        return attackSet;
    }

    private boolean inGrid(int x, int y) {
        return x >= 0 && y >= 0 && x < size && y < size;
    }

    @Override
    public boolean humanMove(int x, int y) {
        List<Pair<Integer, Integer>> attackSet = attackSet(x, y);
        attackSet.stream().distinct();

        if (players.containsKey(new Pair<>(x, y))) {
            players.remove(new Pair<>(x, y));
            if (attackSet.isEmpty()) {
                var up = new Pair<>(x, y - 1);
                if (getPosition(up) != Player.COMPUTER && inGrid(up.getX(), up.getY()) && getPosition(up) != Player.HUMAN) {

                    players.put(up, Player.HUMAN);
                } else {
                    players.put(new Pair<>(x, y), Player.HUMAN);
                }
                return true;
            }
            var attack = attackSet.get(random.nextInt(attackSet.size()));
            if (inGrid(attack.getX(), attack.getY())) {
                players.put(attack, Player.HUMAN);
            } else {
            
                players.put(new Pair<>(x, y), Player.HUMAN);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isOver() {
        return players.values().stream().distinct().count() == 1;
    }

}
