package GameCore;

import ConstantTypes.Variables;
import Frames.BuildingConstructionSelect;
import Frames.BuildingHandler;
import GameObjects.*;
import Warns.MessageWarn;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.HTMLDocument;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering,
 * actually it performs triple-buffering!
 * For more information on BufferStrategy check out:
 * http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 * http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameFrame extends JFrame {

    //uncomment all /*...*/ in the class for using Tank icon instead of a simple circle
    /*private BufferedImage image;*/

    private long lastRender;
    private ArrayList<Float> fpsHistory;

    private BufferStrategy bufferStrategy;
    private BackgroundObject backgroundObject;
    private ArrayList<BuildingPlace> buildingPlaces;
    private static ArrayList<Building> buildings;
    private static ExecutorService executorService;
    private static boolean isCheckingArrays;
    private BuildingConstructionSelect buildingConstructionSelect;
    private BuildingHandler handler;
    private static boolean holderMustRemove;

    public GameFrame(String title) {
        super(title);
        setResizable(false);
        setSize(Variables.WINDOW_WIDTH, Variables.WINDOW_HEIGHT);
        setUndecorated(true);
        buildingConstructionSelect = new BuildingConstructionSelect();
        handler = buildingConstructionSelect.getHandler();
        buildingPlaces = new ArrayList<>();
        buildings = new ArrayList<>();
        lastRender = -1;
        fpsHistory = new ArrayList<>(100);
        isCheckingArrays = false;
        executorService = Executors.newCachedThreadPool();
        try {
            backgroundObject = new BackgroundObject(0, 0, ImageIO.read(new File("src\\Resources\\villageBackground3.png")), 0);
            for (int i = 0; i < 14; i++) {
                BuildingPlace tempBuildingPlace = null;
                switch (i) {
                    case 0:
                        tempBuildingPlace = new BuildingPlace(846, 210, ImageIO.read(new File("src\\Resources\\buildingPosition2.png")), i);
                        break;
                    case 1:
                        tempBuildingPlace = new BuildingPlace(940, 179, ImageIO.read(new File("src\\Resources\\buildingPosition2.png")), i);
                        break;
                    case 2:
                        tempBuildingPlace = new BuildingPlace(1027, 198, ImageIO.read(new File("src\\Resources\\buildingPosition2.png")), i);
                        break;
                    case 3:
                        tempBuildingPlace = new BuildingPlace(1122, 240, ImageIO.read(new File("src\\Resources\\buildingPosition2.png")), i);
                        break;
                    case 4:
                        tempBuildingPlace = new BuildingPlace(1172, 296, ImageIO.read(new File("src\\Resources\\buildingPosition2.png")), i);
                        break;
                    case 5:
                        tempBuildingPlace = new BuildingPlace(1131, 424, ImageIO.read(new File("src\\Resources\\buildingPosition2.png")), i);
                        break;
                    case 6:
                        tempBuildingPlace = new BuildingPlace(944, 511, ImageIO.read(new File("src\\Resources\\buildingPosition2.png")), i);
                        break;
                    case 7:
                        tempBuildingPlace = new BuildingPlace(712, 418, ImageIO.read(new File("src\\Resources\\buildingPosition2.png")), i);
                        break;
                    case 8:
                        tempBuildingPlace = new BuildingPlace(749, 355, ImageIO.read(new File("src\\Resources\\buildingPosition2.png")), i);
                        break;
                    case 9:
                        tempBuildingPlace = new BuildingPlace(720, 300, ImageIO.read(new File("src\\Resources\\buildingPosition2.png")), i);
                        break;
                    case 10:
                        tempBuildingPlace = new BuildingPlace(759, 254, ImageIO.read(new File("src\\Resources\\buildingPosition2.png")), i);
                        break;
                    case 11:
                        tempBuildingPlace = new BuildingPlace(948, 254, ImageIO.read(new File("src\\Resources\\buildingPosition2.png")), i);
                        break;
                    case 12:
                        tempBuildingPlace = new BuildingPlace(1047, 299, ImageIO.read(new File("src\\Resources\\buildingPosition2.png")), i);
                        break;
                    case 13:
                        tempBuildingPlace = new BuildingPlace(831, 288, ImageIO.read(new File("src\\Resources\\curveBuildingPlace.png")), i);
                }
                buildingPlaces.add(tempBuildingPlace);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	/*	try{
			image = ImageIO.read(new File("Icon.png"));
		}
		catch(IOException e){
			System.out.println(e);
		}*/
    }

    /**
     * This must be called once after the JFrame is shown:
     * frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }


    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state, MessageWarn warner) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state, warner);
                } finally {
                    // Dispose the graphics
                    graphics.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());

            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }

    /**
     * Rendering all game elements based on the game state.
     */

    private void handleMouse() {
        boolean cursorMustChange = false;
        for (BuildingPlace buildingPlace : buildingPlaces)
            if (buildingPlace.select() && buildingPlace.isAlive()) {
                cursorMustChange = true;
                break;
            }
        Iterator buildingsIterator = buildings.iterator();
        while (buildingsIterator.hasNext()) {
            Building thisBuilding = (Building) buildingsIterator.next();
            if (thisBuilding.isAlive() && thisBuilding.select()) {
                cursorMustChange = true;
                break;
            }
        }

        if (cursorMustChange)
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        else
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private static boolean hasBuilding(int buildingType) {
        for (Building building : buildings)
            if (building.getBuildingType() == buildingType && !building.isInBuildTime() && building.isAlive())
                return true;

        return false;
    }

    private boolean buildingRequirementIsGood(Building building) {

        ArrayList<Boolean> buildingsRequiredBooleanList = new ArrayList<>();
        HashMap<Integer, Integer> tempRequiredBuildings = building.getRequiredBuildings();

        for (Integer requiredType : tempRequiredBuildings.keySet())
            switch (requiredType) {
                case Variables.TYPE_MAIN_BUILDING:
                    int levelRequired = tempRequiredBuildings.get(requiredType);
                    for (Building buildingInVillage : buildings) {
                        if (buildingInVillage instanceof MainBuilding && buildingInVillage.isAlive() && buildingInVillage.getLevel() == levelRequired) {
                            buildingsRequiredBooleanList.add(true);
                            break;
                        }
                    }
                    break;
                case Variables.TYPE_BARRACKS_BUILDING:
                    levelRequired = tempRequiredBuildings.get(requiredType);
                    for (Building buildingInVillage : buildings)
                        if (buildingInVillage instanceof BarracksBuilding && buildingInVillage.isAlive() && buildingInVillage.getLevel() == levelRequired) {
                            buildingsRequiredBooleanList.add(true);
                            break;
                        }
                    break;
                case Variables.TYPE_RALLYPOINT_BUILDING:
                    levelRequired = tempRequiredBuildings.get(requiredType);
                    for (Building buildingInVillage : buildings)
                        if (buildingInVillage instanceof RallyPointBuilding && buildingInVillage.isAlive() && building.getLevel() == levelRequired) {
                            buildingsRequiredBooleanList.add(true);
                            break;
                        }
            }

        return (buildingsRequiredBooleanList.size() == tempRequiredBuildings.keySet().size());
    }

    private void doRendering(Graphics2D g2d, GameState state, MessageWarn warner) {
        // Draw background
        backgroundObject.render(g2d);


        // System.out.println("Going in handle building places loop");
        //handle building places
        Iterator placesIterator = buildingPlaces.iterator();
        while (placesIterator.hasNext()) {
            BuildingPlace selectedBuildingPlace = (BuildingPlace) placesIterator.next();
            selectedBuildingPlace.update(state);
            if (selectedBuildingPlace.isAlive())
                selectedBuildingPlace.render(g2d);
            else {
                holderMustRemove = false;
                //  placesIterator.remove();
                buildingConstructionSelect.setVisible(true);
                handler.setSelectedIdNum(selectedBuildingPlace.getIdNum());
                //  System.out.println("going inside selected building switch");

                if (holderMustRemove) {
                    placesIterator.remove();
                    //System.out.println("Building added and place holder removed.");
                } else {
                    selectedBuildingPlace.setDestroyed(false);
                    state.mousePress = false;
                }
            }
        }


        //  System.out.println("Going in buildings loop");
        Iterator buildingsIterator = buildings.iterator();
        while (buildingsIterator.hasNext()) {
            Building selectedBuilding = (Building) buildingsIterator.next();
            selectedBuilding.update(state);
            if (selectedBuilding.isAlive() && !selectedBuilding.isInBuildTime())
                selectedBuilding.render(g2d);
            else if (selectedBuilding.isInBuildTime()) {
                String toShow = selectedBuilding.getTimeSpendInSec() + " / " + selectedBuilding.getBuildTime() + " Seconds";
                g2d.setColor(Color.orange);
                g2d.setFont(g2d.getFont().deriveFont(18.0f));
                g2d.drawString(toShow, selectedBuilding.getxPos() + 30, selectedBuilding.getyPos() + 30);
            }
            //else
            // buildingsIterator.remove();
        }
        //  System.out.println("building loops finished");

        //handle mouse cursor hovering building places
        handleMouse();

        //Print FPS info
        handleFps(g2d);

        //Draw GAME OVER
        handleGameOver(g2d, state);
    }

    public static void createBuilding(int buildingType, int idNum) {
        System.out.println("In Create Building");
        try {
            switch (buildingType) {
                case Variables.TYPE_MAIN_BUILDING:
                    int tempX = 0, tempY = 0;
                    switch (idNum) {
                        case 0:
                            tempX = 825;
                            tempY = 159;
                            break;
                        case 1:
                            tempX = 932;
                            tempY = 127;
                            break;
                        case 2:
                            tempX = 1015;
                            tempY = 142;
                            break;
                        case 3:
                            tempX = 1117;
                            tempY = 187;
                            break;
                        case 4:
                            tempX = 1163;
                            tempY = 243;
                            break;
                        case 5:
                            tempX = 1130;
                            tempY = 383;
                            break;
                        case 6:
                            tempX = 927;
                            tempY = 468;
                            break;
                        case 7:
                            tempX = 690;
                            tempY = 384;
                            break;
                        case 8:
                            tempX = 728;
                            tempY = 315;
                            break;
                        case 9:
                            tempX = 697;
                            tempY = 254;
                            break;
                        case 10:
                            tempX = 755;
                            tempY = 200;
                            break;
                        case 11:
                            tempX = 943;
                            tempY = 206;
                            break;
                        case 12:
                            tempX = 1038;
                            tempY = 250;
                            break;
                        case 13:
                            tempX = 825;
                            tempY = 159;
                            break;
                    }
                    if (idNum < 13) {
                        MainBuilding temp = new MainBuilding(tempX, tempY, ImageIO.read(new File("src\\Resources\\buildings\\mainBuilding2.png")), idNum);
                        buildings.add(temp);
                        holderMustRemove = true;
                        executorService.execute(new MessageWarn(Variables.TYPE_MAIN_BUILDING, Variables.TYPE_IS_NOW_CONSTRUCTING, JOptionPane.INFORMATION_MESSAGE));
                    } else {
                        executorService.execute(new MessageWarn(Variables.TYPE_MAIN_BUILDING, Variables.TYPE_BUILDING_REQUIRE_ANOTHER, JOptionPane.WARNING_MESSAGE));
                    }
                    break;

                case Variables.TYPE_RALLYPOINT_BUILDING:
                    if (idNum == 13 && hasBuilding(Variables.TYPE_MAIN_BUILDING)) {
                        tempX = 830;
                        tempY = 283;
                        RallyPointBuilding temp = new RallyPointBuilding(tempX, tempY, ImageIO.read(new File("src\\Resources\\buildings\\rallyPoint2.png")), idNum);
                        buildings.add(temp);
                        holderMustRemove = true;
                        executorService.execute(new MessageWarn(Variables.TYPE_RALLYPOINT_BUILDING, Variables.TYPE_IS_NOW_CONSTRUCTING, JOptionPane.INFORMATION_MESSAGE));
                    } else {
                        executorService.execute(new MessageWarn(Variables.TYPE_RALLYPOINT_BUILDING, Variables.TYPE_BUILDING_REQUIRE_ANOTHER, JOptionPane.WARNING_MESSAGE));
                    }
                    break;
                case Variables.TYPE_BARRACKS_BUILDING:

                    switch (idNum) {
                        case 0:
                            tempX = 805;
                            tempY = 139;
                            break;
                        case 1:
                            tempX = 903;
                            tempY = 112;
                            break;
                        case 2:
                            tempX = 990;
                            tempY = 131;
                            break;
                        case 3:
                            tempX = 1102;
                            tempY = 174;
                            break;
                        case 4:
                            tempX = 1140;
                            tempY = 230;
                            break;
                        case 5:
                            tempX = 1125;
                            tempY = 357;
                            break;
                        case 6:
                            tempX = 905;
                            tempY = 444;
                            break;
                        case 7:
                            tempX = 687;
                            tempY = 351;
                            break;
                        case 8:
                            tempX = 702;
                            tempY = 288;
                            break;
                        case 9:
                            tempX = 686;
                            tempY = 239;
                            break;
                        case 10:
                            tempX = 715;
                            tempY = 190;
                            break;
                        case 11:
                            tempX = 912;
                            tempY = 192;
                            break;
                        case 12:
                            tempX = 1011;
                            tempY = 250;
                            break;
                        default:
                            tempX = 0;
                            tempY = 0;
                    }
                    if (hasBuilding(Variables.TYPE_RALLYPOINT_BUILDING)) {
                        BarracksBuilding tempBarracks = new BarracksBuilding(tempX, tempY, ImageIO.read(new File("src\\Resources\\buildings\\barracks2.png")), idNum);
                        buildings.add(tempBarracks);
                        holderMustRemove = true;
                        executorService.execute(new MessageWarn(Variables.TYPE_BARRACKS_BUILDING, Variables.TYPE_IS_NOW_CONSTRUCTING, JOptionPane.INFORMATION_MESSAGE));
                    } else {
                        executorService.execute(new MessageWarn(Variables.TYPE_BARRACKS_BUILDING, Variables.TYPE_BUILDING_REQUIRE_ANOTHER, JOptionPane.WARNING_MESSAGE));
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleFps(Graphics2D g2d) {
        long currentRender = System.currentTimeMillis();
        if (lastRender > 0) {
            fpsHistory.add(1000.0f / (currentRender - lastRender));
            if (fpsHistory.size() > 100) {
                fpsHistory.remove(0); // remove oldest
            }
            float avg = 0.0f;
            for (float fps : fpsHistory) {
                avg += fps;
            }
            avg /= fpsHistory.size();
            String str = String.format("Average FPS = %.1f , Last Interval = %d ms",
                    avg, (currentRender - lastRender));
            g2d.setColor(Color.CYAN);
            g2d.setFont(g2d.getFont().deriveFont(18.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            int strHeight = g2d.getFontMetrics().getHeight();
            g2d.drawString(str, (Variables.WINDOW_WIDTH - strWidth) / 2, strHeight + 50);
        }
        lastRender = currentRender;
    }

    private void handleGameOver(Graphics2D g2d, GameState state) {
        if (state.gameOver) {
            String str = "GAME OVER";
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (Variables.WINDOW_WIDTH - strWidth) / 2, Variables.WINDOW_HEIGHT / 2);
            System.exit(0);
        }
    }
}