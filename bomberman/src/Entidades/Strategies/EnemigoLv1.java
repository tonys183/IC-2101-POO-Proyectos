package Entidades.Strategies;
import main.Bomberman;
import Entidades.Villanos.*;
import Entidades.*;

public class EnemigoLv1 extends Villanos {
    protected int move;
    protected Player player;
    protected HighlyRandomStrategy strategy= new HighlyRandomStrategy();
    protected DontExploteStrategy explote= new DontExploteStrategy();

    protected EnemigoLv1(String nombre, Bomberman bomberman, Boolean ATM, Player player) {
        super(nombre, bomberman, ATM);
        this.player = player;
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
        } else randStrat();

    }

    private void randStrat() {
        move = strategy.useRandom(this);
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
        switch (direccion) {
            case "up" -> moveFoward();
            case "down" -> moveBackward();
            case "left" -> moveLeft();
            case "right" -> moveRight();
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
        }else goodStrat();
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
            goodStrat(); // Otra estrategia si no se debe reutilizar `explote`.
        } else {
            dontExplote();
        }

        // Actualiza la animación.
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum % 4) + 1;
            spriteCounter = 0;
        }
    }

}
