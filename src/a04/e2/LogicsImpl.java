package a04.e2;

import java.util.*;

public class LogicsImpl implements Logics {
    private final int size;
    private final Random random = new Random();
    private final Map<Player, Pair<Integer, Integer>> players = new HashMap<>();

    public LogicsImpl(int size) {
        this.size = size;
        reset();
    }

    @Override
    public Pair<Integer, Integer> getPosition(Player player) {
        return players.get(player);
    }

    private Pair<Integer, Integer> setRandomPosition() {
        return new Pair<Integer, Integer>(random.nextInt(size), random.nextInt(size));
    }

    @Override
    public boolean rookMove(int x, int y) {
        var position = new Pair<>(x, y);
        if (attack(position, players.get(Player.ROOK))) {
            players.put(Player.ROOK, position);
            return true;
        }
        return false;
    }

    private boolean attack(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
        return !p1.equals(p2) && (p1.getX() == p2.getX() || p1.getY() == p2.getY());
    }

    private List<Pair<Integer, Integer>> allAttackedList() {
        List<Pair<Integer, Integer>> attackedList = new LinkedList<>();
        Pair<Integer, Integer> king = players.get(Player.KING);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Pair<Integer, Integer> actual = new Pair<Integer, Integer>(i, j);
                if (Math.abs(i - king.getX()) <= 1 && Math.abs(j - king.getY()) <= 1) {
                    attackedList.add(actual);
                }
            }
        }
        return attackedList;
    }

    @Override
    public void kingMove() {
        var valid = allAttackedList();

        if (valid.contains(players.get(Player.ROOK))) {
            players.put(Player.KING, players.get(Player.ROOK));
        } else {
            players.put(Player.KING, valid.get(random.nextInt(valid.size())));
        }
    }

    @Override
    public void reset() {
        players.put(Player.ROOK, setRandomPosition());
        do {
            players.put(Player.KING, setRandomPosition());
        } while (attack(players.get(Player.ROOK), players.get(Player.KING)));
    }

    @Override
    public boolean over() {
        return players.get(Player.KING).equals(players.get(Player.ROOK));
    }
}
