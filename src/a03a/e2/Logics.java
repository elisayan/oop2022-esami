package a03a.e2;

public interface Logics {

    enum Player {
        HUMAN, COMPUTER
    }

    Pair<Integer, Integer> getPosition(Player player);

    boolean humanMove(int x, int y);

    void computerMove();

    void reset();

    boolean isOver();
}
