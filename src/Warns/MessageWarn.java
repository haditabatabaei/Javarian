package Warns;

import ConstantTypes.Variables;

import javax.swing.*;

public class MessageWarn implements Runnable {
    int messageType, buildingType, constructType;

    public MessageWarn(int buildingType, int constructType, int messageType) {
        this.buildingType = buildingType;
        this.constructType = constructType;
        this.messageType = messageType;
    }

    @Override
    public void run() {
        String messageToShow = "Your \"";
        String messageTitle = "Information Message";
        switch (buildingType) {
            case Variables.TYPE_MAIN_BUILDING:
                messageToShow += "Main Building\" ";
                break;
            case Variables.TYPE_BARRACKS_BUILDING:
                messageToShow += "Barracks Building\" ";
                break;
            case Variables.TYPE_RALLYPOINT_BUILDING:
                messageToShow += "Rally Point Building\" ";
                break;
        }
        switch (constructType) {
            case Variables.TYPE_IS_NOW_CONSTRUCTING:
                messageToShow += " is now being constructed...";
                break;
            case Variables.TYPE_BUILDING_DELETED:
                messageToShow += " destroyed.";
                break;
            case Variables.TYPE_BUILDING_REQUIRE_ANOTHER:
                messageToShow += " needs one or more prior buildings.\ncheck building's requirements";
                break;
            case Variables.TYPE_BUILDING_COMPLETED:
                messageToShow += " has been constructed successfully.";
                break;
        }
        JOptionPane.showMessageDialog(null, messageToShow, messageTitle, messageType);
    }
}
