/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Eric
 */
public class SelectUnit extends JFrame{
    private final int cardsPerRow = 5;
    private ArrayList<Unit> choice;
    private ArrayList<Unit> pool;
    private final int limit;
    private int count;
    private final int poolSize;
    private boolean selected;
    private boolean finish;
    private JLayeredPane[] cardHolder;
    private JLabel[] circle;
    private boolean[] mark;
    
    public SelectUnit(ArrayList<Unit> p, int limit, boolean isHuman, boolean isChoosing){
        count = 0;
        this.limit = limit;
        pool = p;
        poolSize = pool.size();
        selected = false;
        choice = new ArrayList();
        cardHolder = new JLayeredPane[pool.size()];
        circle = new JLabel[pool.size()];
        mark = new boolean[pool.size()];
        if(isHuman){
            redraw(isChoosing);
            try{
                while(!selected){Thread.sleep(10);}
            }catch(Exception ex){
                    ex.printStackTrace();
            }
            finish = true;
        }else{
            Random r = new Random();
            for(int i = 0; i < Math.min(limit, poolSize); i++){
                choice.add(pool.remove(r.nextInt(pool.size())));
            }
            selected = true;
        }
    }
    private void redraw(boolean isChoosing){
        if(!pool.isEmpty()){
            JPanel cards = new JPanel(new GridLayout(0, cardsPerRow));
            for(int i = 0; i < pool.size(); i++){
                final int j = i;
//                if(cardHolder[i]!=null) { remove(cardHolder[i]); }
                cardHolder[i] = new JLayeredPane();
                JLabel label = new JLabel(pool.get(i).getImage());  //TODO add icon
                label.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        changeMark(j);
                    }
                    @Override public void mousePressed(MouseEvent e) {}
                    @Override public void mouseReleased(MouseEvent e) {}
                    @Override public void mouseEntered(MouseEvent e) {}
                    @Override public void mouseExited(MouseEvent e) {}
                });
                label.setBounds(0,0,150, 200);
                label.setOpaque(false);
                circle[i] = new JLabel(new ImageIcon(Constants.circleImg));
                circle[i].setBounds(0,0,150,200);
                circle[i].setVisible(mark[i]);
                circle[i].setOpaque(false);
                cardHolder[i].add(label, JLayeredPane.DEFAULT_LAYER, 0);
                cardHolder[i].add(circle[i], JLayeredPane.PALETTE_LAYER, 0);
                cardHolder[i].setBounds(i*150,0,150, 200);
                cards.add(cardHolder[i]);
            }
            JScrollPane scroll = new JScrollPane(cards);
            setLayout(new BorderLayout());
            add(scroll, BorderLayout.CENTER);
            JButton finishButton = new JButton("Finish");
            finishButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    finish();
                }
            });
            //finishButton.setBounds(pool.size()*150 + 10, 0, 80, 30);
            add(finishButton, BorderLayout.SOUTH);
            setTitle(isChoosing?"Choose Units":"Enemy Units");
            setSize(cardsPerRow*150, (((pool.size()-1)/cardsPerRow)+1)*200+50);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            setVisible(true);
            revalidate();
            repaint();
        }
    }
    
    private void finish(){
        for(int i=mark.length-1;i>=0;i--){
            if(mark[i]){
                mark[i] = false;
                choice.add(pool.remove(i));
            }
        }
        if(choice.size()<=limit){
            setVisible(false);
            selected = true;
        }else{
            for(int i = 0; i <choice.size(); i++){
                pool.add(choice.remove(i));
            }
            revalidate();
            repaint();
        }
    }
    
    public boolean isSelected(){
        return selected;
    }
    
    public ArrayList<Unit> getChoice(){
        return choice;
    }
    
    private void changeMark(int i){
        if(mark[i]){    // remove mark
            count--;
            mark[i] = !mark[i];
        }else{  //add mark
            if(count<limit){
                count++;
                mark[i] = !mark[i];
            }
        }
        circle[i].setVisible(mark[i]);
        this.revalidate();
        this.repaint();
    }
    boolean isFinish(){
        if(finish){
            finish = false;
            return true;
        }
        return false;
    }
}
