package pers.syq.todo.af.appleft;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MenuColorIcon {
    private Map<String,Color> colorNameMap;
    private Map<Color, ImageIcon> colorImgMap;

    public MenuColorIcon(){
        colorNameMap = new HashMap<>();
        colorImgMap = new HashMap<>();
        String[] colorName = {"green","zise","pink2","orange","blue","lightGreen"};
        Color[] colors = {new Color(31,31,255),new Color(143,31,255),new Color(255,31,143),
                new Color(255,143,31),new Color(31,255,143),new Color(31,255,255)};
        colorNameMap.put("green",new Color(31,31,255));
        for (int i = 0; i < colorName.length; i++) {
            colorNameMap.put(colorName[i],colors[i]);
            colorImgMap.put(colors[i],lordImgIcon("/images/" +colorName[i]+".png"));
        }
    }

    private ImageIcon lordImgIcon(String imgPath) {
        return new ImageIcon(getClass().getResource(imgPath));
    }

    public Map<Color, ImageIcon> getColorImgMap() {
        return colorImgMap;
    }
}
