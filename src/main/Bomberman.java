package main;
import Entidades.Strategies.*;
import Entidades.Villanos.*;
import Events.EventManager;
import Objects.Cupones.Cupon;
import Objects.Cupones.CuponFactory;
import Tiles.*;
import Entidades.*;
import Objects.*;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Bomberman extends JPanel implements Runnable, Serializable {
    private int levelActu;
    private int nextLifeScore = 100000;
    private int sizeX = 15;
    private int sizeY = 17;
    private int FPS = 60;
    private int cellSize = 48;
    private long totalScore = 0;
    private boolean nextLevel = false;
    private static Player player = null;
    private static Bomberman instancia = null;
    private static EnemigoRND enemigoRND = null;
    private static VillanoFactory villanoFactory;
    private TileManager tileM = new TileManager(this);
    private static ArrayList<Actor> actors = new ArrayList<>();
    private AssetSetter assetSetter = new AssetSetter(this);
    private KeyHandler keyH = new KeyHandler();
    private CollisionChecker cChecker = new CollisionChecker(this);
    private SuperObject obj[] = new SuperObject[30];
    private BombManager bombManag = new BombManager(this, keyH);
    private VillainManager villainManager = new VillainManager(this);
    private EventManager eventManager;
    private UI ui = new UI(this);
    private Thread thread = null;
    private long lastMake = System.currentTimeMillis();
    private CuponFactory cuponFactory;
    private int gamestate;
    private Sound sound = new Sound();
    private long resetSoundLevel;
    private boolean gmo = false;
    private boolean levelExtra = false;

    private Bomberman() {
        this.levelActu = 1;
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(keyH);
        keyH.setBomberman(this);
        gamestate=0;
        player = new Player(this, keyH, bombManag);
        this.eventManager = new EventManager(player, this);
        tileM.assignHiddenItems();
        makeVillains(levelActu);
        startGameThread();
        sound.playX(1);
        sound.loopX(1);
    }

    private void makeVillains(int levelActu) {
        int min, max;
        if (levelActu <= 5) {
            min = 2; max = 4;
        } else if (levelActu <= 10) {
            min = 5; max = 8;
        } else {
            min = 8; max = 10;
        }
        Random random = new Random();
        int cantidad = random.nextInt((max - min) + 1) + min;
        villanoFactory.crearVillanosParaNivel(actors, cantidad, player, this, levelActu, eventManager);
    }

    public void startGameThread(){
        this.setFocusable(true);
        thread = new Thread(this);
        thread.start();
    }

    private void ClearObjects(){
        for (int i = 0; i < 10; i++) {
            obj[i] = null;
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double nextDraw = System.nanoTime() + drawInterval;
        while(thread != null){
            update();
            repaint();
            double remain = nextDraw - System.nanoTime();
            remain =  remain/1000000;
            if(remain < 0) remain = 0;
            Console.sleepL((long) remain);
            nextDraw += drawInterval;
        }
    }

    public static Bomberman getInstancia() {
        if (instancia == null) return new Bomberman();
        return instancia;
    }

    public void update() {
        if(gamestate==0){
            if (levelActu%5 == 0 && levelActu != 0 ){
                levelExtra();
            }
            if (totalScore >= nextLifeScore) {
                player.addLives();
                nextLifeScore += 100000;
            }
            eventManager.updateText();
            player.update();
            for (Actor actor : actors) actor.update();
            nextLevel = eventManager.checkStatusEnemies();
            if(!eventManager.isFirstPlay() && (System.currentTimeMillis() - resetSoundLevel) > 3500) {
                sound.resume(1);
                sound.resume(4);
            }
            bombManag.update(player);
        } else if (gamestate == 2 && !gmo) {
            actors.clear();
            levelActu=1;
            gmo=true;
            sound.stopX(1);
            sound.stopX(8);
            sound.stopX(13);
            sound.stopX(14);
            sound.stopX(12);
            sound.stopX(9);
            sound.playX(3);
            sound.loopX(3);
        }
    }

    public void levelExtra(){
        player.setInv(true);
        if(ui.getPlayTime()<=0) startNextLevel();
        if(System.currentTimeMillis()-lastMake>=5000){
            makeVillains(levelActu);
            lastMake = System.currentTimeMillis();
        }
    }

    public void startNewWorld(){
        player.setDefaultVals();
        totalScore=0;
        resetLevel();
        loadNewLevel();
    }

    public void startNextLevel(){
        this.levelActu++;
        resetLevel();
        loadNewLevel();
    }

    public void resetLevel(){
        ui.setPlayTime(200);
        player.setInv(false);
        nextLevel = false;
        actors.clear();
        ClearObjects();
        bombManag.setDefaultValues(this);
        player.resetDefault(true);
        eventManager.reset();
        if(levelActu%5==0 && levelActu != 0){
            lastMake = System.currentTimeMillis();
            ui.setPlayTime(30);
            this.levelExtra = true;
        } else{
            this.levelExtra = false;
        }
    }

    public void loadNewLevel(){
        tileM.reset();
        tileM.assignHiddenItems();
        villainManager.reset();
        makeVillains(levelActu);
        gamestate=0;
        gmo=false;
        if(levelExtra){
            sound.stopX(1);
            sound.stopX(2);
            sound.playX(2);
            sound.loopX(2);
        } else{
            sound.stopX(4);
            sound.stopX(3);
            sound.stopX(2);
            sound.stopX(1);
            sound.playX(1);
            sound.loopX(1);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.requestFocusInWindow();
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.decode("#474747"));
        g2.fillRect(0, 0, panelWidth, panelHeight);
        tileM.draw(g2);
        bombManag.draw(g2);
        player.draw(g2);
        ui.draw(g2);
        eventManager.render(g2);
        for (Actor actor : actors) actor.draw(g2);
        g2.dispose();
    }

    public void muerteSubita(){
        if(levelActu%5==0) return;
        sound.stopX(1);
        sound.playX(4);
        sound.loopX(4);
        makeVillains(levelActu);
        ArrayList<Villanos> villanosArrayList=new ArrayList<>();
        for (Actor actor : actors){
            villanosArrayList.add((Villanos) actor);
        }
        eventManager.flipPlayMusic();
        actors.clear();
        eventManager.reset();
        for (int i=0; i<villanosArrayList.size(); i++){
            Actor actor = villanosArrayList.get(i).flipToMonge();
            actors.add(actor);
            eventManager.addActor(actor);
        }
        eventManager.flipPlayMusic();
    }

    public void penalty(){
        sound.playX(14);
        if(ui.getPlayTime()>=213){
            makeVillains(levelActu+1);
            ArrayList<Villanos> villanosArrayList=new ArrayList<>();
            for (Actor actor : actors){
                villanosArrayList.add((Villanos) actor);
            }
            eventManager.flipPlayMusic();
            actors.clear();
            eventManager.reset();
            for (int i=0; i<villanosArrayList.size(); i++){
                Actor actor = villanosArrayList.get(i).flipToMonge();
                actors.add(actor);
                eventManager.addActor(actor);
            }
            eventManager.flipPlayMusic();
        }
        else makeVillains(levelActu+1);
    }

    public void addPuntaje(int add){ totalScore+=add; }

    public Dupla<Cupon,Integer> getCupon() { return cuponFactory.createCupon(levelActu); }

    public UI getUi(){ return ui; }

    public Sound getSound(){ return sound; }

    public int getCellSize() { return cellSize; }

    public int getSizeX(){ return sizeX; }

    public int getSizeY(){ return sizeY; }

    public CollisionChecker getCollisionChecker(){ return cChecker; }

    public TileManager getTileM() { return tileM; }

    public SuperObject[] getObjects() { return obj; }

    public AssetSetter getAssetSetter() { return assetSetter; }

    public EventManager getEventManager() { return eventManager; }

    public VillainManager getVillainManager() { return villainManager; }

    public BombManager getBombManag() { return  bombManag; }

    public ArrayList getActors() { return actors; }

    public Player getPlayer() { return player; }

    public boolean isNextLevel() { return nextLevel; }

    public long getTotalScore() { return totalScore; }

    public int getLevelActu() { return levelActu; }

    public void setScore(long score) { this.totalScore = score; }

    public int getGamestate(){
        return gamestate;
    }

    public void changeGameState(int n){
        gamestate= n;
    }

    public void setResetSoundLevel(long n) { this.resetSoundLevel = n; }
}