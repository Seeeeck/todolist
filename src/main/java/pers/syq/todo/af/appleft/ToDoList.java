package pers.syq.todo.af.appleft;

import pers.syq.todo.swing.AfPanel;
import pers.syq.todo.swing.layout.AfXLayout;
import pers.syq.todo.Display;
import pers.syq.todo.appright.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

public class ToDoList extends AfPanel {
    private JLabel listNameL;
    private JLabel cardNumL;
    private ArrayList<Card> Cards;
    private Display display;
    private JPopupMenu popup;
    private Color color;
    private Map<Color,ImageIcon> iconMap;
    public ToDoList(Display display) {
        this.display = display;
        listNameL = new JLabel("新規リスト");
        listNameL.setIcon(new ImageIcon(getClass().getResource("/images/orange.png")));
        setLayout(new AfXLayout());
        Cards = new ArrayList<>();
        padding(0,4,0,1);
        add(listNameL,"1w");
        cardNumL = new JLabel("");
        cardNumL.setText(String.valueOf(Cards.size()));
        add(cardNumL,"20px");
        popup = new JPopupMenu();
        JMenuItem changeName = new JMenuItem("名前を変更");
        JMenuItem delete = new JMenuItem("削除");
        changeName.setActionCommand("changeName");
        changeName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameChanged();

            }
        });
        delete.setActionCommand("delete");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ToDoList.this.setVisible(false);
                if(ToDoList.this.display.getSelectedList()==ToDoList.this){
                    for(Card card:ToDoList.this.Cards){
                        card.setVisible(false);
                    }
                    ToDoList.this.display.setSelectedList(null);
                }
                ToDoList.this.display.getTodolist().remove(ToDoList.this);
                ToDoList.this.display.search_();
            }
        });

        color = new Color(255,143,31);

        JMenu changeColor = new JMenu("色を変更");
        iconMap = display.getMenuColorIcon().getColorImgMap();
        for(Color color:iconMap.keySet()){
            JMenuItem tmpColor =  new JMenuItem();
            tmpColor.setBackground(color);
            changeColor.add(tmpColor);
            tmpColor.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(ToDoList.this.display.getSelectedList() == ToDoList.this){
                        ToDoList.this.display.getListNameL().setForeground(color);
                    }
                    listNameL.setIcon(iconMap.get(color));

                }

            });
        }

        popup.add(changeName);
        popup.add(delete);
        popup.add(changeColor);



        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3){
                    popup.show(e.getComponent(),e.getX(),e.getY());
                }
                if(e.getClickCount()==2){
                    nameChanged();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1) {
                    boolean isChanged = false;
                    ToDoList.this.display.clearShowCards();
                    if (ToDoList.this != ToDoList.this.display.getSelectedList()) {
                        isChanged = true;
                    }
                    if (isChanged) {
                        if (ToDoList.this.display.getSelectedList() != null) {
                            for (Card card : ToDoList.this.display.getSelectedList().getCards()) {
                                card.setVisible(false);
                            }
                            ToDoList.this.display.getSelectedList().setBgColor(Color.LIGHT_GRAY);
                            ToDoList.this.display.getSelectedList().getListNameL().setForeground(Color.BLACK);
                            ToDoList.this.display.getSelectedList().getCardNumL().setForeground(Color.BLACK);
                        }
                        ToDoList.this.display.setSelectedList(ToDoList.this);
                        for (Card card : Cards) {
                            card.setVisible(true);
                        }
                        ToDoList.this.display.changeShowPanel("list");
                        setBgColor(new Color(0x1296db));
                        listNameL.setForeground(Color.WHITE);
                        cardNumL.setForeground(Color.WHITE);

                        ToDoList.this.display.getListNameL().setText(ToDoList.this.listNameL.getText());
                        ToDoList.this.display.getListNameL().setForeground(color);
                    }
                }
            }
        });
    }

    public void changeIcon(){
        listNameL.setIcon(iconMap.get(color));
    }


    private void nameChanged(){
        JOptionPane a = new JOptionPane();
        String input = JOptionPane.showInputDialog(this,"リストネーム:",getListNameL().getText());
        if(input != null){
            if(!input.equals("")){
                listNameL.setText(input);
                display.getListNameL().setText(input);
            }else{
                JOptionPane.showMessageDialog(this,"リストネームを入力してください.");
            }
        }
    }



    public JLabel getListNameL() {
        return listNameL;
    }

    public JLabel getCardNumL() {
        return cardNumL;
    }

    public ArrayList<Card> getCards(){
        return Cards;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }


}
