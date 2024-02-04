package a04.e2;

public interface Logics {
    enum Player {
        KING, ROOT
    }

    Pair<Integer,Integer> getPosition(Player player);
    void reset();
    boolean humanMove(int x, int y);
    void computerMove();
    boolean isOver();
}
