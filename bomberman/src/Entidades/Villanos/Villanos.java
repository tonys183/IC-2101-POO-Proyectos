package Entidades.Villanos;
import Entidades.Actor;
import Objects.Bomba;
import main.Bomberman;
import main.Dupla;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class Villanos extends Actor {
    protected int puntaje;
    protected int minLevel;
    private boolean moreThan0ne;
    private Bomba bomba;
    public Villanos(){}

    public Villanos(String tipo, Bomberman bomb, Boolean ATM){
        super(tipo);
        this.bomb = bomb;
        this.ATM = ATM;
        this.moreThan0ne = false;
        this.bomba=null;
        setPosicion();
    }
    public MonG flipToMonge(){
        MonG monG = new MonG(bomb.getPlayer());
        monG.setX(this.getX());
        monG.setY(this.getY());
        monG.ATM=true;
        monG.bomb=this.bomb;
        monG.tipo="MonG";
        return monG;
    }
    public boolean getATM(){
        return ATM;
    }

    public void setBomba(Bomba bomba) {
        this.bomba = bomba;
    }

    public Bomba getBomba() {
        return bomba;
    }

    protected void setPosicion(){
        ArrayList<Dupla> posicionesValidas = new ArrayList<>();
        for (Dupla dupla : bomb.getVillainManager().getVillains()){
            posicionesValidas.add(dupla);
        }
        Collections.shuffle(posicionesValidas);
        Dupla dupla = posicionesValidas.get(0);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int cellSize = 48;
        int matrizCols = 17;
        int matrizRows = 15;
        int matrizWidth = matrizCols * cellSize;
        int matrizHeight = matrizRows * cellSize;

        int startX = (screenSize.width - matrizWidth) / 2;
        int startY = ((screenSize.height - matrizHeight) / 2)-48;
        bomb.getVillainManager().addPosicion(dupla);
        this.x= (48 * (int)dupla.getPrimero())+startX;
        this.y = (48 * (int)dupla.getSegundo())+startY;
    }

    protected void setVillainValues(int minLevel, int puntaje){
        this.minLevel=minLevel;
        this.puntaje=puntaje;
    }

    @Override
    public void draw(Graphics2D g) {
        if (!alive) {
            if(!moreThan0ne){
                bomb.addPuntaje(puntaje);
            }
            moreThan0ne=true;
            if (!isExploding) {
                if (System.currentTimeMillis() - deathTime > 700) {
                    isExploding = true;
                    explosionStartTime = System.currentTimeMillis();
                    spriteNum = 1;
                } else {
                    BufferedImage deathImage = null;
                    switch (spriteNum) {
                        case 1 -> deathImage = death1;
                        case 2 -> deathImage = death2;
                        case 3 -> deathImage = death3;
                        case 4 -> deathImage = death4;
                        case 5 -> deathImage = death5;
                        case 6 -> deathImage = death6;
                    }
                    g.drawImage(deathImage, (int) x, (int) y, 46, 46, null);
                    return;
                }
            }
            if (isExploding) {
                if (System.currentTimeMillis() - explosionStartTime > 550) return;
                BufferedImage explosionImage = null;
                switch (spriteNum) {
                    case 1 -> explosionImage = deathExp1;
                    case 2 -> explosionImage = deathExp2;
                    case 3 -> explosionImage = deathExp3;
                    case 4 -> explosionImage = deathExp4;
                    case 5 -> explosionImage = deathExp5;
                    case 6 -> explosionImage = deathExp6;
                }
                g.drawImage(explosionImage, (int) x, (int) y, 46, 46, null);
            }
            return;
        }

        //g.setColor(Color.RED);
        //g.drawRect((int)x + solidArea.x, (int)y + solidArea.y, solidArea.width, solidArea.height);
        BufferedImage image = null;
        switch (direccion) {
            case "up" -> image = (spriteNum == 1) ? up1 : (spriteNum == 2) ? up2 : (spriteNum == 3) ? up3 : up4;
            case "down" -> image = (spriteNum == 1) ? down1 : (spriteNum == 2) ? down2 : (spriteNum == 3) ? down3 : down4;
            case "left" -> image = (spriteNum == 1) ? left1 : (spriteNum == 2) ? left2 : (spriteNum == 3) ? left3 : left4;
            case "right" -> image = (spriteNum == 1) ? right1 : (spriteNum == 2) ? right2 : (spriteNum == 3) ? right3 : right4;
        }
        g.drawImage(image, (int)x, (int)y, 42, 48, null);
    }

    public int getPuntaje(){
        return puntaje;
    }
    public void setPuntaje(int p){puntaje=p;}
}
