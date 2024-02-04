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

    private Pair<Integer, Integer> setRandom() {
        return new Pair<Integer, Integer>(random.nextInt(size), random.nextInt(size));
    }

    @Override
    public Pair<Integer, Integer> getPosition(Player player) {
        return players.get(player);
    }

    private boolean attack(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
        return !(p1.equals(p2)) && (p1.getX() == p2.getX() || p1.getY() == p2.getY());
    }

    @Override
    public void reset() {
        players.put(Player.KING, setRandom());
        do {
            players.put(Player.ROOT, setRandom());
        } while (attack(players.get(Player.KING), players.get(Player.ROOT)));
    }

    @Override
    public boolean humanMove(int x, int y) {
        var position = new Pair<>(x, y);
        if (attack(position, players.get(Player.ROOT))) {
            if (sameLine(x, players.get(Player.ROOT).getX()) &&
                    sameLine(x, players.get(Player.KING).getX()) &&
                    !isJumpOverKing(y, players.get(Player.KING).getY(), players.get(Player.ROOT).getY())) {

                return false;
            }
            if (sameLine(y, players.get(Player.ROOT).getY()) &&
                    sameLine(y, players.get(Player.KING).getY()) &&
                    !isJumpOverKing(x, players.get(Player.KING).getX(), players.get(Player.ROOT).getX())) {
                return false;
            }
        }
        players.put(Player.ROOT, position);
        return true;
    }

    private boolean sameLine(int p1, int p2) {
        return p1 == p2;
    }

    private boolean isJumpOverKing(int position, int k, int r) {
        return position > r ? position > k && k > r : position < k && k < r;
    }

    private List<Pair<Integer, Integer>> allAttackedList() {
        List<Pair<Integer, Integer>> allAttackedList = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Math.abs(players.get(Player.KING).getX() - i) <= 1 &&
                        Math.abs(players.get(Player.KING).getY() - j) <= 1 &&
                        !(i == players.get(Player.KING).getX() && j == players.get(Player.KING).getY())) {
                    allAttackedList.add(new Pair<Integer, Integer>(i, j));
                }
            }
        }

        return allAttackedList;
    }

    @Override
    public void computerMove() {
        List<Pair<Integer, Integer>> allAttackedList = allAttackedList();

        if (allAttackedList.contains(players.get(Player.ROOT))) {
            players.put(Player.KING, players.get(Player.ROOT));
        } else {
            players.put(Player.KING, allAttackedList.get(random.nextInt(allAttackedList.size())));
        }
    }

    @Override
    public boolean isOver() {
        return players.get(Player.KING).equals(players.get(Player.ROOT));
    }
}
