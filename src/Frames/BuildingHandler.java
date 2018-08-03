package Frames;

import ConstantTypes.Variables;
import GameCore.GameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuildingHandler implements ActionListener {

    private BuildingConstructionSelect selectWindow;
    private boolean readyForBuilder;
    private int selectedBuildingType;
    private JComboBox selectedCombobox;
    private int selectedIdNum;

    public BuildingHandler(BuildingConstructionSelect selectWindow) {
        this.selectWindow = selectWindow;
        readyForBuilder = false;
        selectedBuildingType = Variables.TYPE_BUILDING_UNKNOWN;
        selectedCombobox = selectWindow.getBuildingSelectionCombobox();
        selectedIdNum = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = "";
        if (e.getSource() instanceof JButton)
            command = ((JButton) e.getSource()).getActionCommand();

        switch (command) {
            case Variables.COMMAND_BUILD:
                //TODO select building for build
                switch (selectedCombobox.getSelectedIndex()) {
                    case 0:
                        selectedBuildingType = Variables.TYPE_MAIN_BUILDING;
                        break;
                    case 1:
                        selectedBuildingType = Variables.TYPE_RALLYPOINT_BUILDING;
                        break;
                    case 2:
                        selectedBuildingType = Variables.TYPE_BARRACKS_BUILDING;
                        break;
                }
                GameFrame.createBuilding(selectedBuildingType, selectedIdNum);
                //  readyForBuilder = true;
                selectWindow.setVisible(false);
                break;
            case Variables.COMMAND_CANCEL:
                //TODO cancel building window
                selectWindow.setVisible(false);
                break;
        }
    }

    public void setSelectedIdNum(int selectedIdNum) {
        this.selectedIdNum = selectedIdNum;
    }

    public int getSelectedIdNum() {
        return selectedIdNum;
    }

    public boolean isReadyForBuilder() {
        return readyForBuilder;
    }

    public void reset() {
        // readyForBuilder = false;
        // GameFrame.selectedBuildingFromMenu = Variables.TYPE_BUILDING_UNKNOWN;
    }
}
