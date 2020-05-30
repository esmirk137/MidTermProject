package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        HashMap<Integer, ArrayList<JPanel>> panelHashMap=new HashMap<>();
        ArrayList<JPanel> arrayList=new ArrayList<>();
        JPanel panel1=new JPanel();
        panel1.setBackground(Color.blue);
        arrayList.add(panel1);
        JPanel panel2=new JPanel();
        panel2.setBackground(Color.pink);
        arrayList.add(panel2);
        panelHashMap.put(0,arrayList);
        try(ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("hashPanel.txt")); ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("hashPanel.txt"))){
            objectOutputStream.writeObject(panelHashMap);
            JFrame frame=new JFrame();
            frame.setSize(300,300);
            frame.setLayout(new CardLayout());
            HashMap<Integer, ArrayList<Panel>> hashMap= (HashMap<Integer, ArrayList<Panel>>) objectInputStream.readObject();
            ArrayList<Panel> panelArrayList=hashMap.get(0);
            frame.add(panelArrayList.get(0));
            frame.setVisible(true);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
