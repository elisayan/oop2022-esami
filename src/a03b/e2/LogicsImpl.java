package a03b.e2;

import java.util.*;

public class LogicsImpl implements Logics {

    private final int size;
    private final Random random = new Random();
    private final Map<Pair<Integer, Integer>, Player> positions = new HashMap<>();

    public LogicsImpl(int size) {
        this.size = size;
        reset();
    }

    @Override
    public void reset() {
        positions.clear();
        for (int i = 0; i < size; i++) {
            positions.put(new Pair<Integer, Integer>(i, random.nextInt(2)), Player.COMPUTER);
            positions.put(new Pair<Integer, Integer>(i, size - 1), Player.HUMAN);
        }
    }

    @Override
    public boolean over() {
        return positions.values().stream().distinct().count() == 1;
    }

    @Override
    public boolean hasPiece(Player player, int x, int y) {
        return Optional.ofNullable(positions.get(new Pair<>(x, y))).filter(pl -> pl == player).isPresent();
    }

    private boolean validPosition(Pair<Integer, Integer> position) {
        return (position.getX() >= 0 && position.getY() >= 0 && position.getY() < size && position.getX() < size);
    }

    private List<Pair<Integer, Integer>> moves(Pair<Integer, Integer> position) {
        List<Pair<Integer, Integer>> moves = new ArrayList<>();

        for (int xMove : new int[] { 1, -1 }) {
            for (int yMove : new int[] { -1, -1 }) {
                var newPosition = new Pair<>(position.getX() + xMove, position.getY() + yMove);
                if (validPosition(newPosition) && positions.containsKey(newPosition)
                        && !positions.get(newPosition).equals(Player.HUMAN)) {
                    moves.add(newPosition);
                }
            }
        }

        if (moves.isEmpty()) {
            var newPosition = new Pair<>(position.getX(), position.getY() - 1);
            if (validPosition(newPosition) && !positions.containsKey(newPosition)) {
                moves.add(newPosition);
            }
        }

        return moves;
    }

    @Override
    public boolean humanMove(int x, int y) {
        Pair<Integer, Integer> position = new Pair<Integer, Integer>(x, y);
        var player = positions.get(position);
        var moves = moves(position);

        if (moves.isEmpty()) {
            return false;
        }

        Collections.rotate(moves, random.nextInt(moves.size()));
        var newMove = moves.get(0);
        positions.remove(position);
        positions.put(newMove, player);
        return true;
    }

}
