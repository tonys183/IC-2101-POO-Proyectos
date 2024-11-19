package Entidades.Strategies;
import Entidades.Player;
import main.Bomberman;
import Entidades.Villanos.*;

public class EnemigoRND extends Villanos {
    protected int move;
    protected RandomStrategy randomStrategy = new RandomStrategy();
    protected EnemigoRND(String tipo, Bomberman bomb, boolean ATM, Player player) {
        super(tipo,bomb,ATM);
    }
    @Override
    public void update() {
        if (!alive) {
            if (!isExploding) {
                if (System.currentTimeMillis() - deathTime < 500) {
                    dyingAnimation();
                } else {
                    isExploding = true;
                    explosionStartTime = System.currentTimeMillis();
                    spriteNum = 1;
                }
            } else {
                if (System.currentTimeMillis() - explosionStartTime < 550) {
                    explosionAnimation();
                }
            }
            return;
        }
        move = randomStrategy.RandMove(this);
        switch (move) {
            case 0:
                direccion = "up";
                break;
            case 1:
                direccion = "down";
                break;
            case 2:
                direccion = "left";
                break;
            case 3:
                direccion = "right";
                break;
        }

        setCollisionOn(false);
        bomb.getCollisionChecker().checkTile(this);
        bomb.getCollisionChecker().checkObject(this,false);
        if(!getCollisionOn()){
            switch (direccion) {
                case "up":
                    moveFoward();
                    break;
                case "down":
                    moveBackward();
                    break;
                case "left":
                    moveLeft();
                    break;
                case "right":
                    moveRight();
                    break;
            }
        }
    spriteCounter++;
    if(spriteCounter > 1){
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
}
