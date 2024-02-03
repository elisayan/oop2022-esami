package a02b.e2;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

public class LogicsImpl implements Logics {

    private List<Pair<Integer, Integer>> selected = new LinkedList<>();

    @Override
    public boolean hit(int x, int y) {
        Pair<Integer, Integer> pair = new Pair<Integer, Integer>(x, y);

        if (selected.contains(pair)) {
            selected.remove(pair);
            return false;
        }
        selected.add(pair);
        return true;
    }

    @Override
    public boolean check() {
        if (selected.size() >= 3) {
            Optional<Set<Pair<Integer, Integer>>> diagonal = checkThree();
            diagonal.ifPresent(this::disableDiagonal);
            return diagonal.isPresent();
        }
        return false;
    }

    // private Optional<Set<Pair<Integer, Integer>>> checkThree(Function<Pair<Integer,Integer>,Integer> func) {
    //     return selected
    //             .stream()
    //             .collect(Collectors.groupingBy(func::apply, Collectors.counting()))
    //             .entrySet()
    //             .stream()
    //             .filter(entry -> entry.getValue() == 3)
    //             .map(entry -> entry.getKey())
    //             .findAny()
    //             .map(invariant -> selected.stream().filter(p -> p.getX() - p.getY() == invariant).collect(Collectors.toSet()));
    // }

    private Optional<Set<Pair<Integer, Integer>>> checkThree(Function<Pair<Integer, Integer>, Integer> func) {
        Map<Integer, Long> counts = new HashMap<>();
        
        for (Pair<Integer, Integer> p : selected) {
            int invariant = func.apply(p);
            counts.put(invariant, counts.getOrDefault(invariant, 0L) + 1);
        }
    
        for (Map.Entry<Integer, Long> entry : counts.entrySet()) {
            if (entry.getValue() == 3) {
                int invariant = entry.getKey();
                Set<Pair<Integer, Integer>> diagonal = new HashSet<>();
                
                for (Pair<Integer, Integer> p : selected) {
                    if (func.apply(p) == invariant) {
                        diagonal.add(p);
                    }
                }
    
                return Optional.of(diagonal);
            }
        }
        return Optional.empty();
    }

    private void disableDiagonal(Set<Pair<Integer, Integer>> diagonal) {
        selected.removeAll(diagonal);
    }

    @Override
    public Optional<Set<Pair<Integer, Integer>>> checkThree() {
        return checkThree(p -> p.getX()-p.getY()); // X-Y is an invariant for cells in the same diagonal
    }

    @Override
    public void restart() {
        selected.clear();
    }
}
