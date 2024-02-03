package a03b.e2;

import javax.swing.*;

import a03b.e2.Logics.Player;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    private final Logics logics;

    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        this.logics = new LogicsImpl(size);

        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.get(button);
                if (logics.humanMove(position.getX(), position.getY())) {
                    if (logics.over()) {
                        logics.reset();
                    }
                    redraw();
                }
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<Integer,Integer>(j, i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        redraw();
        this.setVisible(true);
    }

    private void redraw() {
        for (var entry : cells.entrySet()) {
            entry.getKey().setText(logics.hasPiece(Player.HUMAN, entry.getValue().getX(), entry.getValue().getY()) ? "*"
                    : logics.hasPiece(Player.COMPUTER, entry.getValue().getX(), entry.getValue().getY()) ? "o" 
                    : "");
        }
    }
}
