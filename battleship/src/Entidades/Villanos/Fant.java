package Entidades.Villanos;

import Entidades.Player;
import Entidades.Strategies.EnemigoLv2;
import main.Bomberman;

import javax.imageio.ImageIO;
import java.awt.*;

public class Fant extends EnemigoLv2 {
    public Fant(Bomberman bomberman, Player player){
        super("Fant",bomberman,true,player);
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
        this.speed =1;
        this.spriteCounter = 0;
        this.spriteNum = 1;
        this.deathTime = 0;
        this.alive = true;
        this.collisionOn = false;
        direccion = "down";
        setVillainValues(8,2000);

    }
    @Override
    public void getActorImage(){
        try{
            up1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantUp/up1.png"));
            up2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantUp/up2.png"));
            up3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantUp/up1.png"));
            up4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantUp/up2.png"));

            down1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantDown/down1.png"));
            down2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantDown/down2.png"));
            down3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantDown/down1.png"));
            down4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantDown/down2.png"));

            right1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantRight/right1.png"));
            right2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantRight/right2.png"));
            right3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantRight/right1.png"));
            right4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantRight/right2.png"));

            left1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantLeft/left1.png"));
            left2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantLeft/left2.png"));
            left3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantLeft/left1.png"));
            left4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/FantLeft/left2.png"));

            death1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/Death/death1.png"));
            death2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/Death/death2.png"));
            death3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/Death/death1.png"));
            death4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/Death/death2.png"));
            death5 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/Death/death1.png"));
            death6 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Fant/Death/death2.png"));

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
}
