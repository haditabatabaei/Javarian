package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SelectBoxChangeListener implements ItemListener {
    private JComboBox selectedCombobox;
    private BuildingConstructionSelect selectedWindow;

    public SelectBoxChangeListener(BuildingConstructionSelect selectedWindow) {
        this.selectedWindow = selectedWindow;
        selectedCombobox = selectedWindow.getBuildingSelectionCombobox();
    }

    // @Override
    public void itemStateChanged(ItemEvent e) {
        int state = e.getStateChange();
        if (state == ItemEvent.SELECTED) {
            switch (selectedCombobox.getSelectedIndex()) {
                case 0:
                    selectedWindow.getBuildingNameLabel().setText("Name : Main");
                    selectedWindow.getBuidinDesLabel().setText("Description : Your Main Building");
                    selectedWindow.getBuildingImageLabel().setIcon(new ImageIcon("src\\Resources\\buildings\\mainBuilding2.png"));
                    selectedWindow.revalidate();
                    break;
                case 1:
                    selectedWindow.getBuildingNameLabel().setText("Name : Rally Point");
                    selectedWindow.getBuidinDesLabel().setText("Description : You need this to build barracks");
                    selectedWindow.getBuildingImageLabel().setIcon(new ImageIcon("src\\Resources\\buildings\\rallyPoint2.png"));
                    selectedWindow.revalidate();
                    break;
                case 2:
                    selectedWindow.getBuildingNameLabel().setText("Name : Barracks");
                    selectedWindow.getBuidinDesLabel().setText("Description : You need this to train units");
                    selectedWindow.getBuildingImageLabel().setIcon(new ImageIcon("src\\Resources\\buildings\\barracks2.png"));
                    selectedWindow.revalidate();
                    break;
            }
        }
    }
}
