package pers.syq.todo.appright;

import javax.swing.*;
import java.net.URL;


public class AddCardButton extends JButton {
    public AddCardButton(){
        URL url = getClass().getResource("/images/shizijiahao2_1.png");
        Icon icon = new ImageIcon(url);
        setIcon(icon);
        setContentAreaFilled(false);
        setFocusPainted(false);
    }
}
