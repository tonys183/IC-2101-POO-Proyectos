package Objects;
import Entidades.Actor;
import Entidades.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Bomba extends SuperObject{
    private long time;
    private ArrayList<Explosion> exp;
    private boolean playerOver;
    private int timeBlowBomb = 2000;
    private boolean detonador;
    private long ID;

    public Bomba(){
        exp = new ArrayList<>();
        solidArea = new Rectangle();
        ID=System.currentTimeMillis();
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width=42;
        solidArea.height=42;
        this.detonador= false;
        this.playerOver = false;
        this.name = "Bomba";
        this.collision = true;
        this.time = System.currentTimeMillis();
        try{
            this.image1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/Bomb/bomb1.png"));
            this.image2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/Bomb/bomb2.png"));
            this.image3 = ImageIO.read(getClass().getResourceAsStream("/Sprites/Bomb/bomb3.png"));
            this.image4 = ImageIO.read(getClass().getResourceAsStream("/Sprites/Bomb/bomb4.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void renueTime(long time){
        this.time=time+System.currentTimeMillis()-this.time;
    }
    public long getID(){
        return ID;
    }
    public boolean playerOverlap(Actor actor) {
        Rectangle actorArea = new Rectangle(
                (int) (actor.getX() + actor.getSolidArea().x),
                (int) (actor.getY() + actor.getSolidArea().y),
                actor.getSolidArea().width,
                actor.getSolidArea().height
        );
        Rectangle bombArea = new Rectangle(
                this.x + this.getSolidArea().x,
                this.y + this.getSolidArea().y,
                this.getSolidArea().width,
                this.getSolidArea().height
        );
        if (actorArea.intersects(bombArea)) {
            playerOver = true;
            this.collision = false;
        } else {
            playerOver = false;
            this.collision = true;
        }
        return playerOver;
    }

    public boolean shouldExplode() {
        return (System.currentTimeMillis() - time >= timeBlowBomb);
    }

    public Explosion blow(){
        Explosion explosion= new Explosion(this.x, this.y, 0, 4,ID);
        return explosion;
    }

    public ArrayList getExplosion(){
        return this.exp;
    }
    public void setDetonador(boolean detonador) {
        this.detonador = detonador;
    }

    @Override
    protected BufferedImage getCurrentImage() {
        long timeTrans = System.currentTimeMillis() - time;
        int temp = (int) (timeTrans / 667);
        long timeTemp = timeTrans % 667;
        if (temp < 3) {
            if (timeTemp < 167) {
                return image1;
            } else if (timeTemp < 334) {
                return image2;
            } else if (timeTemp < 501) {
                return image3;
            } else {
                return image4;
            }
        }
        if(detonador) {
            time=System.currentTimeMillis();
            return image4;
        }
        return null;
    }

    public boolean colis(Player actor) {
        if (actor.getBOMBR()) {
            return false;
        }
        return collision;
    }
}
