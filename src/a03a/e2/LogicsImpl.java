package a03a.e2;

import java.util.*;

public class LogicsImpl implements Logics {

    private final Random random = new Random();
    private final Map<Player, Pair<Integer, Integer>> towers = new HashMap<>();
    private final int size;

    public LogicsImpl(int size) {
        this.size = size;
        reset();
    }

    private Pair<Integer, Integer> randomPosition() {
        return new Pair<Integer, Integer>(random.nextInt(size), random.nextInt(size));
    }

    @Override
    public Pair<Integer, Integer> getPosition(Player player) {
        return towers.get(player);
    }

    @Override
    public void reset() {
        towers.put(Player.HUMAN, randomPosition());

        do {
            towers.put(Player.COMPUTER, randomPosition());
        } while (attack(getPosition(Player.HUMAN), getPosition(Player.COMPUTER)));
    }

    private boolean attack(Pair<Integer, Integer> player1, Pair<Integer, Integer> player2) {
        return !player1.equals(player2) && (player1.getX() == player2.getX() || player1.getY() == player2.getY());
    }

    @Override
    public boolean isOver() {
        return towers.get(Player.COMPUTER).equals(towers.get(Player.HUMAN));
    }

    @Override
    public boolean humanMove(int x, int y) {
        var pair= new Pair<>(x,y);
        if (attack(pair, towers.get(Player.HUMAN))) {
            towers.put(Player.HUMAN, new Pair<Integer,Integer>(x, y));
            return true;
        }
        return false;
    }

    private List<Pair<Integer, Integer>> allAttacked(Pair<Integer, Integer> position) {
        List<Pair<Integer, Integer>> attackedPositions = new ArrayList<>();

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Pair<Integer, Integer> currentPos = new Pair<>(x, y);
                if (attack(currentPos, position)) {
                    attackedPositions.add(currentPos);
                }
            }
        }

        return attackedPositions;
    }

    @Override
    public void computerMove() {
        //la lista delle posizioni che possono essere attaccate dal computer
        var valid = allAttacked(towers.get(Player.COMPUTER));

        if (valid.contains(towers.get(Player.HUMAN))) {
            towers.put(Player.COMPUTER, towers.get(Player.HUMAN));
        } else{
            towers.put(Player.COMPUTER, valid.get(random.nextInt(valid.size())));
        }
        
    }
}
