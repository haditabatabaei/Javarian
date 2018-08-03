package GameObjects;

import ConstantTypes.Variables;
import GameCore.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BuildingPlace extends GameObject {

    public BuildingPlace(int xPos, int yPos, BufferedImage image, int idNum) {
        super(xPos, yPos, image, Variables.TYPE_BUILDING_PLACE, idNum);
    }

    @Override
    public void update(GameState state) {
        boolean xSelect = false;
        boolean ySelect = false;
        if (state.mouseX >= xPos && state.mouseX <= xPos + image.getWidth())
            xSelect = true;
        if (state.mouseY >= yPos && state.mouseY <= yPos + image.getHeight())
            ySelect = true;
        mouseIn = (xSelect && ySelect);
        if (mouseIn && state.mousePress) {
            System.out.println("Clicked on building place id : " + idNum + " type : " + buildingType);
            setDestroyed(true);
            state.mousePress = false;
            System.out.println("Building place id : " + idNum + " destroyed : " + " type : " + buildingType);
        }
    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.drawImage(image, xPos, yPos, image.getWidth(), image.getHeight(), null);
    }

    @Override
    public boolean select() {
        try {
            if (isMouseIn()) {
                if (idNum < 13) {
                    image = ImageIO.read(new File("src\\Resources\\buildingPosition2-hover.png"));
                } else {
                    image = ImageIO.read(new File("src\\Resources\\curveBuildingPlace-hover.png"));
                }
                return true;
                //  System.out.println("Hovered");
            } else {
                if (idNum < 13) {
                    image = ImageIO.read(new File("src\\Resources\\buildingPosition2.png"));
                } else {
                    image = ImageIO.read(new File("src\\Resources\\curveBuildingPlace.png"));
                }
                return false;
                //System.out.println("UnHovered");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public BufferedImage getImage() {
        return image;
    }
}
