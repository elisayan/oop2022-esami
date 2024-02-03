package a03a.e2;

public interface Logics {
    enum Player {
        HUMAN, COMPUTER
    }

    Pair<Integer,Integer> getPosition(Player player);
    boolean attacks(Pair<Integer,Integer> p1, Pair<Integer,Integer> p2);
    void reset();
    boolean humanMove(int x, int y);
    void computerMove();
    boolean isOver();
}
