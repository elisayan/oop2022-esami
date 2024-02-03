package a04.e2;

public interface Logics {
    enum Player{
        ROOK, KING
    }

    Pair<Integer,Integer> getPosition (Player player);
    boolean rookMove(int x, int y);
    void kingMove();
    void reset();
    boolean over();
}
