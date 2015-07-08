/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Eric
 */
public class GatherSelector extends JFrame implements ActionListener{
    private int choice;     // -1 -> not selected
    JRadioButton[] buttons;
    ButtonGroup bg ;
    private JButton select;
    public GatherSelector(){
        choice = -1;
        initComponents();
        setTitle("Gather");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        try{ while(choice==-1){     Thread.sleep(1);    }   }
        catch(Exception ex){    ex.printStackTrace();   }
    }
    
    private void initComponents(){        
        JPanel panel = new JPanel();
        bg = new ButtonGroup();
        buttons = new JRadioButton[10];
        buttons[0] = new JRadioButton("Food");
        buttons[1] = new JRadioButton("Favor");
        buttons[2] = new JRadioButton("Wood");
        buttons[3] = new JRadioButton("Gold");
        buttons[4] = new JRadioButton("Fertile");
        buttons[5] = new JRadioButton("Forest");
        buttons[6] = new JRadioButton("Hills");
        buttons[7] = new JRadioButton("Mountains");
        buttons[8] = new JRadioButton("Desert");
        buttons[9] = new JRadioButton("Swamp");
        for (JRadioButton button : buttons) {
            bg.add(button);
            panel.add(button);
        }
        add(panel);
        JButton finish = new JButton("Finish");
        finish.addActionListener(this);
        add(finish);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0;i<buttons.length;i++){
            if(bg.getSelection() == buttons[i].getModel()){
                choice = i+1;
                break;
            }
        }
    }
    public int getChoice(){
        return choice;
    }
}