package Entidades;
import Events.DamageEvent;
import Objects.Cupones.Cupon;
import main.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Actor {
    private BombManager bombManager;
    private KeyHandler keyH;
    private boolean die;
    private int vidas;
    private float dfspeed;
    private boolean HELL;
    private boolean detonator;
    private boolean BOMBR;
    private boolean inv;
    private long invTime=-1;
    private ArrayList<Cupon> cupon = new ArrayList<>();
    private long pickCupon=0;
    private long dieTime=-1;

    public Player(Bomberman bomb, KeyHandler keyHandler, BombManager bombManager){
        super("Heroe");
        this.vidas=3;
        this.die=false;
        this.dfspeed=(float) 3.5;
        this.bomb = bomb;
        this.keyH = keyHandler;
        this.bombManager=bombManager;
        this.HELL=false;
        this.detonator=true;
        this.inv=false;
        this.BOMBR=false;
        solidArea = new Rectangle();
        solidArea.x = 10;
        solidArea.y = 25;
        solidAreaDefX = solidArea.x;
        solidAreaDefY = solidArea.y;
        solidArea.width=25;
        solidArea.height=20;
        setDefaultVals();
        getActorImage();

    }

    @Override
    public void setDefaultVals(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int startX = (screenSize.width - (17*48)) / 2;
        int startY = (screenSize.height - (15*48)) / 2;
        this.x = startX+48;
        this.y = startY;
        this.vidas=3;
        this.speed = dfspeed;
        this.spriteCounter = 0;
        this.spriteNum = 1;
        this.deathTime = 0;
        this.die = false;
        this.alive = true;
        this.collisionOn = false;
        direccion = "down";
        this.detonator=false;
        this.inv=false;
        resetCupon();
    }

    public void resetCupon(){
        for (int i=0; i<cupon.size(); i++) {
            Cupon c = cupon.get(i);
            if(c!=null)  c.deactivateCupon(this);
            cupon.set(i, null);
        }  deleteNullCupon();
    }

    private void deleteNullCupon(){
        ArrayList<Cupon> temp = new ArrayList<>();
        for (Cupon c: cupon) {
            if(c!=null) temp.add(c);
        }
        cupon.clear();
        cupon.addAll(temp);
    }

    public void resetDefault(boolean newWorld){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int startX = (screenSize.width - (17*48)) / 2;
        int startY = (screenSize.height - (15*48)) / 2;
        this.x = startX+48;
        this.y = startY;
        this.speed = dfspeed;
        this.spriteCounter = 0;
        this.spriteNum = 1;
        this.deathTime = 0;
        this.die = false;
        this.alive = true;
        this.collisionOn = false;
        direccion = "down";
        if(newWorld) for (Cupon c: cupon) c.useCupon(this);
    }

    @Override
    public void getActorImage(){
        try{
            up1 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkUp/walkup1.png"));
            up2 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkUp/walkup2.png"));
            up3 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkUp/walkup3.png"));
            up4 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkUp/walkup4.png"));

            down1 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkDown/walkdown1.png"));
            down2 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkDown/walkdown2.png"));
            down3 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkDown/walkdown3.png"));
            down4 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkDown/walkdown4.png"));

            right1 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkRight/walkright1.png"));
            right2 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkRight/walkright2.png"));
            right3 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkRight/walkright3.png"));
            right4 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkRight/walkright4.png"));

            left1 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkLeft/walkleft1.png"));
            left2 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkLeft/walkleft2.png"));
            left3 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkLeft/walkleft3.png"));
            left4 = ImageIO.read(getClass().getResource("/Sprites/Player/WalkLeft/walkleft4.png"));

            death1 = ImageIO.read(getClass().getResource("/Sprites/Player/Death/death1.png"));
            death2 = ImageIO.read(getClass().getResource("/Sprites/Player/Death/death2.png"));
            death3 = ImageIO.read(getClass().getResource("/Sprites/Player/Death/death3.png"));
            death4 = ImageIO.read(getClass().getResource("/Sprites/Player/Death/death4.png"));
            death5 = ImageIO.read(getClass().getResource("/Sprites/Player/Death/death5.png"));
            death6 = ImageIO.read(getClass().getResource("/Sprites/Player/Death/death6.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(){
        if(die) {
            dyingAnimation();
            if(dieTime==-1) dieTime=System.currentTimeMillis();
            if(System.currentTimeMillis()-dieTime>1000){
                bomb.changeGameState(2);
                dieTime=-1;
            }
            return;
        }
        if(!alive){
            long elapsedTime = System.currentTimeMillis() - deathTime;
            if (elapsedTime >= 2000){
                resetDefault(false);
                inv = true;
                invTime=System.currentTimeMillis();
                spriteCounter=5;
            }
            dyingAnimation();
            return;
        }
        if(invTime!=-1 && System.currentTimeMillis()-invTime>8000){
            invTime=-1;
            spriteCounter=0;
            inv = false;
        }

        if (keyH.up) {
            direccion = "up";
        }
        if (keyH.down) {
            direccion = "down";
        }
        if (keyH.left) {
            direccion = "left";
        }
        if (keyH.right) {
            direccion = "right";
        }
        setCollisionOn(false);
        if(!ATM) bomb.getCollisionChecker().checkTile(this);
        else bomb.getCollisionChecker().checkTileForATM(this);

        pickUpObject( bomb.getCollisionChecker().checkObject(this,true));
        int indexVilla = bomb.getCollisionChecker().checkActor(this, bomb.getActors());
        hitVilla(indexVilla);
        for (int i=0; i<cupon.size(); i++) {
            Cupon c = cupon.get(i);
            if(c!=null && c.getTipo().equals("Pregunta") && (System.currentTimeMillis()-pickCupon)>60000 && pickCupon!=0) {
                c.deactivateCupon(this);
                cupon.set(i, null);
            }
         }
        deleteNullCupon();
        if(!getCollisionOn() && (keyH.up || keyH.down || keyH.left || keyH.right)) {
           switch (direccion){
               case "up":  moveFoward();break;
               case "down":  moveBackward();break;
               case "left":  moveLeft(); break;
               case "right":  moveRight(); break;
           }
        }
        if(keyH.up || keyH.down || keyH.left || keyH.right){
            spriteCounter++;
            if(spriteCounter > 10){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 3;
                }
                else if(spriteNum == 3){
                    spriteNum = 4;
                }
                else if(spriteNum == 4){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        bombManager.setDetonador(detonator);
        if (keyH.exp && detonator) {
            bombManager.turnOnDetonar();
        } else bombManager.turnOffDetonar();

    }

    private void hitVilla(int indexVilla){
        if(indexVilla != 999){
            removeLife();
            if(vidas==0) Kill();
            if(!inv) {
                DamageEvent hit = new DamageEvent(this);
                hit.apply();
            }
        }
    }

    private void pickUpObject(int index){
        if(index != 999 && bomb.getObjects()[index] instanceof Cupon){
            Cupon c = (Cupon) bomb.getObjects()[index];
            c.useCupon(this);
            cupon.add(c);
            bomb.getObjects()[index]=null;
            if(c.equals("Pregunta")) pickCupon=System.currentTimeMillis();
        }
    }

    public void removeLife(){
        vidas--;
        bomb.getSound().playX(7);
        for (int i=0; i<cupon.size(); i++) {
            Cupon c = cupon.get(i);
            if(c!=null && !c.isAF()){
                c.deactivateCupon(this);
                cupon.set(i, null);
            }
        }  deleteNullCupon();
    }

    @Override
    public void draw(Graphics2D g) {
        if (!alive) {
            BufferedImage deathImage = null;
            switch (spriteNum) {
                case 1:
                    deathImage = death1;
                    break;
                case 2:
                    deathImage = death2;
                    break;
                case 3:
                    deathImage = death3;
                    break;
                case 4:
                    deathImage = death4;
                    break;
                case 5:
                    deathImage = death5;
                    break;
                case 6:
                    deathImage = death6;
                    break;
                case 7:
                    spriteCounter = 1;
                    break;
            }
            g.drawImage(deathImage, (int)x, (int)y, 46, 46, null);
            return;
        }
        //g.setColor(Color.RED);
        //g.drawRect((int)x + solidArea.x, (int)y + solidArea.y, solidArea.width, solidArea.height);
        BufferedImage image = null;
        switch (direccion){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                else if(spriteNum == 2){
                    image = up2;
                }
                else if(spriteNum == 3){
                    image = up3;
                }
                else if(spriteNum == 4){
                    image = up4;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                else if(spriteNum == 2){
                    image = down2;
                }
                else if(spriteNum == 3){
                    image = down3;
                }
                else if(spriteNum == 4){
                    image = down4;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                else if(spriteNum == 2){
                    image = left2;
                }
                else if(spriteNum == 3){
                    image = left3;
                }
                else if(spriteNum == 4){
                    image = left4;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                else if(spriteNum == 2){
                    image = right2;
                }
                else if(spriteNum == 3){
                    image = right3;
                }
                else if(spriteNum == 4){
                    image = right4;
                }
                break;
        }
        if (inv){
            boolean visible = (System.currentTimeMillis() / 60) % 2 == 0;
            if (visible) {
                g.drawImage(image,(int)x, (int)y, 46, 46, null);
            }
        }
        else g.drawImage(image, (int)x, (int)y, 46, 46, null);
    }

    public void setSpeed(float speed){ this.speed = dfspeed =speed; }

    public void flipATM(boolean t){ this.ATM = t; }

    public void flipHELL(boolean t){ this.HELL=t; }

    public void flipDetonator(boolean t){ this.detonator=t; }

    public boolean isHELL() { return HELL; }

    public boolean getBOMBR() { return BOMBR; }

    public void setBOMBR(boolean BOMBR) { this.BOMBR = BOMBR; }

    public BombManager getBombManager() { return bombManager; }

    public float getDfspeed() { return dfspeed; }

    public int getVidas() { return vidas; }

    public void setLives(int v) { vidas=v; }

    public void Kill() { die=true; }

    public boolean isInmortal() { return inv;}

    public void setInv(boolean  inv) { this.inv=inv; }

    public void addLives() { vidas++; }
}