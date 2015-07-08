/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author JAY
 */
public class CultureChoice extends JFrame{
    private int choice;
    
    public CultureChoice(){
        super();
        choice = 0; // not selected any culture
        this.setTitle("Choose Your Race");
        this.getContentPane().setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(400, 100);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        JPanel panel = new JPanel();
        JButton egyptian = new JButton("Egyptian");
        egyptian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = Constants.egyptian;
            }
        });
        panel.add(egyptian);
        JButton greek = new JButton("Greek");
        greek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = Constants.greek;
            }
        });
        panel.add(greek);
        JButton norse = new JButton("Norse");
        norse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = Constants.norse;
            }
        });
        panel.add(norse);
        this.add(panel);
        this.setVisible(true);
    }
    /**
     * no need for setChoice
     * @return 
     */
    
    public int getChoice(){
        return choice;
    }
}
