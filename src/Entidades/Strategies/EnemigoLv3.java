package Entidades.Strategies;
import main.Bomberman;
import Entidades.Villanos.*;
import Entidades.*;

public class EnemigoLv3 extends Villanos {
    protected int move;
    protected EnemyShortPathStrategy strategy;
    protected RandomStrategy random;
    protected Player player;
    private static final double DETECTION_RADIUS = 312.0;
    protected DontExploteStrategy explote= new DontExploteStrategy();


    protected EnemigoLv3(String nombre, Bomberman bomberman, Boolean ATM, Player player) {
        super(nombre, bomberman, ATM);
        this.player = player;
        strategy = new EnemyShortPathStrategy();
        random = new RandomStrategy();
    }

    private double calculateDistance() {
        return Math.sqrt(Math.pow(player.getX() - getX(), 2) + Math.pow(player.getY() - getY(), 2));
    }

    private boolean playerInRange() {
        return calculateDistance() <= DETECTION_RADIUS;
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
    private void goodStrat() {
        move = strategy.getMovement(this, player);
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
        if (!getCollisionOn()) {
            switch (direccion) {
                case "up" -> {
                    moveFoward();
                }
                case "down" -> {
                    moveBackward();
                }
                case "left" -> {
                    moveLeft();
                }
                case "right" -> {
                    moveRight();
                }
            }
        } else {
            strategy.movementCorrection(this, player);
        }
    }

    private void randStrat() {
        move = random.RandMove(this);
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
        if (!getCollisionOn()) {
            switch (direccion) {
                case "up" -> moveFoward();
                case "down" -> moveBackward();
                case "left" -> moveLeft();
                case "right" -> moveRight();
            }
        }
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

            if (playerInRange()) {
                goodStrat();
            } else {
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
