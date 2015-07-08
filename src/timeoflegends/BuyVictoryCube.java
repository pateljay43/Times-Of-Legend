/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Eric
 */
public class BuyVictoryCube extends JFrame implements ActionListener{
    
    private boolean finish;
    private JComboBox vCubes;
    private JButton trade;
    private JLabel cost;
    
    public BuyVictoryCube(int max){
        finish = false;
        setTitle("Trade");
        setLocationRelativeTo(null);
//        setSize(200, 200);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        cost = new JLabel("0");
        vCubes = new JComboBox();
        vCubes.setModel(new DefaultComboBoxModel());
        for(int i = 0; i <= max; i++){
            vCubes.addItem(i);
            vCubes.setSelectedIndex(0);
        }
        vCubes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changedSelection();
            }
        });
        add(vCubes);
        JLabel favor = new JLabel("Favor: ");
        add(favor);
        add(cost);
        trade = new JButton("Trade");
        trade.addActionListener(this);
        add(trade);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        finish = true;
    }
    
    private void changedSelection(){
        cost.setText(Integer.toString(vCubes.getSelectedIndex()*8));
    }
    
    public boolean isFinished(){
        return finish;
    }
    
    public int getCubes(){
        return vCubes.getSelectedIndex();
    }
    
    public int getResult(){
        return vCubes.getSelectedIndex();
    }
}
