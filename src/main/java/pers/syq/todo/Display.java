package pers.syq.todo;

import pers.syq.todo.swing.AfPanel;
import pers.syq.todo.swing.layout.AfXLayout;
import pers.syq.todo.swing.layout.AfYLayout;
import pers.syq.todo.af.appleft.ToDoList;
import pers.syq.todo.af.appleft.AddListButton;
import pers.syq.todo.af.appleft.MenuColorIcon;
import pers.syq.todo.appright.AddCardButton;
import pers.syq.todo.appright.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Appのwindowを作成するClass
 */
public class Display extends JFrame {
    private AfPanel root;
    private AfPanel left1, right1,rightStart,rightStart2,rightEnd2,
    leftStart2,leftCenter2,leftEnd2;
    private JLabel listNameL;
    private JButton addCardB,addListB;
    private ArrayList<ToDoList> todolist;
    private ArrayList<Card> showCards;
    private ToDoList selectedList;
    private JTextField searchBox;
    private String defaultText;
    private CardLayout rightCardLayout;
    private MenuColorIcon menuColorIcon;


    public Display(String title){
        super(title);
        init();

    }

    private void init(){
        todolist = new ArrayList<>();
        selectedList = null;
        //root
        root = new AfPanel();
        setContentPane(root);
        root.setLayout(new AfXLayout(0));
        //1
        left1 = new AfPanel();
        right1 = new AfPanel();
        right1.setBgColor(Color.white);
        root.add(left1,"225px");
        root.add(right1,"1w");
        left1.setLayout(new BorderLayout());
        left1.setBgColor(Color.LIGHT_GRAY);
        right1.setLayout(new BorderLayout());
        //2_left
        leftStart2 = new AfPanel();
        leftCenter2 = new AfPanel();
        leftEnd2 = new AfPanel();
        leftStart2.padding(3,8,3,0);
        leftStart2.preferredHeight(40);
        leftCenter2.padding(2,8,0,0);
        leftEnd2.padding(4,0,2,0);
        leftEnd2.preferredHeight(40);
        leftEnd2.setBgColor(Color.LIGHT_GRAY);
        leftEnd2.setOpaque(true);
        left1.add(leftStart2,BorderLayout.PAGE_START);
        left1.add(leftCenter2,BorderLayout.CENTER);
        left1.add(leftEnd2,BorderLayout.PAGE_END);
        //2_left Button and Label
        addListB = new AddListButton();
        leftEnd2.setLayout(new AfXLayout());
        leftEnd2.add(addListB,"120px");
        addListB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addList();
            }
        });
        leftCenter2.setLayout(new AfYLayout());
        JLabel myList = new JLabel("マイリスト");
        myList.setFont(new Font("Menlo",1,14));
        leftCenter2.add(myList);

        defaultText = "検索";
        searchCard();


        //2_right
        rightStart = new AfPanel();
        rightCardLayout = new CardLayout();
        rightStart.setLayout(rightCardLayout);
        rightStart2 = new AfPanel();
        AfPanel rightStartSearch = new AfPanel();

        rightStart.add(rightStart2,"list");
        rightStart.add(rightStartSearch,"search");
        changeShowPanel("list");
        rightEnd2 = new AfPanel();
        rightStart2.preferredHeight(40);
        rightEnd2.padding(10,13,0,0);
        rightStart2.setLayout(new AfXLayout());
        rightEnd2.setLayout(new AfYLayout(10));
        rightStart2.padding(3,13,0,5);

        rightStartSearch.preferredHeight(40);
        rightStartSearch.padding(3,13,0,5);
        rightStartSearch.setLayout(new AfXLayout());
        JLabel searchNameL = new JLabel("検索結果");
        searchNameL.setFont(new Font(Font.MONOSPACED,Font.BOLD,26));
        searchNameL.setForeground(new Color(0x1296db));
        rightStartSearch.add(searchNameL,"1w");

        right1.add(rightStart,BorderLayout.PAGE_START);
        right1.add(rightEnd2,BorderLayout.CENTER);
        //2_rightStart  ListName and addCardButton
        listNameL = new JLabel("");
        listNameL.setFont(new Font(Font.MONOSPACED,Font.BOLD,26));
        rightStart2.add(listNameL,"1w");



        addCardB = new AddCardButton();
        addCardB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCard();
            }
        });
        rightStart2.add(addCardB,"50px");

        //load image
        menuColorIcon = new MenuColorIcon();

    }

    public void initListAndCard(){
        for(ToDoList temp:todolist){
            leftCenter2.add(temp,"25px");
            temp.getCardNumL().setText(String.valueOf(temp.getCards().size()));
            for (Card card:temp.getCards()){
                rightEnd2.add(card,"70px");
                card.setVisible(false);
            }
        }

    }




    private void addCard(){
        if(selectedList != null){
            Card card1 = new Card(selectedList);
            selectedList.getCards().add(card1);
            selectedList.getCardNumL().setText(String.valueOf(selectedList.getCards().size()));
            rightEnd2.add(card1,"70px");
        }

    }

    private void addList(){
        ToDoList temp1 = new ToDoList(this);
        todolist.add(temp1);
        leftCenter2.add(temp1,"25px");
        leftCenter2.updateUI();

    }

    private void searchCard(){
        searchBox = new JTextField();
        searchBox.setText(defaultText);
        searchBox.setForeground(new Color(0x505050));
        JLabel searchIcon = new JLabel();
        searchIcon.setIcon(new ImageIcon(getClass().getResource("/images/chazhao.png")));
        leftStart2.setLayout(new AfXLayout());
        leftStart2.add(searchIcon,"17px");
        leftStart2.add(searchBox,"88%");
        searchBox.setFont(new Font("Menlo", Font.PLAIN,15));
        searchBox.setBackground(new Color(0xa9a9a9));
        searchIcon.setFocusable(true);
        showCards = new ArrayList<>();
        searchBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String temp = searchBox.getText();
                if(temp.equals(defaultText)){
                    searchBox.setText("");
                    searchBox.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String temp = searchBox.getText();
                if(temp.equals("")){
                    searchBox.setText(defaultText);
                    searchBox.setForeground(new Color(0x505050));
                }
            }
        });
        searchBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER && !searchBox.getText().equals("")){
                    search_();
                }
            }
        });


    }

    public void search_(){
        String temp = searchBox.getText().trim();
        changeShowPanel("search");
        if(selectedList != null){
            for(Card card:selectedList.getCards()){
                card.setVisible(false);
            }
            selectedList.setBgColor(Color.LIGHT_GRAY);
            selectedList.getListNameL().setForeground(Color.BLACK);
            selectedList.getCardNumL().setForeground(Color.BLACK);
            selectedList = null;

        }
        clearShowCards();
        for(ToDoList todo:todolist){
            for(Card card: todo.getCards()){
                if(card.getMemoT().getText().contains(temp) || card.getTitleT().getText().contains(temp) ){
                    showCards.add(card);
                    card.setVisible(true);
                }
            }
        }
    }

    public void clearShowCards(){
        for(Iterator iter = showCards.iterator();iter.hasNext();){
            Card c = (Card)iter.next();
            c.setVisible(false);
        }
        showCards.clear();
    }

    public void changeShowPanel(String name){
        rightCardLayout.show(rightStart,name);

    }


    public void setShowCards(ArrayList<Card> showCards) {
        this.showCards = showCards;
    }

    public void setSelectedList(ToDoList selectedList) {
        this.selectedList = selectedList;
    }

    public ArrayList<Card> getShowCards() {
        return showCards;
    }

    public ToDoList getSelectedList() {
        return selectedList;
    }

    public ArrayList<ToDoList> getTodolist(){
        return todolist;
    }

    public JLabel getListNameL(){
        return listNameL;
    }

    public MenuColorIcon getMenuColorIcon() {
        return menuColorIcon;
    }
}
