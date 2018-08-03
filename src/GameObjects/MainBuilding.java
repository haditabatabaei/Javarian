package GameObjects;

import ConstantTypes.Variables;
import GameCore.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainBuilding extends Building {
    public MainBuilding(int xPos, int yPos, BufferedImage image, int idNum) {
        super(xPos, yPos, image, idNum);
        setBuildingType(Variables.TYPE_MAIN_BUILDING);
        setBuildTime(20);
    }

    @Override
    public boolean select() {
        if (!inBuildTime) {
            System.out.println("Main Building select");
            try {
                if (isMouseIn()) {
                    image = ImageIO.read(new File("src\\Resources\\buildings\\mainBuilding2-hover.png"));
                    return true;
                    //  System.out.println("Hovered");
                } else {
                    image = ImageIO.read(new File("src\\Resources\\buildings\\mainBuilding2.png"));
                    return false;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
