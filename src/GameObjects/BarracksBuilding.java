package GameObjects;

import ConstantTypes.Variables;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BarracksBuilding extends Building {
    public BarracksBuilding(int xPos, int yPos, BufferedImage image, int idNum) {
        super(xPos, yPos, image, idNum);
        setBuildingType(Variables.TYPE_BARRACKS_BUILDING);
        setBuildTime(10);
        requiredBuildings.put(Variables.TYPE_MAIN_BUILDING, 3);
        requiredBuildings.put(Variables.TYPE_RALLYPOINT_BUILDING, 1);
    }

    @Override
    public boolean select() {
        if (!inBuildTime) {
            System.out.println("Main Building select");
            try {
                if (isMouseIn()) {
                    image = ImageIO.read(new File("src\\Resources\\buildings\\barracks2-hover.png"));
                    return true;
                    //  System.out.println("Hovered");
                } else {
                    image = ImageIO.read(new File("src\\Resources\\buildings\\barracks2.png"));
                    return false;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
