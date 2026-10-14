package Entidades.Strategies;
import main.Bomberman;
import Entidades.Villanos.*;
import Entidades.*;
import java.util.ArrayList;
import java.util.Random;

public class EnemigoLv2 extends Villanos{
    protected int move;
    protected ArrayList<Integer> moves;
    protected Player player;
    protected Random rand = new Random();
    protected int lastRand;
    protected long lastMove;
    protected EnemyShortPathStrategy strategy=new EnemyShortPathStrategy();
    protected RandomStrategy randStrategy = new RandomStrategy();
    protected DontExploteStrategy explote= new DontExploteStrategy();

    protected EnemigoLv2(String nombre, Bomberman bomberman, Boolean ATM, Player player){
        super(nombre, bomberman, ATM);
        this.moves = new ArrayList<>();
        this.player = player;
        this.lastMove = 0;
        this.lastRand = 0;
    }
    private void goodStrat(){
        move = strategy.getMovement(this,player);
        switch (move) {
            case 0 -> direccion = "up";
            case 1 -> direccion = "down";
            case 2 -> direccion = "left";
            case 3 -> direccion = "right";
        }
        setCollisionOn(false);
        if (ATM) bomb.getCollisionChecker().checkTileForATM(this);
        else bomb.getCollisionChecker().checkTile(this);
        bomb.getCollisionChecker().checkObject(this, false);
        if (!getCollisionOn())
            switch (direccion) {
                case "up" ->{
                    moveFoward();
                }
                case "down" -> {moveBackward();
                }
                case "left" ->{
                    moveLeft();
                }
                case "right" ->{
                    moveRight();
                }
            }
        else strategy.movementCorrection(this,player);
    }
    private void randStrat(){
        move = randStrategy.RandMove(this);
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
        if (ATM) bomb.getCollisionChecker().checkTileForATM(this);
        else bomb.getCollisionChecker().checkTile(this);        bomb.getCollisionChecker().checkObject(this,false);
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
    }
    private void dontExplote() {
        switch (move) {
            case 0 -> direccion = "up";
            case 1 -> direccion = "down";
            case 2 -> direccion = "left";
            case 3 -> direccion = "right";
        }
        setCollisionOn(false);
        if (ATM) bomb.getCollisionChecker().checkTileForATM(this);
        else bomb.getCollisionChecker().checkTile(this);
        bomb.getCollisionChecker().checkObject(this, false);
        if (!getCollisionOn()){
            switch (direccion) {
                case "up" -> moveFoward();
                case "down" -> moveBackward();
                case "left" -> moveLeft();
                case "right" -> moveRight();
            }
        }else randStrat();
    }

    @Override
    public void update() {
        if (!alive) {
            long elapsedTime = System.currentTimeMillis() - deathTime;
            dyingAnimation();
            return;
        }
        if (explote.shouldUseThis(this)) {
            move = explote.thenMove(this);
            dontExplote();
        } else if (!explote.shouldIReUseThis()) {

            if(Math.abs(System.currentTimeMillis()-lastMove)>=3000){
                lastMove = System.currentTimeMillis();
                lastRand = rand.nextInt(10000);
            }
            if(lastRand>7500) {
                goodStrat();
            }
            else{
                randStrat();
            }
        }

        else {
            dontExplote();
        }


        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum % 4) + 1;
            spriteCounter = 0;
        }
    }
}
