/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Eric
 */
public class StealResources extends JFrame{
    private Player p;
    private int limit;
    private int[] result;
    private JComboBox food;
    private JComboBox favor;
    private JComboBox wood;
    private JComboBox gold;
    private boolean selected;
    public StealResources(Player attacker, Player p, int limit){
        this.p = p;
        this.limit = limit;
        if(attacker.isHuman()){
            selected = false;
            setTitle("Steal any "+limit+"res");
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            setLayout(new FlowLayout());
            setSize(350, 100);
            setResizable(false);
            initComponents();
            setLocationRelativeTo(null);
            setVisible(true);
            try{
                while(!selected){Thread.sleep(10);}
            }catch(Exception ex){
                    ex.printStackTrace();
            }
        }else{
            result = new int[]{0,0,0,0};
            Random r = new Random();
            for(int i = 0; i < 5; i++){
                result[r.nextInt(4)]++;
            }
        }
    }
    
    private void initComponents(){
        JLabel foodLabel = new JLabel("Food:");
        add(foodLabel);
        food = new JComboBox();
        food.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= Math.min(p.getResource().getNumOfFood(), limit) ; i++){
            food.addItem(i);
        }
        add(food);
        JLabel favorLabel = new JLabel("Favor:");
        add(favorLabel);
        favor = new JComboBox();
        favor.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= Math.min(p.getResource().getNumOfFavor(), limit) ; i++){
            favor.addItem(i);
        }
        add(favor);
        JLabel woodLabel = new JLabel("Wood:");
        add(woodLabel);
        wood = new JComboBox();
        wood.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= Math.min(p.getResource().getNumOfWood(), limit) ; i++){
            wood.addItem(i);
        }
        add(wood);
        JLabel goldLabel = new JLabel("Gold:");
        add(goldLabel);
        gold = new JComboBox();
        gold.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= Math.min(p.getResource().getNumOfGold(), limit) ; i++){
            gold.addItem(i);
        }
        add(gold);
        JButton finish = new JButton("Finish");
        finish.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                finish();
            }
        });
        add(finish);
    }
    
    private void finish(){
        if(food.getSelectedIndex()+favor.getSelectedIndex()+wood.getSelectedIndex()+gold.getSelectedIndex()<=limit){
            result = new int[]{food.getSelectedIndex(),favor.getSelectedIndex(),wood.getSelectedIndex(),gold.getSelectedIndex()};
            selected = true;
            setVisible(false);
        }
    }
    
    public int[] getResult(){
        return result;
    }
}
