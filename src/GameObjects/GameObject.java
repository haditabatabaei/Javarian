package GameObjects;

import GameCore.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class GameObject {
    protected int xPos;
    protected int yPos;
    protected int buildingType;
    protected int idNum;
    protected boolean mouseIn;
    protected boolean destroyed;
    protected ExecutorService executorService;
    protected BufferedImage image;


    public GameObject(int xPos, int yPos, BufferedImage image, int buildingType, int idNum) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.image = image;
        this.buildingType = buildingType;
        this.idNum = idNum;

        mouseIn = false;
        destroyed = false;

        executorService = Executors.newCachedThreadPool();
    }


    public int getIdNum() {
        return idNum;
    }

    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public int getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(int type) {
        this.buildingType = type;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean isAlive() {
        return !destroyed;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public boolean isMouseIn() {
        return mouseIn;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public abstract void update(GameState state);

    public abstract void render(Graphics2D graphics2D);

    public abstract boolean select();
}
