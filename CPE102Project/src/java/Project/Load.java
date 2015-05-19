package src.java.Project;

import processing.core.PApplet;
import processing.core.PImage;
import src.java.Project.entities.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by marinamoore on 5/11/15.
 */
public class Load {

    private static Load instance = null;
    protected Load(){}

    private Controller controller;

    private static final int propertyKey = 0;

    private static final String bgndKey = "background";
    private static final int bgndNumProperties = 4;
    private static final int bgndName = 1;
    private static final int bgndCol = 2;
    private static final int bgndRow = 3;

    private static final String minerKey = "miner";
    private static final int minerNumProperties = 7;
    private static final int minerName = 1;
    private static final int minerLimit = 4;
    private static final int minerCol = 2;
    private static final int minerRow = 3;
    private static final int minerRate = 5;
    private static final int minerAnimationRate = 6;

    private static final String obstacleKey = "obstacle";
    private static final int obstacleNumProperties = 4;
    private static final int obstacleName = 1;
    private static final int obstacleCol = 2;
    private static final int obstacleRow = 3;

    private static final String oreKey = "ore";
    private static final int oreNumProperties = 4;
    private static final int oreName = 1;
    private static final int oreCol = 2;
    private static final int oreRow = 3;
    private static final int oreRate = 4;

    private static final String smithKey = "blacksmith";
    private static final int smithNumProperties = 7;
    private static final int smithName = 1;
    private static final int smithCol = 2;
    private static final int smithRow = 3;
    private static final int smithLimit = 4;
    private static final int smithRate = 5;
    //private static final int smithReach = 6;

    private static final String veinKey = "vein";
    private static final int veinNumProperties = 6;
    private static final int veinName = 1;
    private static final int veinRate = 4;
    private static final int veinCol = 2;
    private static final int veinRow = 3;
    //private static final int veinReach = 5;

    private static final String defaultImageName = "background_default";

    //private List<List<PImage>> iStore;
    public static Map<String,List<PImage>> map = new HashMap<String, List<PImage>>();


    private WorldModel world;
    private WorldView view = WorldView.getInstance();

    public static Load getInstance(){
        if(instance == null){
            instance =  new Load();
        }
        return instance;
    }

    public void init(Controller controller){
        this.controller = controller;
        world = WorldModel.getInstance();
    }

    public static void loadWorld(String args, boolean run, WorldModel world, long ticks){
        try{
            Scanner data = new Scanner(new FileInputStream(args));
            while (data.hasNextLine()){
                String [] properties = data.nextLine().split("\\s");
                if (!(properties==null)){
                    if(properties[propertyKey].equals(bgndKey)){
                        addBackground(properties, world);
                    }
                    else{
                        addEntity(properties,run, world, ticks);
                    }
                }
            }
        }
        catch (FileNotFoundException e){
            System.err.println(e.getMessage());
        }
    }

    public static void addBackground(String[] properties, WorldModel world){
        if (properties.length >= bgndNumProperties){
            Point pt = new Point(Integer.parseInt(properties[bgndCol]), Integer.parseInt(properties[bgndRow]));
            String name = properties[bgndName];
            world.setBackground(pt, new Background(name, getImages(name)));
        }
    }

    public static void addEntity(String[] properties, boolean run, WorldModel world, long ticks){
        InteractiveEntity newEntity = createFromProperties(properties);
        if (newEntity != null){
            world.addEntity(newEntity);
            if (run){
                scheduleEntity(newEntity, ticks, world);
            }
        }
    }

    public static InteractiveEntity createFromProperties(String[] properties){
        if (properties != null){
            String key = properties[propertyKey];
            switch (key){
                case minerKey:
                    return createMiner(properties);
                case veinKey:
                    return  createVein(properties);
                case oreKey:
                    return  createOre(properties);
                case smithKey:
                    return createBlacksmith(properties);
                case obstacleKey:
                    return createObstacle(properties);
            }
        }
        return null;
    }

    public static Miner createMiner(String[] properties){
        if(properties.length == minerNumProperties){
            Miner miner = new MinerNotFull(properties[minerName],
                    getImages(properties[propertyKey]),
                    Integer.parseInt(properties[minerLimit]),
                    new Point(Integer.parseInt(properties[minerCol]), Integer.parseInt(properties[minerRow])),
                    Integer.parseInt(properties[minerRate]),
                    Integer.parseInt(properties[minerAnimationRate])
                    );
            //System.out.println(properties[minerName]);
            //System.out.println(Integer.parseInt(properties[minerLimit]));
            //System.out.println(Integer.parseInt(properties[minerCol]));
            //System.out.println(Integer.parseInt(properties[minerRow]));
            return miner;
        }
        else{
            return null;
        }
    }

    public static Vein createVein(String[] properties){
        if(properties.length == veinNumProperties){
            Vein vein = new Vein(properties[veinName],
                    getImages(properties[propertyKey]),
                    Integer.parseInt(properties[veinRate]),
                    new Point(Integer.parseInt(properties[veinCol]), Integer.parseInt(properties[veinRow]))
            );
            return vein;
        }
        else{
            return null;
        }
    }

    public static Ore createOre(String[] properties){
        if(properties.length == oreNumProperties){
            Ore ore = new Ore(properties[oreName],
                    getImages(properties[propertyKey]),
                    new Point(Integer.parseInt(properties[oreCol]), Integer.parseInt(properties[oreRow])),
                    Integer.parseInt(properties[oreRate])
            );
            return ore;
        }
        else{
            return null;
        }
    }

    public static Blacksmith createBlacksmith(String[] properties){
        if(properties.length == smithNumProperties){
            Blacksmith blacksmith = new Blacksmith(properties[smithName],
                    getImages(properties[propertyKey]),
                    new Point(Integer.parseInt(properties[smithCol]), Integer.parseInt(properties[smithRow])),
                    Integer.parseInt(properties[smithLimit]),
                    Integer.parseInt(properties[smithRate])
            );
            return blacksmith;
        }
        else{
            return null;
        }
    }

    public static Obstacle createObstacle(String[] properties){
        if(properties.length == obstacleNumProperties){
            Obstacle obstacle = new Obstacle(properties[obstacleName],
                    getImages(properties[propertyKey]),
                    new Point(Integer.parseInt(properties[obstacleCol]), Integer.parseInt(properties[obstacleRow]))
            );
            return obstacle;
        }
        else{
            return null;
        }
    }

    public static void scheduleEntity(InteractiveEntity entity, long ticks, WorldModel world){
        if(entity instanceof MinerNotFull){
            MinerNotFull newEntity = (MinerNotFull) entity;
            newEntity.scheduleMiner(world,ticks);
        }
        else if(entity instanceof Vein){
            Vein newEntity = (Vein) entity;
            newEntity.scheduleVein(world,ticks);
        }
        else if(entity instanceof Ore){
            Ore newEntity = (Ore) entity;
            newEntity.scheduleOre(world, ticks);
        }
    }


    public static List<PImage> getImages(String key){
        return map.get(key);
    }

    public void loadImages(String file) {
        try {
            Scanner in = new Scanner(new FileInputStream(file));
            while (in.hasNextLine()) {
                processImageLine(in.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void processImageLine(String line){
        String [] attrs = line.split("\\s");
        if(attrs.length >=2){
            String key = attrs[0];
            PImage img = controller.loadImage(attrs[1]);
            if(key.equals("miner") || key.equals("ore")){
                img = controller.setAlpha(img, controller.color(252, 252, 252), 0);
            }

            if(img != null){
                List<PImage> imgs = getImagesInternal(key);
                imgs.add(img);
                map.put(key,imgs);
            }
        }
    }

    public List<PImage> getImagesInternal(String key){
        List<PImage> temp = new ArrayList<>();
        for(Map.Entry<String, List<PImage>> entry : map.entrySet()){
            if(entry.getKey().equals(key)){
                temp = entry.getValue();
            }
        }
        return temp;
    }
}
