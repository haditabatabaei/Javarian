package GameObjects;

import ConstantTypes.Variables;
import GameCore.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class BackgroundObject extends GameObject {
    boolean rendered;

    public BackgroundObject(int xPos, int yPos, BufferedImage image, int idNum) {
        super(xPos, yPos, image, Variables.TYPE_BACKGROUND_OBJECT, idNum);
        rendered = false;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public boolean isRendered() {
        return rendered;
    }

    @Override
    public void update(GameState state) {

    }

    @Override
    public void render(Graphics2D graphics2D) {
        graphics2D.drawImage(image, 0, 0, Variables.WINDOW_WIDTH, Variables.WINDOW_HEIGHT, null);
    }

    @Override
    public boolean select() {
        return false;
    }
}
