package pers.syq.todo.appright;

import pers.syq.todo.swing.AfPanel;
import pers.syq.todo.swing.layout.AfXLayout;
import pers.syq.todo.af.appleft.ToDoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Card extends AfPanel {
    private JTextField titleT;
    private JTextArea memoT;
    private AfPanel start;
    private JScrollPane end;
    private DelCardButton delCardButton;
    private ToDoList toDoList;
    private String defaultMemo,defaultTitle;
    public Card(ToDoList toDoList){

        this.toDoList = toDoList;
        defaultMemo = "メモ";
        defaultTitle = "タイトル";
        titleT = new JTextField(defaultTitle);
        memoT = new JTextArea(defaultMemo);
        start = new AfPanel();
        end = new JScrollPane(memoT);
        delCardButton = new DelCardButton();
        preferredHeight(70);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.BLACK));
        padding(2,1,2,3);
        start.preferredHeight(24);
        add(start,BorderLayout.PAGE_START);
        add(end,BorderLayout.CENTER);

        //start
        start.setLayout(new AfXLayout());
        start.padding(0,0,2,0);
        titleT.setFont(new Font("Menlo", Font.PLAIN,15));
        titleT.setForeground(Color.GRAY);
        titleT.setOpaque(false);
        titleT.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(0x1296db)));

        start.add(titleT,"1w");
        start.add(delCardButton,"16px");

        //end

        //end.setLayout(new AfXLayout());
        //end.add(memoT,"96%");
        memoT.setBorder(null);
        memoT.setFont(new Font("Menlo", Font.PLAIN,12));
        memoT.setForeground(Color.GRAY);
        memoT.setLineWrap(true);


        memoT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String temp = memoT.getText();
                if(temp.equals(defaultMemo)){
                    memoT.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String temp = memoT.getText();
                if(temp.equals("")){
                    memoT.setText(defaultMemo);
                }
            }
        });
        titleT.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String temp = titleT.getText();
                if(temp.equals(defaultTitle)){
                    titleT.setText("");
                    titleT.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String temp = titleT.getText();
                if(temp.equals("")){
                    titleT.setText(defaultTitle);
                    titleT.setForeground(Color.GRAY);
                }

            }
        });

        delCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                for(Card card:Card.this.toDoList.getCards()){
                    if(Card.this.equals(card)){
                        Card.this.toDoList.getCards().remove(card);
                        break;
                    }
                }
                Card.this.toDoList.getCardNumL().setText(String.valueOf((Card.this.toDoList.getCards().size())));

            }
        });





    }

    public JTextField getTitleT() {
        return titleT;
    }

    public JTextArea getMemoT() {
        return memoT;
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }
}
