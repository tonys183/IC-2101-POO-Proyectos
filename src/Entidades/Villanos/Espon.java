package Entidades.Villanos;
import main.Bomberman;

import javax.imageio.ImageIO;
import java.awt.*;
import Entidades.Strategies.*;
import Entidades.*;
public class Espon extends EnemigoLv1{

    public Espon(Bomberman bomberman, Player player){
        super("Espon",bomberman,true,player);
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
        this.speed = 1;
        this.spriteCounter = 0;
        this.spriteNum = 1;
        this.deathTime = 0;
        this.alive = true;
        this.collisionOn = false;
        direccion = "down";
        setVillainValues(6,1000);
    }
    @Override
    public void getActorImage(){
        try{
            up1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponUp/up1.png"));
            up2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponUp/up2.png"));
            up3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponUp/up3.png"));
            up4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponUp/up4.png"));

            down1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponDown/down1.png"));
            down2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponDown/down2.png"));
            down3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponDown/down3.png"));
            down4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponDown/down4.png"));

            right1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponRight/right1.png"));
            right2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponRight/right2.png"));
            right3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponRight/right3.png"));
            right4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponRight/right4.png"));

            left1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponLeft/left1.png"));
            left2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponLeft/left2.png"));
            left3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponLeft/left3.png"));
            left4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/EsponLeft/left4.png"));

            death1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/Death/death1.png"));
            death2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/Death/death2.png"));
            death3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/Death/death3.png"));
            death4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/Death/death4.png"));
            death5 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/Death/death5.png"));
            death6 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Espon/Death/death6.png"));

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
