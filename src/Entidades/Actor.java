package Entidades;

import main.Bomberman;
import Entidades.Strategies.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Actor implements Serializable {
    protected String tipo;
    protected double x, y;
    protected double speed = 3;
    protected Rectangle solidArea;
    protected int solidAreaDefX, solidAreaDefY;
    protected boolean collisionOn = false;
    protected String direccion = "";
    protected BufferedImage up1,up2,up3,up4,down1,down2,down3,down4,left1,left2,left3,left4,right1,right2,right3,right4;
    protected BufferedImage death1, death2, death3, death4, death5, death6;
    protected BufferedImage deathExp1, deathExp2, deathExp3, deathExp4, deathExp5, deathExp6, deathExp7, deathExp8, deathExp9;
    protected int spriteCounter;
    protected int spriteNum;
    protected boolean alive;;
    protected long deathTime;
    protected Bomberman bomb;
    protected boolean isExploding = false;
    protected long explosionStartTime;
    protected int explosionCounter = 0;
    protected final int explosionDelay = 3;
    protected boolean ATM;

    public Actor(){}

    public Actor(String tipo) {
        this.tipo = tipo;
        this.x = -1;
        this.y = -1;
        this.alive = true;
        this.ATM = false;
    }

    public Bomberman getBomb() {
        return bomb;
    }

    public void copyActor(Actor actor) {
        this.x = actor.x;
        this.y = actor.y;
        this.speed = actor.speed;
        this.solidAreaDefX = actor.solidAreaDefX;
        this.solidAreaDefY = actor.solidAreaDefY;
        this.collisionOn = actor.collisionOn;
        this.direccion = actor.direccion;
        this.solidArea = actor.solidArea;
        this.bomb=actor.bomb;
    }
    public void setCollisionOn(Boolean collisionOn) { this.collisionOn = collisionOn; }

    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void setAlive(boolean alive) { this.alive = alive; }

    public String getTipo() {
        return tipo;
    }

    public void setDeathTime(long deathTime) { this.deathTime = deathTime; }

    public boolean getCollisionOn() {
        return collisionOn;
    }

    public double getX() { return x; }

    public double getY() { return y; }

    public double getSpeed() { return speed; }

    public int getSolidAreaDefX() { return solidAreaDefX; }

    public int getSolidAreaDefY() { return solidAreaDefY; }

    public boolean getAlive() { return alive; }

    public Rectangle getSolidArea() { return solidArea; }

    public String getDireccion() { return direccion; }

    public void moveFoward(){
        this.y -= speed;
    }

    public void moveBackward(){
        this.y += speed;
    }

    public void moveRight(){
        this.x += speed;
    }

    public void moveLeft(){
        this.x -= speed;
    }

    public void dyingAnimation() {
        spriteCounter++;
        if (spriteCounter >= 10) {
            spriteCounter = 0;
            spriteNum++;
            if (spriteNum > 6) {
                spriteNum = 6;
            }
        }
    }

    public void explosionAnimation() {
        spriteCounter++;
        if (spriteCounter >= 6) {
            spriteCounter = 0;
            spriteNum++;
            if (spriteNum > 6) {
                spriteNum = 6;
            }
        }
    }

    public void setDefaultVals(){}
    public void getActorImage(){}
    public void update(){}
    public void draw(Graphics2D g) {}
}