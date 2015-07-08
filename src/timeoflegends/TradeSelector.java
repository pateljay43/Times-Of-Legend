/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Eric
 */
public class TradeSelector extends JFrame implements ActionListener{
    private final JPanel player;
    private final JPanel bank;
    private boolean finished;
    private final JComboBox playerFood;
    private final JComboBox playerFavor;
    private final JComboBox playerWood;
    private final JComboBox playerGold;
    private final JComboBox bankFood;
    private final JComboBox bankFavor;
    private final JComboBox bankWood;
    private final JComboBox bankGold;
    private final JButton trade;
    
    public TradeSelector(){
        player = new JPanel(new FlowLayout());
        bank = new JPanel(new FlowLayout());
        finished = false;
        playerFood = new JComboBox();
        playerFood.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= PlayerHolder.getCurrentPlayer().getResource().getNumOfFood(); i++){
            playerFood.addItem(i);
            playerFood.setSelectedIndex(0);
        }
        playerFavor = new JComboBox();
        playerFavor.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= PlayerHolder.getCurrentPlayer().getResource().getNumOfFavor(); i++){
            playerFavor.addItem(i);
            playerFavor.setSelectedIndex(0);
        }
        playerWood = new JComboBox();
        playerWood.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= PlayerHolder.getCurrentPlayer().getResource().getNumOfWood(); i++){
            playerWood.addItem(i);
            playerWood.setSelectedIndex(0);
        }
        playerGold = new JComboBox();
        playerGold.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= PlayerHolder.getCurrentPlayer().getResource().getNumOfGold(); i++){
            playerGold.addItem(i);
            playerGold.setSelectedIndex(0);
        }
        bankFood = new JComboBox();
        bankFood.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= Bank.getInstance().getResource().getNumOfFood(); i++){
            bankFood.addItem(i);
            bankFood.setSelectedIndex(0);
        }
        bankFavor = new JComboBox();
        bankFavor.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= Bank.getInstance().getResource().getNumOfFavor(); i++){
            bankFavor.addItem(i);
            bankFavor.setSelectedIndex(0);
        }
        bankWood = new JComboBox();
        bankWood.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= Bank.getInstance().getResource().getNumOfWood(); i++){
            bankWood.addItem(i);
            bankWood.setSelectedIndex(0);
        }
        bankGold = new JComboBox();
        bankGold.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= Bank.getInstance().getResource().getNumOfGold(); i++){
            bankGold.addItem(i);
            bankGold.setSelectedIndex(0);
        }
        JLabel pFood = new JLabel("Player Food: ");
        player.add(pFood);
        player.add(playerFood);
        JLabel pFavor = new JLabel("Player Favor: ");
        player.add(pFavor);
        player.add(playerFavor);
        JLabel pWood = new JLabel("Player Wood: ");
        player.add(pWood);
        player.add(playerWood);
        JLabel pGold = new JLabel("Player Gold: ");
        player.add(pGold);
        player.add(playerGold);
        JLabel bFood = new JLabel("Bank Food: ");
        bank.add(bFood);
        bank.add(bankFood);
        JLabel bFavor = new JLabel("Bank Favor: ");
        bank.add(bFavor);
        bank.add(bankFavor);
        JLabel bWood = new JLabel("Bank Wood: ");
        bank.add(bWood);
        bank.add(bankWood);
        JLabel bGold = new JLabel("Bank Gold: ");
        bank.add(bGold);
        bank.add(bankGold);
        trade = new JButton("Trade");
        trade.addActionListener(this);
        bank.add(trade);
        add(player);
        add(bank);
        setSize(750, 100);
        setLayout(new GridLayout(2, 1));
        setTitle("Trade");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int pResources = playerFood.getSelectedIndex()+
                playerFavor.getSelectedIndex()+
                playerWood.getSelectedIndex()+
                playerGold.getSelectedIndex();
        int bResources = bankFood.getSelectedIndex()+
                bankFavor.getSelectedIndex()+
                bankWood.getSelectedIndex()+
                bankGold.getSelectedIndex();
        if(pResources==bResources){
            finished = true;
            setVisible(false);
        }
    }
    
    public boolean isFinished(){
        return finished;
    }
    
    public ArrayList<Integer> getResults(){
        ArrayList<Integer> results = new ArrayList();
        results.add(playerFood.getSelectedIndex());
        results.add(playerFavor.getSelectedIndex());
        results.add(playerWood.getSelectedIndex());
        results.add(playerGold.getSelectedIndex());
        results.add(bankFood.getSelectedIndex());
        results.add(bankFavor.getSelectedIndex());
        results.add(bankWood.getSelectedIndex());
        results.add(bankGold.getSelectedIndex());
        return results;
    }
}
