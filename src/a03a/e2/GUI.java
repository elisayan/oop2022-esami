package a03a.e2;

import javax.swing.*;

import a03a.e2.Logics.Player;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {

    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private final Logics logic;

    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);
        this.logic = new LogicsImpl(size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                var button = (JButton) e.getSource();
                var position = cells.get(button);
                if (logic.humanMove(position.getX(), position.getY())) {
                    if (logic.isOver()) {
                        System.out.println("Player wins");
                        logic.reset();
                    } else {
                        logic.computerMove();
                        if (logic.isOver()) {
                            System.out.println("Computer wins");
                            logic.reset();
                        }
                    }
                    redraw();
                }
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<Integer, Integer>(j, i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }

        redraw();

        this.setVisible(true);
    }

    private void redraw() {
        var playerPosition = logic.getPosition(Player.HUMAN);
        var computerPosition = logic.getPosition(Player.COMPUTER);

        for (var entry : cells.entrySet()) {
            entry.getKey().setText(entry.getValue().equals(playerPosition) ? "*"
                    : entry.getValue().equals(computerPosition) ? "o" : "");
        }
    }

}
