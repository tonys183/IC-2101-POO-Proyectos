package Entidades.Villanos;

import Entidades.*;
import Entidades.Strategies.EnemigoLv3;
import main.Bomberman;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Mon extends EnemigoLv3 {
    private BufferedImage[] sprites;
    private int frame;
    private long lastUpdateTime;

    public Mon(Bomberman bomberman, Player player){
        super("Mon",bomberman,false,player);
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
        this.speed = 1.5;
        this.spriteCounter = 0;
        this.spriteNum = 1;
        this.deathTime = 0;
        this.alive = true;
        this.collisionOn = false;
        direccion = "down";
        setVillainValues(11,3000);

    }
    @Override
    public void getActorImage(){
        try{
            sprites = new BufferedImage[18];
            for (int i = 0; i < 18; i++) {
                sprites[i] = ImageIO.read(getClass().getResource("/Sprites/Villanos/Mon/Walk/mon"+(i+1)+".png"));
            }

            death1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Mon/Death/death1.png"));
            death2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Mon/Death/death2.png"));
            death3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Mon/Death/death1.png"));
            death4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Mon/Death/death2.png"));
            death5 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Mon/Death/death1.png"));
            death6 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Mon/Death/death2.png"));

            deathExp1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/DeathExplosion/deathExp1.png"));
            deathExp2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/DeathExplosion/deathExp2.png"));
            deathExp3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/DeathExplosion/deathExp3.png"));
            deathExp4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/DeathExplosion/deathExp4.png"));
            deathExp5 = ImageIO.read(getClass().getResource("/Sprites/Villanos/DeathExplosion/deathExp5.png"));
            deathExp6 = ImageIO.read(getClass().getResource("/Sprites/Villanos/DeathExplosion/deathExp6.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (!alive) {
            super.draw(g);
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime > 100) {
            frame = (frame + 1) % sprites.length;
            lastUpdateTime = currentTime;
        }

        BufferedImage currentSprite = sprites[frame];
        g.drawImage(currentSprite, (int) x, (int) y, 42, 42, null);
    }
}
