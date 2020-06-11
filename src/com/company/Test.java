package com.company;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        /*
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

         */

        /*
        JFrame frame=new JFrame();
        JPanel panel=new JPanel();
        panel.setLayout(new FlowLayout());
        JButton b1=new JButton("1");
        JButton b2=new JButton("2");
        JButton b3=new JButton("3");
        JButton b4=new JButton("4");
        JButton b5=new JButton("5");

        panel.add(b1);panel.add(b2);panel.add(b3);panel.add(b4);panel.add(b5);

        panel.setLayout(new FlowLayout(FlowLayout.TRAILING));
        frame.add(panel);
        frame.setVisible(true);


         */
        System.out.println(Integer.parseInt("1"));
    }


}
