package a03b.e2;

import a03b.e2.LogicsImpl.Player;

public interface Logics {
    Player getPosition(Pair<Integer,Integer> position);
    boolean humanMove(int x, int y);
    boolean isOver();
    void initialize();
}
