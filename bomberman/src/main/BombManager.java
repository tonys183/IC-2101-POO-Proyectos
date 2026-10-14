package main;

import Entidades.Actor;
import Entidades.Player;
import Entidades.Villanos.Villanos;
import Events.EventManager;
import Objects.Bomba;
import Objects.Explosion;
import Objects.SuperObject;
import Tiles.DestructionAnimation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BombManager {
    private Bomberman bomberman;
    private boolean bombPreviouslyPressed = false;
    private int cantBombas = 1;
    private int tileSize = 48;
    private KeyHandler keyH = null;
    private ArrayList<DestructionAnimation> destructionAnimations = new ArrayList<>();
    private int rango;
    private boolean detonado;
    private boolean detonador;
    private long doorPenalty=-1;

    public BombManager(Bomberman bomberman, KeyHandler keyH) {
        this.bomberman = bomberman;
        this.keyH = keyH;
        this.rango=1;
        this.detonado= this.detonador = false;
    }

    public int getRango(){ return rango; }
    public void setRango(int rango) {
        this.rango = rango;
    }
    public int getCantBombas(){ return cantBombas; }
    public void turnOnDetonar(){ detonado = true; }
    public void turnOffDetonar(){ detonado = false; }

    public void setDefaultValues(Bomberman bomberman){
        this.bomberman = bomberman;
        bombPreviouslyPressed = false;
        this.rango = 1;
        this.detonado = this.detonador = false;
        cantBombas = 1;
    }
    public void update(Player player) {
        if (keyH.bomb && !bombPreviouslyPressed && cantBombas > 0 && player.getAlive()) {
            int centeredX = Math.round((int) player.getX() / 48) * 48 + 48 / 2;
            int centeredY = Math.round((int) player.getY() / 48) * 48 + 48 / 2;

            int posBombaX = centeredX + 3; int posBombaY = centeredY + 27;
            if (valido(posBombaX, posBombaY)) {
                bomberman.getAssetSetter().setBomba(posBombaX, posBombaY);
                this.cantBombas--;
            }
        }
        bombPreviouslyPressed = keyH.bomb;
    }


    public void setDetonador(boolean detonado) {
        this.detonador = detonado;
    }

    private boolean valido(int x, int y) {
        int tableroWidth = bomberman.getSizeY() * tileSize;
        int tableroHeight = bomberman.getSizeX() * tileSize;
        int startX = (bomberman.getWidth() - tableroWidth) / 2;
        int startY = (bomberman.getHeight() - tableroHeight) / 2;

        int sx=((x-startX)/48);
        int sy=((y-startY)/48);
        if(!(sx<17 && sy<15 && sy>0 && sx>0)) return false;
        return bomberman.getTileM().getMapTileNum()[sx][sy]==0;
    }

    private void penalty(int x, int y) {
        int tableroWidth = bomberman.getSizeY() * tileSize;
        int tableroHeight = bomberman.getSizeX() * tileSize;
        int startX = (bomberman.getWidth() - tableroWidth) / 2;
        int startY = (bomberman.getHeight() - tableroHeight) / 2;
        int sx=((x-startX)/48);
        int sy=((y-startY)/48);
        if(!(sx<17 && sy<15 && sy>0 && sx>0)) return;
        if((bomberman.getTileM().getMapTileNum()[sx][sy]>=4 && bomberman.getTileM().getMapTileNum()[sx][sy]<=11)){
            bomberman.getTileM().getMapTileNum()[sx][sy]=0;
            bomberman.penalty();
            doorPenalty=System.currentTimeMillis();
        }
        else if(bomberman.getTileM().getMapTileNum()[sx][sy]==3  && (doorPenalty==-1)){
            bomberman.penalty();
            doorPenalty=System.currentTimeMillis();
        }
        if(System.currentTimeMillis()-doorPenalty>2000 && doorPenalty!=-1) doorPenalty=-1;

    }

    private void quitarMuro(int x, int y) {
        int tableroWidth = bomberman.getSizeY() * tileSize;
        int tableroHeight = bomberman.getSizeX() * tileSize;
        int startX = (bomberman.getWidth() - tableroWidth) / 2;
        int startY = (bomberman.getHeight() - tableroHeight) / 2;
        int sx=((x-startX)/48);
        int sy=((y-startY)/48);
        if(!(sx<17 && sy<15 && sy>0 && sx>0)) return;
        if(bomberman.getTileM().getMapTileNum()[sx][sy]==2){
            Image[] destructionFrames = loadDestructionFrames();
            DestructionAnimation animation = new DestructionAnimation(x, y, destructionFrames);
            destructionAnimations.add(animation);
        }
    }

    private Image[] loadDestructionFrames() {
        try {
            return new Image[] {
                    ImageIO.read(getClass().getResource("/Sprites/BrickDestroy/brickdes1.png")),
                    ImageIO.read(getClass().getResource("/Sprites/BrickDestroy/brickdes2.png")),
                    ImageIO.read(getClass().getResource("/Sprites/BrickDestroy/brickdes3.png")),
                    ImageIO.read(getClass().getResource("/Sprites/BrickDestroy/brickdes4.png")),
                    ImageIO.read(getClass().getResource("/Sprites/BrickDestroy/brickdes5.png")),
                    ImageIO.read(getClass().getResource("/Sprites/BrickDestroy/brickdes6.png")),
                    ImageIO.read(getClass().getResource("/Sprites/BrickDestroy/brickdes7.png"))
            };
        } catch (IOException e) {
            e.printStackTrace();
            return new Image[0];
        }
    }

    private void updateAndDrawAnimations(Graphics2D g2) {
        for (int i = 0; i < destructionAnimations.size(); i++) {
            DestructionAnimation animation = destructionAnimations.get(i);
            int x = animation.getX();
            int y = animation.getY();
            int tableroWidth = bomberman.getSizeY() * tileSize;
            int tableroHeight = bomberman.getSizeX() * tileSize;
            int startX = (bomberman.getWidth() - tableroWidth) / 2;
            int startY = (bomberman.getHeight() - tableroHeight) / 2;
            int sx = ((x - startX) / 48);
            int sy = ((y - startY) / 48);
            bomberman.getTileM().getMapTileNum()[sx][sy] = 0;
            animation.update();
            animation.draw(g2);
            if (animation.isFinished()) {
                if(bomberman.getTileM().getHiddenItems()[sx][sy] != 0){
                    bomberman.getTileM().getMapTileNum()[sx][sy] = bomberman.getTileM().getHiddenItems()[sx][sy];
                }
                destructionAnimations.remove(i);
                i--;
            }
        }
    }

    public void draw(Graphics2D g2) {
        SuperObject objs[] = bomberman.getObjects();
        EventManager eventManager = bomberman.getEventManager();
        for (int i = 0; i < objs.length; i++) {
            if (objs[i] != null && objs[i] instanceof Bomba) {
                drawBomb(g2, objs, eventManager, i);
            }
            else if (objs[i] != null){
                objs[i].draw(g2, 42, 42);
            }
        }
        updateAndDrawAnimations(g2);
    }

    public void drawBomb(Graphics2D g2, SuperObject[] objs, EventManager eventManager, int i) {
        objs[i].draw(g2, 42, 42);
        Bomba bomba = (Bomba) objs[i];
        if(bomberman.getGamestate()==1) bomba.renueTime(System.currentTimeMillis());
        bomba.setDetonador(detonador);
        boolean detonar = detonador ? detonado: bomba.shouldExplode();
        if (detonar) {
            if (bomba.getExplosion().isEmpty()) {
                bomberman.getSound().stopX(8);
                bomberman.getSound().playX(8);
                Explosion exp = bomba.blow();
                long ID= bomba.getID();
                bomba.getExplosion().add(exp);
                int x = bomba.getX(), y = bomba.getY();
                boolean expandUp = true, expandDown = true, expandLeft = true, expandRight = true;
                for (int j = 1; j <= rango; j++) {
                    int imagen = (j == rango) ? 2 : 1;
                    if (expandDown && valido(x, y + 48 * j)) bomba.getExplosion().add(new Explosion(x, y + 48 * j, imagen, 1,ID));
                    else{
                        if(expandDown){
                            quitarMuro(x, y + 48 * j);
                            penalty(x, y + 48 * j);
                        }
                        expandDown = false;
                    }
                    if (expandLeft && valido(x - 48 * j, y)) bomba.getExplosion().add(new Explosion(x - 48 * j, y, imagen, 0,ID));
                    else{
                        if(expandLeft){
                            quitarMuro(x - 48 * j, y);
                            penalty(x - 48 * j, y);
                        }
                        expandLeft = false;
                    }
                    if (expandUp && valido(x, y - 48 * j)) bomba.getExplosion().add(new Explosion(x, y - 48 * j, imagen, 3,ID));
                    else{
                        if(expandUp){
                            quitarMuro(x, y - 48 * j);
                            penalty(x, y - 48 * j);
                        }
                        expandUp = false;
                    }
                    if (expandRight && valido(x + 48 * j, y)) bomba.getExplosion().add(new Explosion(x + 48 * j, y, imagen, 2,ID    ));
                    else {
                        if(expandRight ){
                            quitarMuro(x + 48 * j, y);
                            penalty(x + 48* j, y );
                        }
                        expandRight = false;
                    }
                }
            }
        }
        if(bomberman.getGamestate()==0) {
            for (int j = 0; j < bomba.getExplosion().size(); j++) {
                Explosion explosion = (Explosion) bomba.getExplosion().get(j);
                explosion.draw(g2, 48, 48);
                eventManager.checkExplosionCollisions(explosion);
                eventManager.update();
                if (explosion.isFinished()) {
                    if (objs[i] != null) {
                        objs[i] = null;
                        this.cantBombas++;
                        exploteBomb();
                    };
                }
            }
            eventManager.checkForSameID();
        }
    }

    private void exploteBomb(){
        ArrayList<Actor> actors = bomberman.getActors();
        for (Actor villanos: actors) ((Villanos) villanos).setBomba(null);
    }

    public void setCantBombas(int cantBombas) {
        this.cantBombas = cantBombas;
    }
}