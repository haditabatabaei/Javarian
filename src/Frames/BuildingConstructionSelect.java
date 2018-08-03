/*
 * Created by JFormDesigner on Thu Aug 02 18:06:51 IRDT 2018
 */

package Frames;

import ConstantTypes.Variables;

import java.awt.*;
import javax.swing.*;

/**
 * @author Hadi
 */
public class BuildingConstructionSelect extends JFrame implements Runnable {
    public BuildingConstructionSelect() {
        initComponents();
        //   setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        buildingSelectionCombobox = new JComboBox();
        selectionLabel = new JLabel();
        buildButton = new JButton();
        buildingNameLabel = new JLabel();
        buidinDesLabel = new JLabel();
        buildingImageLabel = new JLabel();
        panel1 = new JPanel();
        cancelButton = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(116, 197, 116));
        setUndecorated(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- buildingSelectionCombobox ----
        buildingSelectionCombobox.setBackground(new Color(0, 153, 0));
        buildingSelectionCombobox.setForeground(Color.white);
        contentPane.add(buildingSelectionCombobox);
        buildingSelectionCombobox.setBounds(20, 40, 190, buildingSelectionCombobox.getPreferredSize().height);

        //---- selectionLabel ----
        selectionLabel.setText("Select Building : ");
        selectionLabel.setForeground(Color.white);
        contentPane.add(selectionLabel);
        selectionLabel.setBounds(new Rectangle(new Point(20, 10), selectionLabel.getPreferredSize()));

        //---- buildButton ----
        buildButton.setText("Build");
        buildButton.setBackground(new Color(0, 127, 14));
        buildButton.setForeground(Color.white);
        buildButton.setFocusPainted(false);
        contentPane.add(buildButton);
        buildButton.setBounds(20, 80, 90, buildButton.getPreferredSize().height);

        //---- buildingNameLabel ----
        buildingNameLabel.setText("Name :  Main");
        buildingNameLabel.setForeground(Color.white);
        contentPane.add(buildingNameLabel);
        buildingNameLabel.setBounds(225, 40, 335, buildingNameLabel.getPreferredSize().height);

        //---- buidinDesLabel ----
        buidinDesLabel.setText("Description : Your Main Building");
        buidinDesLabel.setForeground(Color.white);
        contentPane.add(buidinDesLabel);
        buidinDesLabel.setBounds(225, 70, 340, 16);

        //---- buildingImageLabel ----
        buildingImageLabel.setIcon(new ImageIcon("C:\\Users\\Hadi-PC\\Desktop\\Advanced Programming - JAVA\\projects\\JavaStratGame\\src\\Resources\\buildings\\mainBuilding2.png"));
        contentPane.add(buildingImageLabel);
        buildingImageLabel.setBounds(5, 130, 140, 135);

        //======== panel1 ========
        {
            panel1.setBackground(new Color(83, 211, 83));
            panel1.setLayout(null);

            //---- cancelButton ----
            cancelButton.setText("Cancel");
            cancelButton.setBackground(new Color(0, 127, 14));
            cancelButton.setForeground(Color.white);
            cancelButton.setFocusPainted(false);
            panel1.add(cancelButton);
            cancelButton.setBounds(120, 110, 90, 26);
        }
        contentPane.add(panel1);
        panel1.setBounds(0, -30, 620, 330);

        contentPane.setPreferredSize(new Dimension(620, 300));
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

        buildingSelectionCombobox.addItem("Main Building");
        buildingSelectionCombobox.addItem("RallyPoint Building");
        buildingSelectionCombobox.addItem("Barracks Building");

        changeListener = new SelectBoxChangeListener(this);
        buildingSelectionCombobox.addItemListener(changeListener);

        handler = new BuildingHandler(this);

        buildButton.setActionCommand(Variables.COMMAND_BUILD);
        cancelButton.setActionCommand(Variables.COMMAND_CANCEL);

        buildButton.addActionListener(handler);
        cancelButton.addActionListener(handler);
    }

    public BuildingHandler getHandler() {
        return handler;
    }

    public JComboBox getBuildingSelectionCombobox() {
        return buildingSelectionCombobox;
    }

    public JLabel getBuidinDesLabel() {
        return buidinDesLabel;
    }

    public JLabel getBuildingImageLabel() {
        return buildingImageLabel;
    }

    public JLabel getBuildingNameLabel() {
        return buildingNameLabel;
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JComboBox buildingSelectionCombobox;
    private JLabel selectionLabel;
    private JButton buildButton;
    private JLabel buildingNameLabel;
    private JLabel buidinDesLabel;
    private JLabel buildingImageLabel;
    private JPanel panel1;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private BuildingHandler handler;
    private SelectBoxChangeListener changeListener;

    @Override
    public void run() {

    }
}
