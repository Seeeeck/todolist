package pers.syq.todo.af.appleft;

import javax.swing.*;

public class AddListButton extends JButton {
    public AddListButton(){
        super("リストを追加");
        setIcon(new ImageIcon(getClass().getResource("/images/jiahao2.png")));
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(null);
        setOpaque(false);


    }
}
