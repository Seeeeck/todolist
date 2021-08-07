package pers.syq.todo.appright;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class DelCardButton extends JButton {
    public DelCardButton(){
        URL url = getClass().getResource("/images/shanchu.png");
        Icon icon = new ImageIcon(url);
        setIcon(icon);
        setToolTipText("削除");
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(new Color(0x1296db)));
    }
}
