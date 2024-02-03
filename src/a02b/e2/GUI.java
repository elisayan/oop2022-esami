package a02b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    
    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
    private final Logics logic;
    private boolean toInit = false;

    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        this.logic= new LogicsImpl();
        
        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        JButton check = new JButton("Check > Restart");
        main.add(BorderLayout.SOUTH, check);

        check.addActionListener(e->{
            if (toInit) {
                logic.restart();
                this.clear();
                toInit=false;
            }else{
                var res = this.logic.checkThree();
                res.ifPresent(set->{
                    this.disable(set);
                    toInit=true;
                });
            }
        });
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = cells.get(button);
                button.setText(logic.hit(position.getX(), position.getY())?"*":"");
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
        this.setVisible(true);
    }   
    
    private void clear() {
        this.cells.keySet().forEach(b -> {
            b.setText(" ");
            b.setEnabled(true);
        });
    }

    private void disable(Set<Pair<Integer, Integer>> set) {
        for (var entry: cells.entrySet()){
            if (set.contains(entry.getValue())){
                entry.getKey().setEnabled(false);
            }
        }
    }
}
