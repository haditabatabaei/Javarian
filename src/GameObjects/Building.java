package GameObjects;

import ConstantTypes.Variables;
import GameCore.GameState;
import Warns.MessageWarn;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public abstract class Building extends GameObject {
    protected long startTime;
    protected long lastTime;
    protected long timeSpendInMilis;
    protected int timeSpendInSec;
    protected int buildTime;
    protected int level;
    protected boolean inBuildTime;
    protected HashMap<Integer, Integer> requiredBuildings;


    public Building(int xPos, int yPos, BufferedImage image, int idNum) {
        super(xPos, yPos, image, Variables.TYPE_BUILDING_UNKNOWN, idNum);
        setInBuildTime(true);
        setDestroyed(true);
        startTime = System.currentTimeMillis();
        requiredBuildings = new HashMap<>();
    }

    public HashMap<Integer, Integer> getRequiredBuildings() {
        return requiredBuildings;
    }

    public void setInBuildTime(boolean inBuildTime) {
        this.inBuildTime = inBuildTime;
    }

    public boolean isInBuildTime() {
        return inBuildTime;
    }

    public int getBuildTime() {
        return buildTime;
    }

    public int getTimeSpendInSec() {
        return timeSpendInSec;
    }

    public void setBuildTime(int buildTime) {
        this.buildTime = buildTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public void update(GameState state) {
        lastTime = System.currentTimeMillis();
        timeSpendInMilis = lastTime - startTime;
        timeSpendInSec = (int) (timeSpendInMilis / 1000);
        if (timeSpendInSec >= buildTime && isInBuildTime()) {
            setInBuildTime(false);
            setDestroyed(false);
            System.out.println("Construction Completed.");
            executorService.execute(new MessageWarn(buildingType, Variables.TYPE_BUILDING_COMPLETED, JOptionPane.INFORMATION_MESSAGE));
        } else if (isInBuildTime()) {
            System.out.println("Construction in progress;");
            lastTime += timeSpendInMilis;
        }
        System.out.println("Rally Point Update");
        boolean xSelect = false;
        boolean ySelect = false;
        if (state.mouseX >= xPos && state.mouseX <= xPos + image.getWidth())
            xSelect = true;
        if (state.mouseY >= yPos && state.mouseY <= yPos + image.getHeight())
            ySelect = true;
        mouseIn = (xSelect && ySelect);
        System.out.println("Rally Point mouse in : " + mouseIn);
        if (mouseIn && state.mousePress) {
            System.out.println("Inside RallyPoint");
        }
    }

    public void render(Graphics2D graphics2D) {
        graphics2D.drawImage(image, xPos, yPos, image.getWidth(), image.getHeight(), null);
    }

    @Override
    public abstract boolean select();
}
