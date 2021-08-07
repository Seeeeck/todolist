package pers.syq.todo;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import pers.syq.todo.af.appleft.ToDoList;
import pers.syq.todo.appright.Card;

import java.awt.*;
import java.io.*;
import java.net.URL;


public class Objectios {
    private static final Objectios objectios = new Objectios();
    private Objectios(){

    }

    public static Display readToDoList() {
        URL url = objectios.getClass().getResource("/todolist.xml");
        Display display = new Display("ToDoList");
        SAXReader sr = new SAXReader();
        try{
            Document doc = sr.read(url);
            Element toDoLists = doc.getRootElement();
            for(Element list:toDoLists.elements()){
                ToDoList l1 = new ToDoList(display);
                for(Element temp1:list.elements()){
                    switch (temp1.getName()) {
                        case "listname":
                            l1.getListNameL().setText((String) temp1.getData());
                            break;
                        case "cardlist":
                            for (Element card : temp1.elements()) {
                                Card temp_card = new Card(l1);
                                for (Element temp2 : card.elements()) {
                                    if (temp2.getName().equals("title")) {
                                        temp_card.getTitleT().setText((String) temp2.getData());
                                        if(!temp2.getData().equals(temp_card.getDefaultTitle())){
                                            temp_card.getTitleT().setForeground(Color.BLACK);
                                        }
                                    } else if (temp2.getName().equals("memo")) {
                                        temp_card.getMemoT().setText((String) temp2.getData());
                                    }
                                }
                                l1.getCards().add(temp_card);
                            }
                            break;
                        case "color":
                            String[] strs = temp1.getText().split(",");
                            l1.setColor(new Color(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]), Integer.parseInt(strs[2])));
                            l1.changeIcon();
                            break;
                    }
                }
                display.getTodolist().add(l1);
            }
            display.initListAndCard();

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return display;
    }

    public static void writeToDoList(Display display)  {
        String filename = objectios.getClass().getResource("/todolist.xml").getPath();
        File file = new File(filename);
        XMLWriter xw = null;
        try{
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            OutputFormat of = new OutputFormat("\t",true,"UTF-8");
            xw = new XMLWriter(bos,of);
            Document newDoc = DocumentHelper.createDocument();
            Element toDoLists = newDoc.addElement("todolists");
            for(ToDoList t1:display.getTodolist()){
                Element list = toDoLists.addElement("list");
                list.addElement("listname").setText(t1.getListNameL().getText());
                list.addElement("color").setText(""+t1.getColor().getRed()+","+t1.getColor().getGreen()+","+t1.getColor().getBlue());
                Element cardList = list.addElement("cardlist");
                for(Card card1: t1.getCards()){
                    Element card = cardList.addElement("card");
                    card.addElement("title").setText(card1.getTitleT().getText());
                    card.addElement("memo").setText(card1.getMemoT().getText());
                }
            }
            xw.write(newDoc);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if (xw != null) {
                    xw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
