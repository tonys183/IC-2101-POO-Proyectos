package Entidades.Villanos;
import main.Bomberman;

import javax.imageio.ImageIO;
import java.awt.*;
import Entidades.Strategies.*;
public class Globo extends EnemigoRND {
    public Globo(Bomberman bomberman) {
        super("Globo", bomberman,false,null);
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
        this.speed = 0.67;
        this.spriteCounter = 0;
        this.spriteNum = 1;
        this.deathTime = 0;
        this.alive = true;
        this.collisionOn = false;
        direccion = "down";
        setVillainValues(1,100);

    }

    @Override
    public void getActorImage(){
        try{
            up1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonUp/up1.png"));
            up2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonUp/up2.png"));
            up3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonUp/up3.png"));
            up4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonUp/up4.png"));

            down1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonDown/down1.png"));
            down2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonDown/down2.png"));
            down3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonDown/down3.png"));
            down4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonDown/down4.png"));

            right1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonRight/right1.png"));
            right2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonRight/right2.png"));
            right3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonRight/right3.png"));
            right4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonRight/right4.png"));

            left1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonLeft/left1.png"));
            left2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonLeft/left2.png"));
            left3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonLeft/left3.png"));
            left4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/BallonLeft/left4.png"));

            death1 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/Death/death1.png"));
            death2 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/Death/death2.png"));
            death3 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/Death/death1.png"));
            death4 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/Death/death2.png"));
            death5 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/Death/death1.png"));
            death6 = ImageIO.read(getClass().getResource("/Sprites/Villanos/Ballon/Death/death2.png"));

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
