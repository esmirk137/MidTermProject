package com.company;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
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

    private JPanel secondPanel;
    private JComboBox requestMethodComboBox;
    private PTextField URLTextFiled;
    private JPanel bodyPanel;
    private JPanel headerPanel;
    private JPanel queryPanel;
    private ArrayList<JPanel> dataFormMassageBody;
    private ArrayList<JPanel> headersOfHeaderPanel;
    private ArrayList<JPanel> queriesOfHeaderPanel;

    private JPanel thirdPanel;
    private JPanel raw;
    private JLabel detailLabelOfThirdPanel;
    private ArrayList<JLabel> labelsOfHeaderInThirdPanel;

    private ArrayList<JComponent> someUnknownComponent;


    /**
     * This is constructor of this class and allocate fields and make main frame.
     * ??????????????????????//
     * ?????????????
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
        dataFormMassageBody=new ArrayList<>();
        headersOfHeaderPanel=new ArrayList<>();
        queriesOfHeaderPanel=new ArrayList<>();

        thirdPanel = new JPanel();
        raw=new JPanel();
        detailLabelOfThirdPanel=new JLabel();
        labelsOfHeaderInThirdPanel=new ArrayList<>();

        someUnknownComponent=new ArrayList<>();
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
                    for(JPanel panel: headersOfHeaderPanel)
                        panel.setBackground(themeColor);
                }
                for (JComponent component : someUnknownComponent) component.setBackground(themeColor);
            } else {
                themeColor=new Color(255,255,255);
                for(int i=0; i<arrayListOfRequestObjects.size(); i++) {
                    for(JPanel panel: headersOfHeaderPanel)
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
                //System.out.println("change........."); //test...............................................
                selectedRequestIndex = jListOfRequest.getSelectedIndex();
                //System.out.println("Selected index is: "+selectedRequestIndex); //test...............................................
                Request request = arrayListOfRequestObjects.get(selectedRequestIndex);

                frame.setVisible(true);
                //set request method to combo box
                //System.out.println("method is: "+arrayListOfRequestObjects.get(selectedRequestIndex).getRequestMethod()); //test...............................................
                requestMethodComboBox.setSelectedItem(arrayListOfRequestObjects.get(selectedRequestIndex).getRequestMethod());
                //set url on textfield
                URLTextFiled.setText(request.getURL());
                //set headers
                if (request.getHeaders().size() != 0) {
                    for (String key : request.getHeaders().keySet()) {
                        createHeaderComponent(key, request.getHeaders().get(key));
                    }
                }
                //set response header

                //setMassageBody
                if (request.getMassageBodyType().equals("multipart form")) {
                    if (request.getMassageBodyFormData().size() != 0) {
                        for (String key : request.getMassageBodyFormData().keySet()) {
                            // createHeaderComponent(bodyPanel, themeColor, key, request.getMassageBodyFormData().get(key),true);
                        }
                    }
                } else {
                    //Json
                }
                //set detail
                detailLabelOfThirdPanel.setText(request.getResponseCode() + " " + request.getResponseMassage() + "  " + request.getTime() + " ms   " + request.getLength() + " byte");
                secondPanel.setVisible(false);
                secondPanel.setVisible(true);
                thirdPanel.setVisible(false);
                thirdPanel.setVisible(true);
                frame.setVisible(true);
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

    private void createHeaderComponent(String keyString, String valueString){
        JPanel panel=new JPanel();
        panel.setBackground(themeColor);
        headerPanel.add(panel);
        headersOfHeaderPanel.add(panel);
        //FocusListener
        FocusListener focusListener=new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
            }
            @Override
            public void focusGained(FocusEvent e) {
                createHeaderComponent( "New Key","New Value");
            }
        };
        PTextField textFieldKey=new PTextField(keyString);
        Dimension dimension1=textFieldKey.getPreferredSize();
        dimension1.width=190;
        textFieldKey.setPreferredSize(dimension1);
        textFieldKey.addFocusListener(focusListener);
        PTextField textFieldValue=new PTextField(valueString);
        Dimension dimension2=textFieldValue.getPreferredSize();
        dimension2.width=190;
        textFieldValue.setPreferredSize(dimension2);
        textFieldValue.addFocusListener(focusListener);
        JCheckBox checkBox=new JCheckBox();
        JButton button=new JButton(new ImageIcon("icons\\trash.PNG"));
        button.setText(""+headersOfHeaderPanel.size());
        button.addActionListener(event -> {
            System.out.println("repeat....................................");
            if (headersOfHeaderPanel.size() > 1) {
                headersOfHeaderPanel.remove(Integer.parseInt(button.getText())-2);
                System.out.println("delete"); //test
                headerPanel.remove(headersOfHeaderPanel.remove(Integer.parseInt(button.getText())-2));
                secondPanel.setVisible(false);
                secondPanel.setVisible(true);
                // frame.setVisible(false);
                frame.setVisible(true);
            }
        });
        panel.add(textFieldKey);
        panel.add(textFieldValue);
        panel.add(checkBox);
        panel.add(button);
        frame.setVisible(true);
        if(headersOfHeaderPanel.size()>1){
            headersOfHeaderPanel.get(headersOfHeaderPanel.size()-2).removeAll();
            PTextField aTextFieldKey=new PTextField(keyString);
            Dimension aDimension1=aTextFieldKey.getPreferredSize();
            aDimension1.width=190;
            aTextFieldKey.setPreferredSize(aDimension1);
            PTextField aTextFieldValue=new PTextField(valueString);
            Dimension aDimension2=aTextFieldValue.getPreferredSize();
            aDimension2.width=190;
            aTextFieldValue.setPreferredSize(aDimension2);
            JCheckBox aCheckBox=new JCheckBox();
            JButton aButton=new JButton(new ImageIcon("icons\\trash.PNG"));
            aButton.setText(""+(headersOfHeaderPanel.size()-1));
            headersOfHeaderPanel.get(headersOfHeaderPanel.size()-2).add(aTextFieldKey);
            headersOfHeaderPanel.get(headersOfHeaderPanel.size()-2).add(aTextFieldValue);
            headersOfHeaderPanel.get(headersOfHeaderPanel.size()-2).add(aCheckBox);
            headersOfHeaderPanel.get(headersOfHeaderPanel.size()-2).add(aButton);
            secondPanel.setVisible(false); //test
            secondPanel.setVisible(true);
            frame.setVisible(true);
            aButton.addActionListener(actionEvent -> {
                System.out.println("repeat....................................");
                if (headersOfHeaderPanel.size() > 1) {
                    headersOfHeaderPanel.remove(Integer.parseInt(aButton.getText())-2);
                    System.out.println("delete number is: "+(Integer.parseInt(aButton.getText())-2)); //test
                    headerPanel.remove(headersOfHeaderPanel.remove(Integer.parseInt(aButton.getText())-2));
                    secondPanel.setVisible(false);
                    secondPanel.setVisible(true);
                    //frame.setVisible(false);
                    frame.setVisible(true);
                }
            });
        }
    }

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
                System.out.println("text is: "+URLTextFiled.getText()); //test.......................................
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
        createHeaderComponent("New Key", "New Value");
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
                    System.out.println("yeeeeee");
                });
                JMenuItem FormURLEncoded = new JMenuItem("Form URL Encoded");
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
        detailLabelOfThirdPanel=new JLabel("0 null 0 ms 0 Byte");
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
                JMenuItem previewItem=new JMenuItem("Preview");
                JMenuItem jsonItem = new JMenuItem("JSON");
                popupMenu.add(rawItem); popupMenu.add(previewItem); popupMenu.add(jsonItem);
                popupMenu.show(frame , 703, 180);
                tabbedPaneOfThirdPanel.setSelectedIndex(0);
            }
            if(tabbedPaneOfThirdPanel.getSelectedIndex()==2){
                JPanel centerHeader=new JPanel();
                centerHeader.setLayout(new GridLayout(9,2));
                centerHeader.setBackground(themeColor);
                someUnknownComponent.add(centerHeader);
                centerHeader.add((new JLabel(" NAME")));
                JLabel name=new JLabel(" null");
                labelsOfHeaderInThirdPanel.add(name);
                centerHeader.add(name);

                centerHeader.add(new JLabel(" Server"));
                JLabel server=new JLabel(" null");
                labelsOfHeaderInThirdPanel.add(server);
                centerHeader.add(server);

                centerHeader.add(new JLabel(" Date"));
                JLabel date=new JLabel(" null");
                labelsOfHeaderInThirdPanel.add(date);
                centerHeader.add(date);

                centerHeader.add(new JLabel(" Content-Type"));
                JLabel contentType=new JLabel(" null");
                labelsOfHeaderInThirdPanel.add(contentType);
                centerHeader.add(contentType);

                centerHeader.add(new JLabel(" Content-Length"));
                JLabel contentLength=new JLabel(" null");
                labelsOfHeaderInThirdPanel.add(contentLength);
                centerHeader.add(contentLength);

                centerHeader.add(new JLabel(" Connection"));
                JLabel connection=new JLabel(" null");
                labelsOfHeaderInThirdPanel.add(connection);
                centerHeader.add(connection);

                centerHeader.add(new JLabel(" Last-Modified"));
                JLabel lastModified=new JLabel(" null");
                labelsOfHeaderInThirdPanel.add(lastModified);
                centerHeader.add(lastModified);

                centerHeader.add(new JLabel(" ETag"));
                JLabel ETag=new JLabel(" null");
                labelsOfHeaderInThirdPanel.add(ETag);
                centerHeader.add(ETag);

                centerHeader.add(new JLabel(" Accept-Ranges"));
                JLabel acceptRanges=new JLabel(" null");
                labelsOfHeaderInThirdPanel.add(acceptRanges);
                centerHeader.add(acceptRanges);

                finalAHeader.add(centerHeader, BorderLayout.CENTER);

                JButton copyButton=new JButton("Copy to clipboard");
                copyButton.addActionListener(CBEvent->{
                    StringSelection stringSelection=new StringSelection("Name: "+name.getText()+"\nServer: "+
                            server.getText()+"\nDate: "+date.getText()+"\nContent-Type: "+contentType.getText()+
                            "\nContent-Length: "+contentLength.getText()+"\nConnection: "+connection.getText()+
                            "\nLast-Modified: "+lastModified.getText()+"\nETag: "+ETag.getText()+"\nAccept-Ranges: "+
                            acceptRanges.getText());
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
        // other
        try(ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("save\\hashMap.src"))){
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

    private boolean stringToBoolean(String string){
        return string.equals("true");
    }

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
        try(ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("save\\hashMap.src"))){
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
            System.out.println(request.getRequestMethod()+" "+request.getName());
        }
    }

    /**
     * This method show frame.
     */
    public void showFrame(){
        frame.setVisible(true);
    }
}

class PTextField extends JTextField implements Serializable {
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
