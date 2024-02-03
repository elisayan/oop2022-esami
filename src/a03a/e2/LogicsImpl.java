package a03a.e2;

import java.util.*;

public class LogicsImpl implements Logics{

    private final int size;
    private final Random random = new Random();
    private final Map<Player,Pair<Integer,Integer>> players = new HashMap<>();

    public LogicsImpl(int size) {
        this.size = size;
        reset();
    }

    private Pair<Integer, Integer> setRandomPosition(){
        return new Pair<Integer,Integer>(random.nextInt(size), random.nextInt(size));
    }    

    @Override
    public Pair<Integer, Integer> getPosition(Player player) {
        return players.get(player);
    }

    @Override
    public boolean attacks(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
        return !p1.equals(p2) && (p1.getX() == p2.getX() || p1.getY() == p2.getY());
    }

    @Override
    public void reset() {
        players.put(Player.HUMAN, setRandomPosition());

        do {
            players.put(Player.COMPUTER, setRandomPosition());
        } while (attacks(getPosition(Player.COMPUTER), getPosition(Player.HUMAN)));
    }

    @Override
    public boolean humanMove(int x, int y) {
        var position = new Pair<Integer,Integer>(x,y);
        if (attacks(players.get(Player.HUMAN), position)) {
            players.put(Player.HUMAN, position);
            return true;
        }
        return false;
    }

    private List<Pair<Integer,Integer>> allAttackedList(){
        List<Pair<Integer,Integer>> attacksList = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                var actual = new Pair<>(i,j);
                if (attacks(players.get(Player.COMPUTER), actual)) {
                    attacksList.add(actual);
                }
            }
        }

        return attacksList;
    }

    @Override
    public void computerMove() {
        List<Pair<Integer,Integer>> valid = allAttackedList();
        if (valid.contains(players.get(Player.HUMAN))) {
            players.put(Player.COMPUTER, players.get(Player.HUMAN));
        } else{
            players.put(Player.COMPUTER, valid.get(random.nextInt(valid.size())));
        }
    }

    @Override
    public boolean isOver() {
        return players.get(Player.COMPUTER).equals(players.get(Player.HUMAN));
    }  
    
}
