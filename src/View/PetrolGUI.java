package View;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import Controller.LaunchSim;
import Controller.Simulation;
public class PetrolGUI {
    private JFrame newFrame;
    private JTextField gPrice;
    private JTextField numOfTicks;
    private JComboBox<String> numOfPumps;
    private JComboBox<String> numOfTills;
    private LabelledSliderFP pSlider;
    private LabelledSliderFP qSlider;
    private Simulation s;
    private int ticks;
    private double gallonP;
    private double pC;
    private double qC;
    private int nP;
    private int nT;
    private JFrame sFrame;
    /**
     * Constructor for the PetrolGUI.
     * @param s - The new Simulation object. Reason why it is final is because it can't be initialised normally in enclosed spaces,
     * i.e. when ActionListeners are added.
     */
    public PetrolGUI(final Simulation s) {
//Step 1: Create the components
        JButton startButton = new JButton();
        JButton exitButton = new JButton();
        pSlider = new LabelledSliderFP("Probability of p: ", 0.03, 1, 5, 100);
        qSlider = new LabelledSliderFP("Probability of q: ", 0.03, 1, 5, 100);
        JLabel price = new JLabel();
        JLabel numPumps = new JLabel();
        JLabel numTills = new JLabel();
        JLabel numTicks = new JLabel();
        gPrice = new JTextField();
        numOfTicks = new JTextField();
        String[] pumps = {"1", "2", "4"};
        numOfPumps = new JComboBox<>(pumps);
        String[] tills = {"1", "2", "4"};
        numOfTills = new JComboBox<>(tills);
        this.s = s;
//Step 2: Set the properties of the components
        startButton.setText("Start");
        startButton.setToolTipText("Start simulating with these settings");
        exitButton.setText("Exit");
        exitButton.setToolTipText("Exit the application");
        price.setText("Price per gallon: ");
        numPumps.setText("No. of pumps: ");
        numTills.setText(" No. of tills: ");
        numTicks.setText("No. of ticks: ");
        numOfPumps.setPreferredSize(new Dimension(400, 20));
        numOfTills.setPreferredSize(new Dimension(400, 20));
//Step 3: Create the components to hold the components
        newFrame = new JFrame("Simulator");
        newFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        JPanel sliders = new JPanel();
        JPanel priceBox = new JPanel();
        JPanel dropdown1 = new JPanel();
        JPanel dropdown2 = new JPanel();
        JPanel dropdownBoxes = new JPanel();
        JPanel tickBox = new JPanel();
        JPanel options = new JPanel();
        JPanel commandBox = new JPanel();
//Step 4: Specify the layout managers
        sliders.setLayout(new BorderLayout());
        priceBox.setLayout(new BorderLayout());
        dropdownBoxes.setLayout(new BorderLayout());
        tickBox.setLayout(new BorderLayout());
        options.setLayout(new BorderLayout());
        commandBox.setLayout(new FlowLayout());
//Step 5: Add components to the containers
        sliders.add(pSlider, BorderLayout.NORTH);
        sliders.add(qSlider, BorderLayout.SOUTH);
        priceBox.add(price, BorderLayout.WEST);
        priceBox.add(gPrice, BorderLayout.CENTER);
        dropdown1.add(numPumps, BorderLayout.WEST);
        dropdown1.add(numOfPumps, BorderLayout.CENTER);
        dropdown2.add(numTills, BorderLayout.WEST);
        dropdown2.add(numOfTills, BorderLayout.CENTER);
        dropdownBoxes.add(dropdown1, BorderLayout.NORTH);
        dropdownBoxes.add(dropdown2, BorderLayout.SOUTH);
        tickBox.add(numTicks, BorderLayout.WEST);
        tickBox.add(numOfTicks, BorderLayout.CENTER);
        options.add(priceBox, SpringLayout.NORTH);
        options.add(dropdownBoxes, BorderLayout.CENTER);
        options.add(tickBox, BorderLayout.PAGE_END);
        commandBox.add(startButton);
        commandBox.add(exitButton);
        newFrame.add(sliders, SpringLayout.NORTH);
        newFrame.add(options);
        newFrame.add(commandBox, BorderLayout.SOUTH);
//Step 6: Arrange to handle events in the user interface
        newFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exitApp();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitApp();
            }
        });
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    wantTruck();
                    String gP = gPrice.getText();
                    gallonP = Double.parseDouble(gP);
                    pC = pSlider.getValue();
                    qC = qSlider.getValue();
                    String numP = numOfPumps.getSelectedItem().toString();
                    nP = Integer.parseInt(numP);
                    String numT = numOfTills.getSelectedItem().toString();
                    nT = Integer.parseInt(numT);
                    String t = numOfTicks.getText();
                    ticks = Integer.parseInt(t);
                    startSimulation();
                }
                catch (NumberFormatException | NullPointerException eee ){
                    errorMessage();
                }
            }
        });
//Step 7: Display the GUI
        newFrame.pack();
        newFrame.setResizable(false);
        newFrame.setVisible(true);
    }
    /**
     * Creates a dialog which pops up when exiting the application.
     */
    private void exitApp() {
// Display confirmation dialog before exiting application
        int response = JOptionPane.showConfirmDialog(newFrame,
                "Do you really want to quit?",
                "Quit?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
// Don't quit
    }
    /**
     * Creates a dialog which pops up when the start button is clicked.
     * It asks whether the user wants to have trucks in the simulation.
     */
    private void wantTruck() {
        int response1 = JOptionPane.showConfirmDialog(newFrame,
                "Do you want trucks in your simulation?",
                "With Trucks?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (response1 == JOptionPane.YES_OPTION) {
            s.setTrucks(true);
        }
        else {
            s.setTrucks(false);
        }
    }
    /**
     * Creates a dialog which pops up when the start button is clicked and the price and number of ticks fields are either
     * blank or not in the right format.
     * It tells the user to fill the price and number of ticks fields properly.
     */
    private void errorMessage() {
        int response2 = JOptionPane.showConfirmDialog(newFrame,
                "Fill in the price and number of ticks fields correctly!" + "\n" + "For the price, it's the format '0.00'" +
                        "\n" + "For the ticks, give any integer value, e.g. 1000",
                "Error: NumberFormatException",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE);
    }
    private void fileMessage() {
        int response3 = JOptionPane.showConfirmDialog(sFrame,
                "The excel file has been created!" + "\n" + "Find the file at d://testsheet.xls",
                "File created",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Creates the second layer of the GUI
     */
    public void startSimulation(){
//Step 1: Create the components
        sFrame = new JFrame("Simulation");
        JButton beginButton = new JButton();
        JButton exit = new JButton();
        JLabel name = new JLabel();
        final JLabel stepNumber = new JLabel();
        final JLabel moneyGained = new JLabel();
        final JLabel moneyLost = new JLabel();
        final JLabel moneyGainedS = new JLabel();
        final JLabel moneyLostS = new JLabel();
        JLabel pumps1 = new JLabel();
        JLabel pumps2 = new JLabel();
        JLabel pumps3 = new JLabel();
        JLabel pumps4 = new JLabel();
        JLabel tills1 = new JLabel();
        JLabel tills2 = new JLabel();
        JLabel tills3 = new JLabel();
        JLabel tills4 = new JLabel();
        JTextArea pumpList = new JTextArea();
        JScrollPane l = new JScrollPane(pumpList);
        final LaunchSim ss = new LaunchSim();
        JLabel listOfPumps = new JLabel();
        final JTextField pump1 = new JTextField();
        final JTextField pump2 = new JTextField();
        final JTextField pump3 = new JTextField();
        final JTextField pump4 = new JTextField();
        final JTextField till1 = new JTextField();
        final JTextField till2 = new JTextField();
        final JTextField till3 = new JTextField();
        final JTextField till4 = new JTextField();
        final int value = 0;
//Step 2: Set the properties of the components
        beginButton.setText("Simulate");
        beginButton.setToolTipText("Start the simulation");
        exit.setText("Close");
        exit.setToolTipText("Exit the simulation");
        sFrame.setPreferredSize(new Dimension(900, 378));
        l.setPreferredSize(new Dimension(400, 70));
        pump1.setPreferredSize(new Dimension(400,40));
        pump1.setEditable(false);
        pump2.setPreferredSize(new Dimension(400,40));
        pump2.setEditable(false);
        pump3.setPreferredSize(new Dimension(400,40));
        pump3.setEditable(false);
        pump4.setPreferredSize(new Dimension(400,40));
        pump4.setEditable(false);
        till1.setPreferredSize(new Dimension(400,40));
        till1.setEditable(false);
        till2.setPreferredSize(new Dimension(400,40));
        till2.setEditable(false);
        till3.setPreferredSize(new Dimension(400,40));
        till3.setEditable(false);
        till4.setPreferredSize(new Dimension(400,40));
        till4.setEditable(false);
        listOfPumps.setText("Pumps: ");
        name.setText("PetrolStation Simulation");
        stepNumber.setText("Tick: " + value);
        pumps1.setText("Pump 1: ");
        pumps2.setText("Pump 2: ");
        pumps3.setText("Pump 3: ");
        pumps4.setText("Pump 4: ");
        if (nP == 1) {
            pump1.setText("empty");
            pump2.setText("Not in use");
            pump3.setText("Not in use");
            pump4.setText("Not in use");
        }
        else if (nP == 2) {
            pump1.setText("empty");
            pump2.setText("empty");
            pump3.setText("Not in use");
            pump4.setText("Not in use");
        }
        else {
            pump1.setText("empty");
            pump2.setText("empty");
            pump3.setText("empty");
            pump4.setText("empty");
        }
        tills1.setText("Till 1: ");
        tills2.setText("Till 2: ");
        tills3.setText("Till 3: ");
        tills4.setText("Till 4: ");
        if (nT == 1) {
            till1.setText("empty");
            till2.setText("Not in use");
            till3.setText("Not in use");
            till4.setText("Not in use");
        }
        else if (nT == 2) {
            till1.setText("empty");
            till2.setText("empty");
            till3.setText("Not in use");
            till4.setText("Not in use");
        }
        else {
            till1.setText("empty");
            till2.setText("empty");
            till3.setText("empty");
            till4.setText("empty");
        }
        moneyGained.setText("Total earned petrol money: £" + s.getDatabase().getPetrolEarnedMoney());
        moneyLost.setText("Total lost petrol money: £" + s.getDatabase().getLostPetrolMoney());
        moneyGainedS.setText("Total earned shop money: £" + s.getDatabase().getShopEarned());
        moneyLostS.setText("Total lost shop money: £" + s.getDatabase().getLostShopMoney());
//Step 3: Create the components to hold the components
        JPanel command = new JPanel();
        JPanel pList = new JPanel();
        JPanel namePanel = new JPanel();
        JPanel pumpPanel1 = new JPanel();
        JPanel pumpPanel2 = new JPanel();
        JPanel pumpPanel3 = new JPanel();
        JPanel pumpPanel4 = new JPanel();
        JPanel tillPanel1 = new JPanel();
        JPanel tillPanel2 = new JPanel();
        JPanel tillPanel3 = new JPanel();
        JPanel tillPanel4 = new JPanel();
        JPanel pumpText1 = new JPanel();
        JPanel pumpText2 = new JPanel();
        JPanel pumpText3 = new JPanel();
        JPanel pumpText4 = new JPanel();
        JPanel tillText1 = new JPanel();
        JPanel tillText2 = new JPanel();
        JPanel tillText3 = new JPanel();
        JPanel tillText4 = new JPanel();
        JPanel pPanel = new JPanel();
        JPanel pPanel1 = new JPanel();
        JPanel pPanel2 = new JPanel();
        JPanel tPanel = new JPanel();
        JPanel tPanel1 = new JPanel();
        JPanel tPanel2 = new JPanel();
//Step 4: Specify the layout managers
        sFrame.setLayout(new BorderLayout());
        ((JPanel)sFrame.getContentPane()).setBorder(new EmptyBorder(5, 15, 15, 15));
        command.setLayout(new FlowLayout());
        listOfPumps.setLayout(new BorderLayout());
        pumpPanel1.setLayout(new BorderLayout());
        pumpPanel2.setLayout(new BorderLayout());
        pumpPanel3.setLayout(new BorderLayout());
        pumpPanel4.setLayout(new BorderLayout());
        tillPanel1.setLayout(new BorderLayout());
        tillPanel2.setLayout(new BorderLayout());
        tillPanel3.setLayout(new BorderLayout());
        tillPanel4.setLayout(new BorderLayout());
        pList.setLayout(new BorderLayout());
        pumpText1.setLayout(new BorderLayout());
        pumpText2.setLayout(new BorderLayout());
        pumpText3.setLayout(new BorderLayout());
        pumpText4.setLayout(new BorderLayout());
        tillText1.setLayout(new BorderLayout());
        tillText2.setLayout(new BorderLayout());
        tillText3.setLayout(new BorderLayout());
        tillText4.setLayout(new BorderLayout());
        pPanel.setLayout(new BorderLayout());
        pPanel1.setLayout(new BorderLayout());
        pPanel2.setLayout(new BorderLayout());
        tPanel.setLayout(new BorderLayout());
        tPanel1.setLayout(new BorderLayout());
        tPanel2.setLayout(new BorderLayout());
//Step 5: Add components to the containers
        command.add(beginButton);
        command.add(exit);
        pumpText1.add(pump1);
        pumpText2.add(pump2);
        pumpText3.add(pump3);
        pumpText4.add(pump4);
        tillText1.add(till1);
        tillText2.add(till2);
        tillText3.add(till3);
        tillText4.add(till4);
        pumpPanel1.add(pumps1, BorderLayout.NORTH);
        pumpPanel1.add(pumpText1, BorderLayout.WEST);
        pumpPanel2.add(pumps2, BorderLayout.NORTH);
        pumpPanel2.add(pumpText2, BorderLayout.WEST);
        pumpPanel3.add(pumps3, BorderLayout.NORTH);
        pumpPanel3.add(pumpText3, BorderLayout.WEST);
        pumpPanel4.add(pumps4, BorderLayout.NORTH);
        pumpPanel4.add(pumpText4, BorderLayout.WEST);
        pumpPanel4.add(moneyGained, BorderLayout.SOUTH);
        pPanel1.add(pumpPanel1, BorderLayout.NORTH);
        pPanel1.add(pumpPanel2);
        pPanel2.add(pumpPanel3, BorderLayout.NORTH);
        pPanel2.add(pumpPanel4);
        pPanel.add(pPanel1, BorderLayout.NORTH);
        pPanel.add(pPanel2, BorderLayout.CENTER);
        pPanel.add(moneyLost, BorderLayout.SOUTH);
        tillPanel1.add(tills1, BorderLayout.NORTH);
        tillPanel1.add(tillText1, BorderLayout.WEST);
        tillPanel2.add(tills2, BorderLayout.NORTH);
        tillPanel2.add(tillText2, BorderLayout.WEST);
        tillPanel3.add(tills3, BorderLayout.NORTH);
        tillPanel3.add(tillText3, BorderLayout.WEST);
        tillPanel4.add(tills4, BorderLayout.NORTH);
        tillPanel4.add(tillText4, BorderLayout.WEST);
        tillPanel4.add(moneyGainedS, BorderLayout.SOUTH);
        tPanel1.add(tillPanel1, BorderLayout.NORTH);
        tPanel1.add(tillPanel2);
        tPanel2.add(tillPanel3, BorderLayout.NORTH);
        tPanel2.add(tillPanel4);
        tPanel.add(tPanel1, BorderLayout.NORTH);
        tPanel.add(tPanel2, BorderLayout.CENTER);
        tPanel.add(moneyLostS, BorderLayout.SOUTH);
        pList.add(listOfPumps, BorderLayout.NORTH);
        pList.add(l, BorderLayout.SOUTH);
        namePanel.add(name, BorderLayout.WEST);
        namePanel.add(stepNumber, BorderLayout.SOUTH);
        sFrame.add(namePanel, BorderLayout.NORTH);
        sFrame.add(pPanel, BorderLayout.WEST);
        sFrame.add(tPanel, BorderLayout.EAST);
        sFrame.add(command, BorderLayout.SOUTH);
//Step 6: Arrange to handle events in the user interface
        sFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                sFrame.dispose();
            }
        });
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sFrame.dispose();
            }
        });
        beginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Simulation sss = new Simulation(42, gallonP, pC, qC, nP, nT, ticks, s.getTruck());
                sss.simulate();
                for (int i = 0; i < ticks; i++) {
                    if (nP == 1) {
                        pump1.setText(sss.getPetrolStation().getPump(0).toString());
                    }
                    else if (nP == 2) {
                        pump1.setText(sss.getPetrolStation().getPump(0).toString());
                        pump2.setText(sss.getPetrolStation().getPump(1).toString());
                    }
                    else {
                        pump1.setText(sss.getPetrolStation().getPump(0).toString());
                        pump2.setText(sss.getPetrolStation().getPump(1).toString());
                        pump3.setText(sss.getPetrolStation().getPump(2).toString());
                        pump4.setText(sss.getPetrolStation().getPump(3).toString());
                    }
                    if (nT == 1) {
                        till1.setText(sss.getShop().getTill(0).toStringGUI());
                    }
                    else if (nT == 2) {
                        till1.setText(sss.getShop().getTill(0).toStringGUI());
                        till2.setText(sss.getShop().getTill(1).toStringGUI());
                    }
                    else {
                        till1.setText(sss.getShop().getTill(0).toStringGUI());
                        till2.setText(sss.getShop().getTill(1).toStringGUI());
                        till3.setText(sss.getShop().getTill(2).toStringGUI());
                        till4.setText(sss.getShop().getTill(3).toStringGUI());
                    }
                    moneyGained.setText("Total earned petrol money: £" + sss.getDatabase().getPetrolEarnedMoney());
                    moneyLost.setText("Total lost petrol money: £" + sss.getDatabase().getLostPetrolMoney());
                    moneyGainedS.setText("Total earned shop money: £" + sss.getDatabase().getShopEarned());
                    moneyLostS.setText("Total lost shop money: £" + sss.getDatabase().getLostShopMoney());
                    int value = ticks;
                    stepNumber.setText("Tick: " + value);
                }
                ss.createFile(sss);
                fileMessage();
            }
        });
//Step 7: Display the GUI
        sFrame.pack();
        sFrame.setVisible(true);
    }
}