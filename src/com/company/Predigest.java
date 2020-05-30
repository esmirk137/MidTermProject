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
import java.util.Objects;

/**
 * This class represent graphic of my project, predigest.
 */
public class Predigest {
    private JFrame frame;
    private JButton plusButtonOfFirstPanel;
    private ArrayList<Request> requests;
    private Panel secondPanel;
    private Panel thirdPanel;
    private HashMap<Integer,ArrayList<Panel>> panelNeedToSave;
    private HashMap<Integer,ArrayList<Panel>> someNotImportantPanel;
    private ArrayList<Panel> secondPanelsOfRequestList;
    private ArrayList<Panel> panelsOfHeaderInSecondPanel;
    private ArrayList<Panel> thirdPanelsOfRequestList;
    private ArrayList<Label> labelsOfHeaderInThirdPanel;
    private DefaultListModel demoList;
    private boolean dark;


    /**
     * This is constructor of this class and allocate fields and make main frame.
     * @throws ClassNotFoundException thrown as a result of three different method calls, all of which handling loading classes by name.
     * @throws UnsupportedLookAndFeelException An exception that indicates the requested look & feel management classes are not present on the user's system.
     * @throws InstantiationException Thrown when an application tries to create an instance of a class using the newInstance method in class Class, but the specified class object cannot be instantiated.
     * @throws IllegalAccessException thrown when an application tries to reflectively create an instance (other than an array), set or get a field, or invoke a method, but the currently executing method does not have access to the definition of the specified class, field, method or constructor.
     */
    public Predigest() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        requests=new ArrayList<>();
        secondPanelsOfRequestList=new ArrayList<>();
        thirdPanelsOfRequestList=new ArrayList<>();
        panelsOfHeaderInSecondPanel=new ArrayList<>();
        labelsOfHeaderInThirdPanel=new ArrayList<>();
        dark=false;

        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        //frame[
        frame = new JFrame("Predigest Designer");
        frame.setLocationRelativeTo(null);
        frame.setSize(1200, 700);
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.white);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int closeConfirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit the program?", "Exit",
                        JOptionPane.YES_NO_OPTION);

                if (closeConfirmed == JOptionPane.YES_OPTION) {
                    int saveConfirmed = JOptionPane.showConfirmDialog(null,
                            "Are you want to save the changes?", "Save",
                            JOptionPane.YES_NO_OPTION);
                    if(saveConfirmed == JOptionPane.YES_OPTION) {
                        // save in file
                    }
                    frame.dispose();
                }
            }
        });
        //frame]

        // up side panel(logo+name+...)[
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
        // up side panel(logo+name+...)]

        // first panel[
        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new BorderLayout());
        firstPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        Dimension firstPanelDimension=firstPanel.getPreferredSize();
        firstPanelDimension.width=200;
        firstPanel.setPreferredSize(firstPanelDimension);
        // first panel -> north[
        JPanel northPanelOfFirstPanel=new JPanel();
        northPanelOfFirstPanel.setBackground(Color.white);
        northPanelOfFirstPanel.setLayout(new BorderLayout());
        JPanel searchRequestPanel=new JPanel();
        searchRequestPanel.setBackground(Color.white);
        PTextField searchTextField=new PTextField("Filter");
        Dimension searchTextFieldDimension=searchTextField.getPreferredSize();
        searchTextFieldDimension.width=150;
        searchTextField.setPreferredSize(searchTextFieldDimension);
        searchRequestPanel.add(searchTextField);
        plusButtonOfFirstPanel=new JButton("+");
        plusButtonOfFirstPanel.setFont(new Font("Arial", Font.BOLD, 15));
        Dimension plusButtonOfFirstPanelDimension= plusButtonOfFirstPanel.getPreferredSize();
        plusButtonOfFirstPanelDimension.height=37;
        plusButtonOfFirstPanelDimension.width=37;
        plusButtonOfFirstPanel.setPreferredSize(plusButtonOfFirstPanelDimension);
        plusButtonOfFirstPanel.addActionListener(new buttonActionListener());
        JLabel requestListLabel=new JLabel("Request List:");
        requestListLabel.setFont(new Font("Arial", Font.BOLD, 15));
        requestListLabel.setForeground(new Color(0,100,0));
        northPanelOfFirstPanel.add(searchRequestPanel, BorderLayout.CENTER);
        northPanelOfFirstPanel.add(plusButtonOfFirstPanel, BorderLayout.EAST);
        northPanelOfFirstPanel.add(requestListLabel, BorderLayout.SOUTH);
        firstPanel.add(northPanelOfFirstPanel, BorderLayout.NORTH);
        // first panel -> north]
        // first panel -> center[
        JPanel centerPanelOfFirstPanel = new JPanel();
        centerPanelOfFirstPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        demoList = new DefaultListModel();
        JList requestList=new JList(demoList);
        requestList.addListSelectionListener(es->{
            int index=requestList.getSelectedIndex();
            secondPanel.add(secondPanelsOfRequestList.get(index));
            thirdPanel.add(thirdPanelsOfRequestList.get(index));
            frame.setVisible(true);
        });
        firstPanel.add(requestList, BorderLayout.CENTER);
        // first panel -> center]
        frame.add(firstPanel,BorderLayout.WEST);
        // first panel]

        // second panel[
        secondPanel = new Panel();
        secondPanel.setBackground(Color.white);
        secondPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        secondPanel.setLayout(new CardLayout());
        frame.add(secondPanel,BorderLayout.CENTER);
        // second panel]
        // third panel[
        thirdPanel = new Panel();
        thirdPanel.setBackground(Color.white);
        thirdPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        thirdPanel.setLayout(new CardLayout());
        frame.add(thirdPanel,BorderLayout.EAST);
        // third panel]

        // load first panel[
        File[] requestFile=textFileFinder("save\\requests");
        if(requestFile.length!=0) {
            for (File i : requestFile) {
                try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("save\\requests\\" + i.getName()))) {
                    Request request = (Request) objectInputStream.readObject();
                    requests.add(request);
                    demoList.addElement(request.getType() + " " + request.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        File[] secondPanelFile=textFileFinder("save\\secondPanels");
        if(requestFile.length!=0) {
            for (File i : secondPanelFile) {
                try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("save\\secondPanels\\" + i.getName()))) {
                    System.out.print("name: "+i.getName());
                    Panel aSecondPanel = (Panel) objectInputStream.readObject();
                    secondPanelsOfRequestList.add(aSecondPanel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        File[] thirdPanelFile=textFileFinder("save\\thirdPanels");
        if(requestFile.length!=0) {
            for (File i : thirdPanelFile) {
                try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("save\\thirdPanels\\" + i.getName()))) {
                    Panel aThirdPanel = (Panel) objectInputStream.readObject();
                    thirdPanelsOfRequestList.add(aThirdPanel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // load first panel]


        // frame menu bar[
        JMenuBar menuBar = new JMenuBar();
        JMenu application, edit, view, help, option;
        JMenuItem exit, screen, slider, about, helpItem;
        JCheckBox followRedirect, exitType;
        ComboBox theme;
        application = new JMenu("Application");
        application.setMnemonic('p');
        option=new JMenu("Option");
        option.setMnemonic('O');
        followRedirect=new JCheckBox("Follow redirect");
        followRedirect.setMnemonic('F');
        exitType=new JCheckBox("Hide on system tray");
        exitType.setMnemonic('s');
        theme=new ComboBox(new String[]{"Light theme", "Dark theme"});
        theme.addActionListener(e ->{
            if(Objects.equals(theme.getSelectedItem(), "Light theme")){

            }
            else{

            }
        });
        option.add(followRedirect);
        option.add(exitType);
        option.add(theme);
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
        screen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK));
        slider=new JMenuItem("Toggle Slider",'S');
        screen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
        view.add(screen);
        view.add(slider);
        help = new JMenu("Help");
        help.setMnemonic('e');
        about=new JMenuItem("About",'A');
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
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
                Request request=new Request(comboBox.getSelectedItem().toString(),requestTextField.getText(),"");
                requests.add(request);
                demoList.addElement(request.getType()+" "+requestTextField.getText());
                requestFrame.dispatchEvent(new WindowEvent(requestFrame, WindowEvent.WINDOW_CLOSING));
                secondPanelsOfRequestList.add(makeSecondPanel());
                thirdPanelsOfRequestList.add(makeThirdPanel());
                frame.setVisible(true);
            });
            // new request action]
        });
    }

    /**
     * This method find text file in a folder.
     * @return these file as array
     */
    private File[] textFileFinder(String path){
        File dir = new File(path);
        return dir.listFiles((dir1, filename) -> filename.endsWith(".txt"));
    }

    private void createHeaderComponent(@NotNull Panel headerPanel){
        Panel panel=new Panel();
        panel.setBackground(Color.white);
        PTextField textFieldHeader=new PTextField("New Header");
        Dimension dimension1=textFieldHeader.getPreferredSize();
        dimension1.width=190;
        textFieldHeader.setPreferredSize(dimension1);
        FocusListener focusListener=new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
            }
            @Override
            public void focusGained(FocusEvent e) {
                createHeaderComponent(headerPanel);
            }
        };
        textFieldHeader.addFocusListener(focusListener);
        PTextField textFieldValue=new PTextField("New Value");
        Dimension dimension2=textFieldValue.getPreferredSize();
        dimension2.width=190;
        textFieldValue.setPreferredSize(dimension2);
        textFieldValue.addFocusListener(focusListener);
        CheckBox checkBox=new CheckBox();
        Button button=new Button(new ImageIcon("icons\\trash.PNG"));
        panel.add(textFieldHeader);
        panel.add(textFieldValue);
        panel.add(checkBox);
        panel.add(button);
        //System.out.println("Count: $$$$$$$$$$$$$$$$$$ :   "+headerPanel.getComponentCount());
        headerPanel.add(panel);
        frame.setVisible(true);
        int compIndex=headerPanel.getComponentCount();
        if(compIndex!=1) {
            System.out.println("in if..........");
            System.out.println("Panel num: "+(compIndex-2));
            panelsOfHeaderInSecondPanel.get(compIndex-2).removeAll();
            PTextField atextFieldHeader=new PTextField("New Header");
            Dimension adimension1=textFieldHeader.getPreferredSize();
            dimension1.width=190;
            atextFieldHeader.setPreferredSize(adimension1);
            PTextField atextFieldValue=new PTextField("New Value");
            Dimension adimension2=textFieldValue.getPreferredSize();
            dimension2.width=190;
            atextFieldValue.setPreferredSize(adimension2);
            CheckBox acheckBox=new CheckBox();
            Button abutton=new Button(new ImageIcon("icons\\trash.PNG"));
            panelsOfHeaderInSecondPanel.get(compIndex-2).add(atextFieldHeader);
            panelsOfHeaderInSecondPanel.get(compIndex-2).add(atextFieldValue);
            panelsOfHeaderInSecondPanel.get(compIndex-2).add(acheckBox);
            panelsOfHeaderInSecondPanel.get(compIndex-2).add(abutton);
        }
        panelsOfHeaderInSecondPanel.add(panel);
    }

    /**
     *
     * @return
     */
    @NotNull
    private Panel makeSecondPanel(){
        Panel secondPanel = new Panel();
        secondPanel.setLayout(new BorderLayout());
        secondPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        // second panel -> north[
        Panel northPanelOfSecondPanel=new Panel();
        northPanelOfSecondPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        northPanelOfSecondPanel.setBackground(Color.white);
        ComboBox typeComboBox=new ComboBox(new String[]{"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"});
        PTextField URLTextFieldSecondPanel=new PTextField("https://...");
        Dimension textFieldSecondPanelDimension=URLTextFieldSecondPanel.getPreferredSize();
        textFieldSecondPanelDimension.width=320;
        URLTextFieldSecondPanel.setPreferredSize(textFieldSecondPanelDimension);
        Button sendButton=new Button("Send");
        northPanelOfSecondPanel.add(typeComboBox);
        northPanelOfSecondPanel.add(URLTextFieldSecondPanel);
        northPanelOfSecondPanel.add(sendButton);
        secondPanel.add(northPanelOfSecondPanel,BorderLayout.NORTH);
        // second panel -> north]
        // second panel -> center[
        JPanel centerPanelOfSecondPanel=new JPanel();
        centerPanelOfSecondPanel.setLayout(new BorderLayout());
        centerPanelOfSecondPanel.setBackground(Color.white);
        TabbedPane tabbedPaneOfSecondPanel=new TabbedPane();
        Panel body=new Panel();
        body.setBackground(Color.white);
        Panel bodyItem=new Panel();
        Panel auth=new Panel();
        auth.setBackground(Color.white);
        Panel authItem=new Panel();
        Panel query=new Panel();
        query.setBackground(Color.white);
        Panel header=new Panel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Color.white);
        createHeaderComponent(header);
        Panel docs=new Panel();
        docs.setBackground(Color.white);
        tabbedPaneOfSecondPanel.add("Body",body);
        tabbedPaneOfSecondPanel.add("v",bodyItem);
        tabbedPaneOfSecondPanel.add("Auth",auth);
        tabbedPaneOfSecondPanel.add("v",authItem);
        tabbedPaneOfSecondPanel.add("query",query);
        tabbedPaneOfSecondPanel.add("header",header);
        tabbedPaneOfSecondPanel.add("docs",docs);
        centerPanelOfSecondPanel.add(tabbedPaneOfSecondPanel, BorderLayout.NORTH);
        secondPanel.add(centerPanelOfSecondPanel,BorderLayout.CENTER);
        tabbedPaneOfSecondPanel.addChangeListener(changeEvent -> {
            if(tabbedPaneOfSecondPanel.getSelectedIndex()==1){
                PopupMenu popupMenu = new PopupMenu();
                MenuItem MultipartForm = new MenuItem("Multipart Form");
                MenuItem FormURLEncoded = new MenuItem("Form URL Encoded");
                MenuItem GraphQLQuery = new MenuItem("GraphQL Query");
                MenuItem JSON = new MenuItem("JSON");
                MenuItem XML = new MenuItem("XML");
                MenuItem YAML = new MenuItem("YAML");
                MenuItem EDN = new MenuItem("EDN");
                MenuItem BinaryFile = new MenuItem("Binary File");
                MenuItem noBody = new MenuItem("No Body");
                popupMenu.add("# STRUCTURED ---------"); popupMenu.add(MultipartForm); popupMenu.add(FormURLEncoded); popupMenu.add(GraphQLQuery);
                popupMenu.add("# TEXT ----------------------"); popupMenu.add(JSON); popupMenu.add(XML); popupMenu.add(YAML); popupMenu.add(EDN);
                popupMenu.add("# OTHER -------------------"); popupMenu.add(BinaryFile); popupMenu.add(noBody);
                popupMenu.show(frame , 220, 180);
                tabbedPaneOfSecondPanel.setSelectedIndex(0);
            }
            else if(tabbedPaneOfSecondPanel.getSelectedIndex()==3){
                PopupMenu popupMenu = new PopupMenu();
                MenuItem basic = new MenuItem("Basic Auth");
                MenuItem digest = new MenuItem("Digest Auth");
                MenuItem OAuth1 = new MenuItem("OAuth 1.0");
                MenuItem OAuth2 = new MenuItem("OAuth 2.0");
                MenuItem NTLM = new MenuItem("Microsoft NTLM");
                MenuItem AWS = new MenuItem("AWS IAM v4");
                MenuItem bearer = new MenuItem("Bearer Token");
                MenuItem hawk = new MenuItem("Hawk");
                MenuItem ASAP = new MenuItem("Atlassian ASAP");
                MenuItem netrc = new MenuItem("Netrc File");
                popupMenu.add(basic); popupMenu.add(digest); popupMenu.add(OAuth1); popupMenu.add(OAuth2); popupMenu.add(NTLM);
                popupMenu.add(AWS); popupMenu.add(bearer); popupMenu.add(hawk); popupMenu.add(ASAP);
                popupMenu.add(netrc);
                popupMenu.show(frame , 280, 180);
                tabbedPaneOfSecondPanel.setSelectedIndex(2);
            }
        });
        return secondPanel;
    }


    @NotNull
    private Panel makeThirdPanel(){
        //third panel[
        Panel thirdPanel = new Panel();
        thirdPanel.setLayout(new BorderLayout());
        thirdPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        Dimension thirdPanelDimension=thirdPanel.getPreferredSize();
        thirdPanelDimension.width=500;
        thirdPanel.setPreferredSize(thirdPanelDimension);
        // third panel -> north[
        Panel northPanelOfThirdPanel=new Panel();
        northPanelOfThirdPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        northPanelOfThirdPanel.setBackground(Color.white);
        //northPanelOfThirdPanel.setBorder(new Border());
        Label labelDetailOfThirdPanel=new Label("200 OK  3 s  20 KB");
        labelDetailOfThirdPanel.setFont(new Font("Serif", Font.ITALIC, 21));
        labelDetailOfThirdPanel.setForeground(new Color(0,100,0));
        northPanelOfThirdPanel.add(labelDetailOfThirdPanel);
        thirdPanel.add(northPanelOfThirdPanel,BorderLayout.NORTH);
        // third panel -> north]
        // third panel -> center[
        Panel centerPanelOfThirdPanel=new Panel();
        centerPanelOfThirdPanel.setLayout(new BorderLayout());
        centerPanelOfThirdPanel.setBackground(Color.white);
        TabbedPane tabbedPaneOfThirdPanel=new TabbedPane();
        Panel raw=new Panel();
        raw.setBackground(Color.white);
        Panel rawItemPanel=new Panel();
        Panel aHeader=new Panel();
        aHeader.setBackground(Color.white);
        aHeader.setLayout(new BorderLayout());
        Panel cookie=new Panel();
        cookie.setBackground(Color.white);
        Panel timeLine=new Panel();
        timeLine.setBackground(Color.white);
        tabbedPaneOfThirdPanel.add("Raw",raw);
        tabbedPaneOfThirdPanel.add("v",rawItemPanel);
        tabbedPaneOfThirdPanel.add("Header",aHeader);
        tabbedPaneOfThirdPanel.addChangeListener(event->{
            if(tabbedPaneOfThirdPanel.getSelectedIndex()==1){
                PopupMenu popupMenu = new PopupMenu();
                MenuItem rawItem = new MenuItem("Raw");
                MenuItem jsonItem = new MenuItem("JSON");
                popupMenu.add(rawItem); popupMenu.add(jsonItem);
                popupMenu.show(frame , 703, 180);
                tabbedPaneOfThirdPanel.setSelectedIndex(0);
            }
            if(tabbedPaneOfThirdPanel.getSelectedIndex()==2){
                Panel centerHeader=new Panel();
                centerHeader.setLayout(new GridLayout(9,2));
                centerHeader.setBackground(Color.white);
                centerHeader.add((new Label(" NAME")));
                Label name=new Label(" null");
                labelsOfHeaderInThirdPanel.add(name);
                centerHeader.add(name);

                centerHeader.add(new Label(" Server"));
                Label server=new Label(" null");
                labelsOfHeaderInThirdPanel.add(server);
                centerHeader.add(server);

                centerHeader.add(new Label(" Date"));
                Label date=new Label(" null");
                labelsOfHeaderInThirdPanel.add(date);
                centerHeader.add(date);

                centerHeader.add(new Label(" Content-Type"));
                Label contentType=new Label(" null");
                labelsOfHeaderInThirdPanel.add(contentType);
                centerHeader.add(contentType);

                centerHeader.add(new Label(" Content-Length"));
                Label contentLength=new Label(" null");
                labelsOfHeaderInThirdPanel.add(contentLength);
                centerHeader.add(contentLength);

                centerHeader.add(new Label(" Connection"));
                Label connection=new Label(" null");
                labelsOfHeaderInThirdPanel.add(connection);
                centerHeader.add(connection);

                centerHeader.add(new Label(" Last-Modified"));
                Label lastModified=new Label(" null");
                labelsOfHeaderInThirdPanel.add(lastModified);
                centerHeader.add(lastModified);

                centerHeader.add(new Label(" ETag"));
                Label ETag=new Label(" null");
                labelsOfHeaderInThirdPanel.add(ETag);
                centerHeader.add(ETag);

                centerHeader.add(new Label(" Accept-Ranges"));
                Label acceptRanges=new Label(" null");
                labelsOfHeaderInThirdPanel.add(acceptRanges);
                centerHeader.add(acceptRanges);

                aHeader.add(centerHeader, BorderLayout.CENTER);

                Button copyButton=new Button("Copy to clipboard");
                copyButton.addActionListener(CBEvent->{
                    StringSelection stringSelection=new StringSelection("Name: "+name.getText()+"\nServer: "+
                            server.getText()+"\nDate: "+date.getText()+"\nContent-Type: "+contentType.getText()+
                            "\nContent-Length: "+contentLength.getText()+"\nConnection: "+connection.getText()+
                            "\nLast-Modified: "+lastModified.getText()+"\nETag: "+ETag.getText()+"\nAccept-Ranges: "+
                            acceptRanges.getText());
                    Clipboard clipboard= Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection,null);
                });
                aHeader.add(copyButton, BorderLayout.SOUTH);
            }
        });
        tabbedPaneOfThirdPanel.add("Cookie",cookie);
        tabbedPaneOfThirdPanel.add("TimeLine",timeLine);
        centerPanelOfThirdPanel.add(tabbedPaneOfThirdPanel);
        thirdPanel.add(centerPanelOfThirdPanel,BorderLayout.CENTER);
        // third panel -> center]
        return thirdPanel;
        //third panel]
    }

    private void darkLight(){

    }
    /**
     * This class implement "ActionListener" class for action of buttons.
     */
    private class buttonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getSource().equals(plusButtonOfFirstPanel)){
                plusButtonAction();
            }
        }
    }

    private void save(int numberOfRequest) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("save\\requests\\"+ numberOfRequest + ".txt"))) {
            objectOutputStream.writeObject(requests.get(numberOfRequest));
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("save\\secondPanels\\"+ numberOfRequest + ".txt"));ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("save\\secondPanels\\"+ numberOfRequest + ".txt"))) {
            objectOutputStream.writeObject(secondPanelsOfRequestList.get(numberOfRequest));

        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("save\\thirdPanels\\"+ numberOfRequest + ".txt"))) {
            objectOutputStream.writeObject(thirdPanelsOfRequestList.get(numberOfRequest));
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

class TabbedPane extends JTabbedPane implements Serializable{
}

class PopupMenu extends JPopupMenu implements Serializable{
}

class CheckBox extends JCheckBox implements Serializable{
}

class MenuItem extends JMenuItem implements Serializable{
    public MenuItem(String string){
        super(string);
    }
}

class ComboBox extends JComboBox implements Serializable{
    public ComboBox(String[] arrayOfString){
        super(arrayOfString);
    }
}

class Button extends JButton implements Serializable {
    public Button(String text){
        super(text);
    }
    public Button(ImageIcon imageIcon){
        super(imageIcon);
    }
}

class Label extends JLabel implements Serializable {
    public Label(String string){
        super(string);
    }
}

class Panel extends JPanel implements Serializable{
}