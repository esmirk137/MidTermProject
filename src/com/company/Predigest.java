package com.company;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class represent my project with graphic.
 * Name of my app is "Predigest".
 * @author Sayed Mohammad Ali Mirkazemi
 * @version 1.0.0
 * @since 6/10/2020
 */
public class Predigest {
    private JFrame frame;
    private JCheckBox followRedirect, exitType, darkTheme;
    private Color themeColor;

    private boolean stateOfFirstPanel;
    private JPanel firstPanel;
    private JButton plusButtonOfFirstPanel;
    private DefaultListModel demoList;
    private JList jListOfRequest;
    private ArrayList<Request> arrayListOfRequestObjects;
    private int selectedRequestIndex;
    private int pastSelectedRequestIndex;
    private boolean firstt;

    private JPanel secondPanel;
    private JComboBox requestMethodComboBox;
    private PTextField URLTextFiled;
    private JPanel bodyPanel;
    private JPanel headerPanel;
    private JPanel queryPanel;
    private ArrayList<JPanel> panelsOfDataFormBody;
    private ArrayList<JButton> trashOfDataFormBody;
    private ArrayList<JPanel> panelsOfHeaderPanel;
    private ArrayList<JButton> trashOfHeader;

    private JPanel thirdPanel;
    private JPanel raw;
    private JLabel detailLabelOfThirdPanel;
    private ArrayList<JLabel> labelsOfHeaderInThirdPanel;

    private ArrayList<JComponent> someUnknownComponent;

    private Logic logic;

    /**
     * This is constructor of this class and allocate fields and make main frame with all detail.
     */
    public Predigest(){
        //initialization[
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch (ClassNotFoundException e){
            System.out.println("Exception: "+e.getException());
        }catch (UnsupportedLookAndFeelException e){
            System.out.println("This system does not support this look and feel.");
        }catch (InstantiationException e){
            System.out.println("Instantiation exception.");
        }catch (IllegalAccessException e){
            System.out.println("IllegalAccess Exception");
        }

        frame = new JFrame("Predigest Designer");
        followRedirect=new JCheckBox("Follow redirect");
        darkTheme=new JCheckBox("Dark theme");
        exitType=new JCheckBox("Hide on system tray");
        themeColor=new Color(255,255,255);
        stateOfFirstPanel=true;
        firstPanel = new JPanel();
        plusButtonOfFirstPanel = new JButton("+");
        demoList=new DefaultListModel();
        jListOfRequest = new JList(demoList);
        arrayListOfRequestObjects=new ArrayList<>();
        selectedRequestIndex=0;

        secondPanel = new JPanel();
        requestMethodComboBox=new JComboBox(new String[]{"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"});
        URLTextFiled=new PTextField("https://...");
        bodyPanel=new JPanel();
        headerPanel=new JPanel();
        queryPanel=new JPanel();
        panelsOfDataFormBody=new ArrayList<>();
        trashOfDataFormBody=new ArrayList<>();
        panelsOfHeaderPanel=new ArrayList<>();
        trashOfHeader=new ArrayList<>();
        firstt=true;
        //queriesOfHeaderPanel=new ArrayList<>();

        thirdPanel = new JPanel();
        raw=new JPanel();
        detailLabelOfThirdPanel=new JLabel();
        labelsOfHeaderInThirdPanel=new ArrayList<>();

        someUnknownComponent=new ArrayList<>();

        logic=new Logic();
        //initialization]

        //frame[
        frame.setLocationRelativeTo(null);
        frame.setSize(1200, 700);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int closeConfirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit the program?", "Exit",JOptionPane.YES_NO_OPTION);
                if (closeConfirmed == JOptionPane.YES_OPTION) {
                    int saveConfirmed = JOptionPane.showConfirmDialog(null,
                            "Are you want to save the changes?", "Save", JOptionPane.YES_NO_OPTION);
                    if(saveConfirmed == JOptionPane.YES_OPTION) {
                        save();
                    }
                    frame.dispose();
                }
            }
        });
        TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage("icons\\logo.PNG"));
        trayIcon.setToolTip("Running...");
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.setVisible(true);
            }
        });
        try {
            SystemTray.getSystemTray().add(trayIcon);
        }catch (Exception e){
            e.printStackTrace();
        }
        //frame]

        //up side panel(logo+name+...)[
        JPanel upPanel=new JPanel();
        upPanel.setBackground(Color.white);
        upPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelLogoName=new JLabel("Predigest");
        labelLogoName.setFont(new Font("Serif", Font.BOLD, 30));
        labelLogoName.setForeground(Color.RED);
        JLabel labelExplain=new JLabel("API Design Platform and REST Client");
        labelExplain.setFont(new Font("Serif", Font.BOLD, 21));
        labelExplain.setForeground(new Color(255,100,100));
        ImageIcon logo=new ImageIcon("icons\\logo.PNG");
        JLabel logoLabel=new JLabel(logo);
        upPanel.add(logoLabel);
        upPanel.add(labelLogoName);
        upPanel.add(labelExplain);
        frame.add(upPanel, BorderLayout.BEFORE_FIRST_LINE);
        //up side panel(logo+name+...)]

        //firstPanel[
        createFirstPanel();
        //firstPanel]

        //secondPanel[
        createSecondPanel();
        //secondPanel]

        //thirdPanel[
        createThirdPanel();
        //thirdPanel]
        frame.setVisible(true);

        // load[

        // load]


        // frame menu bar[
        JMenuBar menuBar = new JMenuBar();
        JMenu application, edit, view, help, option;
        JMenuItem exit, screen, slider, about, helpItem;
        application = new JMenu("Application");
        application.setMnemonic('p');
        option=new JMenu("Option");
        option.setMnemonic('O');
        followRedirect.setMnemonic('F');
        followRedirect.addActionListener(followRedirectE->{
            // no implement yet
        });
        exitType.setMnemonic('s');
        exitType.addActionListener(exitTypeE->{
            if(exitType.isSelected())
                frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            else
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
        darkTheme.setMnemonic('D');
        darkTheme.addActionListener(darkThemeE->{
            if (darkTheme.isSelected()) {
                themeColor=new Color(50,50,50);
                for(int i=0; i<arrayListOfRequestObjects.size(); i++) {
                    for(JPanel panel: panelsOfHeaderPanel)
                        panel.setBackground(themeColor);
                }
                for (JComponent component : someUnknownComponent) component.setBackground(themeColor);
            } else {
                themeColor=new Color(255,255,255);
                for(int i=0; i<arrayListOfRequestObjects.size(); i++) {
                    for(JPanel panel: panelsOfHeaderPanel)
                        panel.setBackground(themeColor);
                }
                for (JComponent component : someUnknownComponent) component.setBackground(themeColor);
            }
            firstPanel.setBackground(themeColor);
            secondPanel.setBackground(themeColor);
            thirdPanel.setBackground(themeColor);
            upPanel.setBackground(themeColor);
            jListOfRequest.setBackground(themeColor);
            frame.setVisible(true);
        });
        option.add(followRedirect);
        option.add(exitType);
        option.add(darkTheme);
        exit=new JMenuItem("Exit",'x');
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK));
        exit.addActionListener(e ->{
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        });
        application.add(option);
        application.add(exit);
        edit = new JMenu("Edit");
        edit.setMnemonic('d');
        view =new JMenu("View");
        view.setMnemonic('i');
        screen=new JMenuItem("Toggle Full Screen",'F');
        screen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        screen.addActionListener(screenE->{
            if(frame.getExtendedState()==JFrame.MAXIMIZED_BOTH)
                frame.setSize(1200, 700);
            else
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
        slider=new JMenuItem("Toggle Slider",'S');
        slider.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        slider.addActionListener(sliderE->{
            if(stateOfFirstPanel) {
                frame.remove(firstPanel);
                stateOfFirstPanel=false;
            }
            else {
                frame.add(firstPanel, BorderLayout.WEST);
                stateOfFirstPanel=true;
            }
            frame.setVisible(true);
        });
        view.add(screen);
        view.add(slider);
        help = new JMenu("Help");
        help.setMnemonic('e');
        about=new JMenuItem("About",'A');
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        about.addActionListener(aboutE->{
            JFrame jFrame=new JFrame("Welcome to me...!");
            jFrame.setLocationRelativeTo(null);
            jFrame.setSize(300, 120);
            jFrame.setLayout(new BorderLayout());
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JPanel jPanel=new JPanel();
            jPanel.setBackground(themeColor);
            JLabel jLabel1=new JLabel("Name: Sayed Mohammad Ali Mirkazemi");
            JLabel jLabel2=new JLabel("ID: 9831064");
            JLabel jLabel3=new JLabel("g-mail: esmirk317@gmail.com");
            jLabel1.setFont(new Font("Arial", Font.BOLD, 15));
            jLabel2.setFont(new Font("Arial", Font.BOLD, 15));
            jLabel3.setFont(new Font("Arial", Font.BOLD, 15));
            jLabel1.setForeground(new Color(10,90,90));
            jLabel2.setForeground(new Color(10,90,90));
            jLabel3.setForeground(new Color(10,90,90));
            jPanel.add(jLabel1);
            jPanel.add(jLabel2);
            jPanel.add(jLabel3);
            jFrame.add(jPanel);
            jFrame.setResizable(false);
            jFrame.setVisible(true);
        });
        helpItem=new JMenuItem("Help",'H');
        helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
        help.add(about);
        help.add(helpItem);
        menuBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        menuBar.add(application);
        menuBar.add(edit);
        menuBar.add(view);
        menuBar.add(help);
        frame.setJMenuBar(menuBar);
        // frame menu bar]
    }

    /**
     * This method create first panel of frame (the panel that is in the left side).
     */
    private void createFirstPanel(){
        // first panel[
        firstPanel.setLayout(new BorderLayout());
        firstPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        Dimension firstPanelDimension = firstPanel.getPreferredSize();
        firstPanelDimension.width = 200;
        firstPanel.setPreferredSize(firstPanelDimension);
        // first panel -> north[
        JPanel northPanelOfFirstPanel = new JPanel();
        someUnknownComponent.add(northPanelOfFirstPanel);
        northPanelOfFirstPanel.setBackground(Color.white);
        northPanelOfFirstPanel.setLayout(new BorderLayout());
        JPanel searchRequestPanel = new JPanel();
        someUnknownComponent.add(searchRequestPanel);
        searchRequestPanel.setBackground(Color.white);
        PTextField searchTextField = new PTextField("Filter");
        Dimension searchTextFieldDimension = searchTextField.getPreferredSize();
        searchTextFieldDimension.width = 150;
        searchTextField.setPreferredSize(searchTextFieldDimension);
        searchRequestPanel.add(searchTextField);
        plusButtonOfFirstPanel.setFont(new Font("Arial", Font.BOLD, 15));
        Dimension plusButtonOfFirstPanelDimension = plusButtonOfFirstPanel.getPreferredSize();
        plusButtonOfFirstPanelDimension.height = 37;
        plusButtonOfFirstPanelDimension.width = 37;
        plusButtonOfFirstPanel.setPreferredSize(plusButtonOfFirstPanelDimension);
        plusButtonOfFirstPanel.addActionListener(new buttonActionListener());
        JLabel requestListLabel = new JLabel("Request List:");
        requestListLabel.setFont(new Font("Arial", Font.BOLD, 15));
        requestListLabel.setForeground(new Color(0, 100, 0));
        northPanelOfFirstPanel.add(searchRequestPanel, BorderLayout.CENTER);
        northPanelOfFirstPanel.add(plusButtonOfFirstPanel, BorderLayout.EAST);
        northPanelOfFirstPanel.add(requestListLabel, BorderLayout.SOUTH);
        firstPanel.add(northPanelOfFirstPanel, BorderLayout.NORTH);
        // first panel -> north]
        // first panel -> center[
        JPanel centerPanelOfFirstPanel = new JPanel();
        someUnknownComponent.add(centerPanelOfFirstPanel);
        centerPanelOfFirstPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        jListOfRequest.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                if(arrayListOfRequestObjects.size()>1){
                    //past request
                    //save url
                    arrayListOfRequestObjects.get(pastSelectedRequestIndex).setURL(URLTextFiled.getText());
                    //save input headers
                    HashMap<String,String> hashMapH=new HashMap<>();
                    for(JPanel panel:panelsOfHeaderPanel){
                        if(((JTextField)panel.getComponent(0)).getText().equals("")) continue;
                        System.out.println(((JTextField)panel.getComponent(0)).getText());
                        hashMapH.put(((JTextField)panel.getComponent(0)).getText(),((JTextField)panel.getComponent(1)).getText());
                    }
                    arrayListOfRequestObjects.get(pastSelectedRequestIndex).setHeaders(hashMapH);
                    panelsOfHeaderPanel.clear();
                    headerPanel.removeAll();
                    trashOfHeader.clear();
                    //save data form body
                    HashMap<String,String> hashMapB=new HashMap<>();
                    for(JPanel panel:panelsOfDataFormBody){
                        hashMapB.put(((JTextField)panel.getComponent(0)).getText(),((JTextField)panel.getComponent(1)).getText());
                    }
                    arrayListOfRequestObjects.get(pastSelectedRequestIndex).setMassageBodyFormData(hashMapB);
                    panelsOfDataFormBody.clear();
                    bodyPanel.removeAll();
                    trashOfDataFormBody.clear();
                }
                selectedRequestIndex = jListOfRequest.getSelectedIndex();
                Request request = arrayListOfRequestObjects.get(selectedRequestIndex);

                frame.setVisible(true);
                //set request method to combo box
                requestMethodComboBox.setSelectedItem(arrayListOfRequestObjects.get(selectedRequestIndex).getRequestMethod());
                //set url on textfield
                URLTextFiled.setText(request.getURL());
                //set headers
                if (request.getHeaders().size() != 0) {
                    for (String key : request.getHeaders().keySet()) {
                        createHeaderComponentWithoutAction(key, request.getHeaders().get(key));
                    }
                    createHeaderComponentWithAction();
                }
                else if(headerPanel.getComponentCount()==0){
                    createHeaderComponent();
                }
                //set massage body data form
                if (request.getMassageBodyFormData().size() != 0) {
                    for (String key : request.getMassageBodyFormData().keySet()) {
                        createHeaderComponentWithoutAction(key, request.getMassageBodyFormData().get(key));
                    }

                }
                else if(headerPanel.getComponentCount()==0){
                    createHeaderComponent();
                }
                //set response header: in his panel with action we do that
                //set Massage Body response: : in his panel with action we do that

                //set detail
                detailLabelOfThirdPanel.setText(request.getResponseCode() + " " + request.getResponseMassage() + "  " + request.getTime() + " ms   " + request.getLength() + " byte");
                secondPanel.setVisible(false);
                secondPanel.setVisible(true);
                thirdPanel.setVisible(false);
                thirdPanel.setVisible(true);
                frame.setVisible(true);
                pastSelectedRequestIndex=jListOfRequest.getSelectedIndex();
            }
        });
        firstPanel.add(jListOfRequest, BorderLayout.CENTER);
        // first panel -> center]
        frame.add(firstPanel, BorderLayout.WEST);
        // first panel]
    }
    /**
     * This method is action process of plus button for create new folder or new request.
     */
    private void plusButtonAction(){
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem newRequest = new JMenuItem("New Request");
        JMenuItem newFolder = new JMenuItem("New Folder");
        popupMenu.add(newRequest); popupMenu.add(newFolder);
        popupMenu.show(frame , plusButtonOfFirstPanel.getX(), plusButtonOfFirstPanel.getY()+100);
        // new request action[
        newRequest.addActionListener(e -> {
            JFrame requestFrame = new JFrame("New Request");
            requestFrame.setLocationRelativeTo(null);
            requestFrame.setSize(650, 150);
            requestFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
            requestFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JLabel label=new JLabel("Name");
            JPanel panel=new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            PTextField requestTextField=new PTextField("My Request");
            requestTextField.setFont(new Font("Arial", Font.PLAIN, 13));
            Dimension textFieldDimension=requestTextField.getPreferredSize();
            textFieldDimension.width=530;
            textFieldDimension.height=35;
            requestTextField.setPreferredSize(textFieldDimension);
            JComboBox comboBox=new JComboBox(new String[]{"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"});
            panel.add(requestTextField);
            panel.add(comboBox);
            JButton create=new JButton("create");
            requestFrame.add(label);
            requestFrame.add(panel);
            requestFrame.add(create);
            requestFrame.setVisible(true);
            create.addActionListener(event-> {
                Request request=new Request(requestTextField.getText(), comboBox.getSelectedItem().toString());
                arrayListOfRequestObjects.add(request);
                demoList.addElement(request.getRequestMethod()+" "+requestTextField.getText());
                requestFrame.dispatchEvent(new WindowEvent(requestFrame, WindowEvent.WINDOW_CLOSING));
                frame.setVisible(true);
            });
            // new request action]
        });
    }

    /**
     * This method create a panel include two text field and one check box and one trash button with focuse
     * @param keyString is key of header
     * @param valueString is value of header
     */
    private void createHeaderComponentWithoutAction(String keyString, String valueString){
        JPanel panel=new JPanel();
        panel.setBackground(themeColor);
        headerPanel.add(panel);
        panelsOfHeaderPanel.add(panel);
        JTextField textFieldKey=new JTextField(keyString);
        Dimension dimension1=textFieldKey.getPreferredSize();
        dimension1.width=190;
        textFieldKey.setPreferredSize(dimension1);
        JTextField textFieldValue=new JTextField(valueString);
        Dimension dimension2=textFieldValue.getPreferredSize();
        dimension2.width=190;
        textFieldValue.setPreferredSize(dimension2);
        JCheckBox checkBox=new JCheckBox();
        JButton button=new JButton(new ImageIcon("icons\\trash.PNG"));
        trashOfHeader.add(button);
        panel.add(textFieldKey);
        panel.add(textFieldValue);
        panel.add(checkBox);
        panel.add(button);
        frame.setVisible(true);
    }

    /**
     * This method create a panel include two text field with focusListener and one check box and one trash button.
     */
    private void createHeaderComponentWithAction(){
        JPanel panel=new JPanel();
        panel.setBackground(themeColor);
        headerPanel.add(panel);
        panelsOfHeaderPanel.add(panel);
        //FocusListener
        FocusListener focusListener=new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
            }
            @Override
            public void focusGained(FocusEvent e) {
                createHeaderComponent( );
            }
        };
        JTextField textFieldKey=new JTextField();
        Dimension dimension1=textFieldKey.getPreferredSize();
        dimension1.width=190;
        textFieldKey.setPreferredSize(dimension1);
        textFieldKey.addFocusListener(focusListener);
        JTextField textFieldValue=new JTextField();
        Dimension dimension2=textFieldValue.getPreferredSize();
        dimension2.width=190;
        textFieldValue.setPreferredSize(dimension2);
        textFieldValue.addFocusListener(focusListener);
        JCheckBox checkBox=new JCheckBox();
        JButton button=new JButton(new ImageIcon("icons\\trash.PNG"));
        trashOfHeader.add(button);
        panel.add(textFieldKey);
        panel.add(textFieldValue);
        panel.add(checkBox);
        panel.add(button);
        frame.setVisible(true);
    }

    /**
     * This method create header component with two upper method.
     */
    private void createHeaderComponent(){
        JPanel panel=new JPanel();
        panel.setBackground(themeColor);
        headerPanel.add(panel);
        panelsOfHeaderPanel.add(panel);
        //FocusListener
        FocusListener focusListener=new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
            }
            @Override
            public void focusGained(FocusEvent e) {
                createHeaderComponent();
            }
        };
        JTextField textFieldKey=new JTextField();
        Dimension dimension1=textFieldKey.getPreferredSize();
        dimension1.width=190;
        textFieldKey.setPreferredSize(dimension1);
        textFieldKey.addFocusListener(focusListener);
        JTextField textFieldValue=new JTextField();
        Dimension dimension2=textFieldValue.getPreferredSize();
        dimension2.width=190;
        textFieldValue.setPreferredSize(dimension2);
        textFieldValue.addFocusListener(focusListener);
        JCheckBox checkBox=new JCheckBox();
        JButton button=new JButton(new ImageIcon("icons\\trash.PNG"));
        trashOfHeader.add(button);
        panel.add(textFieldKey);
        panel.add(textFieldValue);
        panel.add(checkBox);
        panel.add(button);
        frame.setVisible(true);
        if(panelsOfHeaderPanel.size()>1){
            System.out.println("new................................................");
            panelsOfHeaderPanel.get(panelsOfHeaderPanel.size()-2).remove(0);
            panelsOfHeaderPanel.get(panelsOfHeaderPanel.size()-2).remove(0);
            JTextField aTextFieldKey=new JTextField();
            Dimension aDimension1=aTextFieldKey.getPreferredSize();
            aDimension1.width=190;
            aTextFieldKey.setPreferredSize(aDimension1);
            JTextField aTextFieldValue=new JTextField();
            Dimension aDimension2=aTextFieldValue.getPreferredSize();
            aDimension2.width=190;
            aTextFieldValue.setPreferredSize(aDimension2);
            panelsOfHeaderPanel.get(panelsOfHeaderPanel.size()-2).add(aTextFieldValue,0);
            panelsOfHeaderPanel.get(panelsOfHeaderPanel.size()-2).add(aTextFieldKey,0);
            secondPanel.setVisible(false); //test
            secondPanel.setVisible(true);
            frame.setVisible(true);
        }
        for(int i=0; i<trashOfHeader.size(); i++){
            int finalI = i;
            trashOfHeader.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    //System.out.println("DELETE");
                    //System.out.println("final i-1 is: "+(finalI)+".............................");
                    headerPanel.remove(finalI);
                    panelsOfHeaderPanel.remove(finalI);
                    trashOfHeader.remove(finalI);
                    HashMap<String,String> hashMapH=new HashMap<>();
                    for(JPanel jPanel:panelsOfHeaderPanel){
                        if(((JTextField)jPanel.getComponent(0)).getText().equals("") && ((JTextField)jPanel.getComponent(1)).getText().equals("")) continue;
                        //System.out.println("key: "+((JTextField)jPanel.getComponent(0)).getText()+".........................");
                        //System.out.println("value: "+((JTextField)jPanel.getComponent(1)).getText()+".........................");
                        hashMapH.put(((JTextField)jPanel.getComponent(0)).getText(),((JTextField)jPanel.getComponent(1)).getText());
                    }
                    arrayListOfRequestObjects.get(selectedRequestIndex).setHeaders(hashMapH);
                    headerPanel.removeAll();
                    panelsOfHeaderPanel.clear();
                    trashOfHeader.clear();
                    //System.out.println("create headers");
                    for (String key : arrayListOfRequestObjects.get(selectedRequestIndex).getHeaders().keySet()) {
                        //System.out.println("key: "+key+".........................");
                        //System.out.println("value: "+arrayListOfRequestObjects.get(selectedRequestIndex).getHeaders().get(key)+".........................");
                        createHeaderComponentWithoutAction(key,arrayListOfRequestObjects.get(selectedRequestIndex).getHeaders().get(key));
                    }
                    createHeaderComponentWithAction();
                    secondPanel.setVisible(false);
                    secondPanel.setVisible(true);
                    frame.setVisible(true);
                }
            });
        }
    }

    /**
     * This method create second panel of frame (panel that is in the center).
     */
    private void createSecondPanel(){
        //second panel[
        secondPanel.setBackground(Color.white);
        secondPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        secondPanel.setLayout(new CardLayout());
        frame.add(secondPanel,BorderLayout.CENTER);
        secondPanel.setLayout(new BorderLayout());
        secondPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        // second panel -> north[
        JPanel northPanelOfSecondPanel=new JPanel();
        northPanelOfSecondPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        northPanelOfSecondPanel.setBackground(themeColor);
        requestMethodComboBox.addActionListener(typeComboBoxEvent->{
            arrayListOfRequestObjects.get(selectedRequestIndex).setRequestMethod(requestMethodComboBox.getSelectedItem().toString());
            demoList.setElementAt(requestMethodComboBox.getSelectedItem().toString()+" "+arrayListOfRequestObjects.get(selectedRequestIndex).getName(),selectedRequestIndex);
            frame.setVisible(true);
        });
        URLTextFiled.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                //System.out.println("text is: "+URLTextFiled.getText()); //test.......................................
                arrayListOfRequestObjects.get(selectedRequestIndex).setURL(URLTextFiled.getText());
            }
        });
        Dimension textFieldSecondPanelDimension=URLTextFiled.getPreferredSize();
        textFieldSecondPanelDimension.width=320;
        URLTextFiled.setPreferredSize(textFieldSecondPanelDimension);
        JButton sendButton=new JButton("Send");
        northPanelOfSecondPanel.add(requestMethodComboBox);
        northPanelOfSecondPanel.add(URLTextFiled);
        northPanelOfSecondPanel.add(sendButton);
        someUnknownComponent.add(northPanelOfSecondPanel);
        secondPanel.add(northPanelOfSecondPanel,BorderLayout.NORTH);
        // second panel -> north]
        // second panel -> center[
        JPanel centerPanelOfSecondPanel=new JPanel();
        someUnknownComponent.add(centerPanelOfSecondPanel);
        centerPanelOfSecondPanel.setLayout(new BorderLayout());
        centerPanelOfSecondPanel.setBackground(themeColor);
        JTabbedPane tabbedPaneOfSecondPanel=new JTabbedPane();
        bodyPanel.setBackground(themeColor);
        //createHeaderComponent(bodyPanel, themeColor,"New Key", "New Value",true);
        someUnknownComponent.add(bodyPanel);
        JPanel bodyItem=new JPanel();
        JPanel auth=new JPanel();
        someUnknownComponent.add(auth);
        auth.setBackground(themeColor);
        JPanel authItem=new JPanel();
        someUnknownComponent.add(queryPanel);
        queryPanel.setBackground(themeColor);
        //createHeaderComponent(queryPanel, themeColor,"New Name", "New Value");
        headerPanel=new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(themeColor);
        createHeaderComponent();
        JPanel docs=new JPanel();
        someUnknownComponent.add(docs); // add 5
        docs.setBackground(themeColor);
        tabbedPaneOfSecondPanel.add("Body",bodyPanel);
        tabbedPaneOfSecondPanel.add("v",bodyItem);
        tabbedPaneOfSecondPanel.add("Auth",auth);
        tabbedPaneOfSecondPanel.add("v",authItem);
        tabbedPaneOfSecondPanel.add("query",queryPanel);
        tabbedPaneOfSecondPanel.add("header",headerPanel);
        tabbedPaneOfSecondPanel.add("docs",docs);
        centerPanelOfSecondPanel.add(tabbedPaneOfSecondPanel, BorderLayout.NORTH);
        secondPanel.add(centerPanelOfSecondPanel,BorderLayout.CENTER);
        tabbedPaneOfSecondPanel.addChangeListener(changeEvent -> {
            if(tabbedPaneOfSecondPanel.getSelectedIndex()==1){
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem MultipartForm = new JMenuItem("Multipart Form");
                MultipartForm.addActionListener(e->{
                    arrayListOfRequestObjects.get(selectedRequestIndex).setMassageBodyType("multipart form");
                });
                JMenuItem FormURLEncoded = new JMenuItem("Form URL Encoded");
                MultipartForm.addActionListener(e->{
                    arrayListOfRequestObjects.get(selectedRequestIndex).setMassageBodyType("urlencoded");
                });
                JMenuItem GraphQLQuery = new JMenuItem("GraphQL Query");
                JMenuItem JSON = new JMenuItem("JSON");
                JMenuItem XML = new JMenuItem("XML");
                JMenuItem YAML = new JMenuItem("YAML");
                JMenuItem EDN = new JMenuItem("EDN");
                JMenuItem BinaryFile = new JMenuItem("Binary File");
                JMenuItem noBody = new JMenuItem("No Body");
                popupMenu.add("# STRUCTURED ---------"); popupMenu.add(MultipartForm); popupMenu.add(FormURLEncoded); popupMenu.add(GraphQLQuery);
                popupMenu.add("# TEXT ----------------------"); popupMenu.add(JSON); popupMenu.add(XML); popupMenu.add(YAML); popupMenu.add(EDN);
                popupMenu.add("# OTHER -------------------"); popupMenu.add(BinaryFile); popupMenu.add(noBody);
                popupMenu.show(frame , 220, 180);
                tabbedPaneOfSecondPanel.setSelectedIndex(0);
            }
            else if(tabbedPaneOfSecondPanel.getSelectedIndex()==3){
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem basic = new JMenuItem("Basic Auth");
                JMenuItem digest = new JMenuItem("Digest Auth");
                JMenuItem OAuth1 = new JMenuItem("OAuth 1.0");
                JMenuItem OAuth2 = new JMenuItem("OAuth 2.0");
                JMenuItem NTLM = new JMenuItem("Microsoft NTLM");
                JMenuItem AWS = new JMenuItem("AWS IAM v4");
                JMenuItem bearer = new JMenuItem("Bearer Token");
                JMenuItem hawk = new JMenuItem("Hawk");
                JMenuItem ASAP = new JMenuItem("Atlassian ASAP");
                JMenuItem netrc = new JMenuItem("Netrc File");
                popupMenu.add(basic); popupMenu.add(digest); popupMenu.add(OAuth1); popupMenu.add(OAuth2); popupMenu.add(NTLM);
                popupMenu.add(AWS); popupMenu.add(bearer); popupMenu.add(hawk); popupMenu.add(ASAP);
                popupMenu.add(netrc);
                popupMenu.show(frame , 280, 180);
                tabbedPaneOfSecondPanel.setSelectedIndex(2);
            }
        });
        //second panel]
    }

    /**
     * This method create third panel of the frame (the panel that is in the right side).
     */
    private void createThirdPanel(){
        //third panel[
        thirdPanel.setBackground(Color.white);
        thirdPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        thirdPanel.setLayout(new CardLayout());
        frame.add(thirdPanel,BorderLayout.EAST);
        thirdPanel.setLayout(new BorderLayout());
        thirdPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        Dimension thirdPanelDimension=thirdPanel.getPreferredSize();
        thirdPanelDimension.width=500;
        thirdPanel.setPreferredSize(thirdPanelDimension);
        // third panel -> north[
        JPanel northPanelOfThirdPanel=new JPanel();
        someUnknownComponent.add(northPanelOfThirdPanel);
        northPanelOfThirdPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        northPanelOfThirdPanel.setBackground(themeColor);
        detailLabelOfThirdPanel=new JLabel("0  0 ms 0 Byte");
        detailLabelOfThirdPanel.setFont(new Font("Serif", Font.ITALIC, 21));
        detailLabelOfThirdPanel.setForeground(new Color(0,100,0));
        northPanelOfThirdPanel.add(detailLabelOfThirdPanel);
        thirdPanel.add(northPanelOfThirdPanel,BorderLayout.NORTH);
        // third panel -> north]
        // third panel -> center[
        JPanel centerPanelOfThirdPanel=new JPanel();
        someUnknownComponent.add(centerPanelOfThirdPanel);
        centerPanelOfThirdPanel.setLayout(new BorderLayout());
        centerPanelOfThirdPanel.setBackground(themeColor);
        JTabbedPane tabbedPaneOfThirdPanel=new JTabbedPane();
        raw=new JPanel();
        someUnknownComponent.add(raw);
        raw.setBackground(themeColor);
        JPanel rawItemPanel=new JPanel();
        rawItemPanel.setLayout(new CardLayout());
        JTextField textFieldBodeResponse=new JTextField();
        textFieldBodeResponse.setEditable(false);
        JPanel aHeader=new JPanel();
        someUnknownComponent.add(aHeader); // add 8
        aHeader.setBackground(themeColor);
        aHeader.setLayout(new BorderLayout());
        JPanel cookie=new JPanel();
        someUnknownComponent.add(cookie); // add 9
        cookie.setBackground(themeColor);
        JPanel timeLine=new JPanel();
        someUnknownComponent.add(timeLine); // add 10
        timeLine.setBackground(themeColor);
        tabbedPaneOfThirdPanel.add("Raw",raw);
        tabbedPaneOfThirdPanel.add("v",rawItemPanel);
        tabbedPaneOfThirdPanel.add("Header",aHeader);
        JPanel finalAHeader = aHeader;
        tabbedPaneOfThirdPanel.addChangeListener(event->{
            if(tabbedPaneOfThirdPanel.getSelectedIndex()==1){
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem rawItem = new JMenuItem("Raw");
                rawItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        textFieldBodeResponse.setText(arrayListOfRequestObjects.get(selectedRequestIndex).getResponseBody());
                    }
                });
                JMenuItem previewItem=new JMenuItem("Preview");
                previewItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if(arrayListOfRequestObjects.get(selectedRequestIndex).getHeaders().get("Content-Type").equals("image/png")){
                            logic.saveResponseBody(arrayListOfRequestObjects.get(selectedRequestIndex),true,true);
                        }
                        else if(arrayListOfRequestObjects.get(selectedRequestIndex).getHeaders().get("Content-Type").equals("image/jpg")){
                            logic.saveResponseBody(arrayListOfRequestObjects.get(selectedRequestIndex),true,true);
                        }
                    }
                });
                JMenuItem jsonItem = new JMenuItem("JSON");
                popupMenu.add(rawItem); popupMenu.add(previewItem); popupMenu.add(jsonItem);
                popupMenu.show(frame , 703, 180);
                tabbedPaneOfThirdPanel.setSelectedIndex(0);
            }
            if(tabbedPaneOfThirdPanel.getSelectedIndex()==2){
                JPanel centerHeader=new JPanel();
                centerHeader.setLayout(new CardLayout());
                JLabel jLabel=new JLabel();
                centerHeader.add(jLabel);
                centerHeader.setBackground(themeColor);
                someUnknownComponent.add(centerHeader);
                centerHeader.add((new JLabel(" Name")));
                centerHeader.add((new JLabel(" Value")));
                if(arrayListOfRequestObjects.get(selectedRequestIndex).getResponseHeaders().size()!=0){
                    for(String key:arrayListOfRequestObjects.get(selectedRequestIndex).getResponseHeaders().keySet()){
                        centerHeader.add(new JLabel(" "+key+": "));
                        centerHeader.add(new JLabel(arrayListOfRequestObjects.get(selectedRequestIndex).getResponseHeaders().get(key)));
                    }
                }
                JButton copyButton=new JButton("Copy to clipboard");
                copyButton.addActionListener(CBEvent->{
                    StringSelection stringSelection=new StringSelection(jLabel.getText());
                    Clipboard clipboard= Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection,null);
                });
                finalAHeader.add(copyButton, BorderLayout.SOUTH);
            }
        });
        tabbedPaneOfThirdPanel.add("Cookie",cookie);
        tabbedPaneOfThirdPanel.add("TimeLine",timeLine);
        centerPanelOfThirdPanel.add(tabbedPaneOfThirdPanel);
        thirdPanel.add(centerPanelOfThirdPanel,BorderLayout.CENTER);
        // third panel -> center]
        //third panel]
    }


    /**
     * This class implement "ActionListener" class for action of buttons.
     */
    private class buttonActionListener implements ActionListener {
        @Override
        public void actionPerformed(@NotNull ActionEvent actionEvent) {
            if(actionEvent.getSource().equals(plusButtonOfFirstPanel)){
                plusButtonAction();
            }
        }
    }

    /**
     * This method save all requests in .src files.
     * Also save all config of graphic.
     */
    private void save() {
        // config
        try(FileWriter fileWriter=new FileWriter("save\\config.src")){
            fileWriter.write(""+followRedirect.isSelected()+" "+exitType.isSelected()+" "+darkTheme.isSelected()+" "
                    +stateOfFirstPanel+" "+frame.getSize().width+" "+frame.getSize().height);
        } catch (FileNotFoundException e) {
            System.out.println("saving process failed. File not founded!"); //change to dialog massage
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("saving process failed!"); //change to dialog massage
            e.printStackTrace();
        }
        // request
        try(ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("save\\requests.src"))){
            objectOutputStream.writeObject(arrayListOfRequestObjects);
        } catch (FileNotFoundException e) {
            System.out.println("saving process failed. File not founded!"); //change to dialog massage
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("saving process failed!"); //change to dialog massage
            e.printStackTrace();
        }
    }

    /**
     * This method find .ser file in a folder.
     * @return these file as array
     */
    private File[] serFormatFileFinder(String path){
        File dir = new File(path);
        return dir.listFiles((dir1, filename) -> filename.endsWith(".src"));
    }

    /**
     * This method get string (true/false) and return it boolean form.
     * @param string is input string and just have to be "true" or "false"
     * @return a boolean as answer
     */
    private boolean stringToBoolean(String string){
        return string.equals("true");
    }

    /**
     * This method load config and requests.
     */
    private void load(){
        // config
        try(FileReader fileReader=new FileReader("save\\config.src"); Scanner scanner=new Scanner(fileReader)) {
            followRedirect.setSelected(stringToBoolean(scanner.next()));
            exitType.setSelected(stringToBoolean(scanner.next()));
            darkTheme.setSelected(stringToBoolean(scanner.next()));
            if(darkTheme.isSelected()) themeColor=new Color(50,50,50);
            stateOfFirstPanel=(stringToBoolean(scanner.next()));
            frame.setSize(scanner.nextInt(),scanner.nextInt());
        } catch (FileNotFoundException e) {
            System.out.println("loading process failed. File not founded!"); //change to dialog massage
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("loading process failed!"); //change to dialog massage
            e.printStackTrace();
        }
        // other
        try(ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("save\\requests.src"))){
            arrayListOfRequestObjects=(ArrayList<Request>) objectInputStream.readObject();
        } catch (ClassNotFoundException e){
            System.out.println("loading process failed. Class not founded!"); //change to dialog massage
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("loading process failed. File not founded!"); //change to dialog massage
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("loading process failed!"); //change to dialog massage
            e.printStackTrace();
        }
        for(Request request:arrayListOfRequestObjects){
            demoList.addElement(request.getRequestMethod()+" "+request.getName());
            System.out.println(request.getRequestMethod()+" "+request.getName()); //test.................................
        }
    }

    /**
     * This method show frame.
     */
    public void showFrame(){
        frame.setVisible(true);
    }
}

/**
 * This class extend "JTextField" class.
 * Constructor of this class get a string as prompt text.
 * @author Sayed Mohammad Ali Mirkazemi
 * @version 1.0.0
 * @since 6/10/2020
 */
class PTextField extends JTextField {
    /**
     * This is constructor of this class and get prompt text and set a focusListener to it.
     * @param promptText is a string as prompt text
     */
    public PTextField(final String promptText) {
        super(promptText);
        addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                if(getText().isEmpty()) {
                    setText(promptText);
                }
            }
            @Override
            public void focusGained(FocusEvent e) {
                if(getText().equals(promptText)) {
                    setText("");
                }
            }
        });

    }
}
