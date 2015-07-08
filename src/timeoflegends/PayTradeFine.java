/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Eric
 */
public class PayTradeFine extends JFrame implements ActionListener, ItemListener{
    
    private boolean paid;
    private final int fine;
    private final JComboBox playerFood;
    private final JComboBox playerFavor;
    private final JComboBox playerWood;
    private final JComboBox playerGold;
    private JButton pay;
    
    public PayTradeFine(int fine){
        this.fine = fine;
        paid = false;
        setLayout(new FlowLayout());
        playerFood = new JComboBox();
        playerFood.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= Math.min(PlayerHolder.getCurrentPlayer().getResource().getNumOfFood(), fine); i++){
            playerFood.addItem(i);
            playerFood.setSelectedIndex(0);
        }
        playerFavor = new JComboBox();
        playerFavor.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= Math.min(PlayerHolder.getCurrentPlayer().getResource().getNumOfFavor(), fine); i++){
            playerFavor.addItem(i);
            playerFavor.setSelectedIndex(0);
        }
        playerWood = new JComboBox();
        playerWood.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= Math.min(PlayerHolder.getCurrentPlayer().getResource().getNumOfWood(), fine); i++){
            playerWood.addItem(i);
            playerWood.setSelectedIndex(0);
        }
        playerGold = new JComboBox();
        playerGold.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= Math.min(PlayerHolder.getCurrentPlayer().getResource().getNumOfGold(), fine); i++){
            playerGold.addItem(i);
            playerGold.setSelectedIndex(0);
        }
        JLabel food = new JLabel("Food: ");
        add(food);
        add(playerFood);
        JLabel favor = new JLabel("Favor: ");
        add(favor);
        add(playerFavor);
        JLabel wood = new JLabel("Wood: ");
        add(wood);
        add(playerWood);
        JLabel gold = new JLabel("Gold: ");
        add(gold);
        add(playerGold);
        pay = new JButton("Pay");
        pay.addActionListener(this);
        add(pay);
        pack();
        setTitle("Pay "+fine+" Recources for Trade");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int x = playerFood.getSelectedIndex()+
                playerFavor.getSelectedIndex()+
                playerWood.getSelectedIndex()+
                playerGold.getSelectedIndex();
        if(x == fine){
            paid = true;
        }
    }
    
    public boolean paidOff(){
        return paid;
    }
    
    public ArrayList<Integer> getResults(){
        ArrayList<Integer> ret = new ArrayList();
        ret.add(playerFood.getSelectedIndex());
        ret.add(playerFavor.getSelectedIndex());
        ret.add(playerWood.getSelectedIndex());
        ret.add(playerGold.getSelectedIndex());
        return ret;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        
    }
}
