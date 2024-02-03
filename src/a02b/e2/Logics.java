package a02b.e2;

import java.util.Optional;
import java.util.Set;

public interface Logics {
    boolean hit (int x, int y);
    boolean check();
    void restart();
    Optional<Set<Pair<Integer, Integer>>> checkThree();
}
