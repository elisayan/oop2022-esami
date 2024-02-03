package a03b.e2;

public interface Logics {

    enum Player{
        HUMAN, COMPUTER
    }

    void reset();
    boolean humanMove(int x, int y);
    boolean over();
    boolean hasPiece(Player player, int x, int y);
}
