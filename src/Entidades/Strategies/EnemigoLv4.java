package Entidades.Strategies;

import Entidades.Player;
import Entidades.Villanos.Villanos;
import main.Bomberman;

public class EnemigoLv4 extends Villanos {
    protected int move;
    protected EnemyShortPathStrategy strategy;
    protected Player player;
    protected DontExploteStrategy explote= new DontExploteStrategy();
    protected RandomStrategy randStrategy = new RandomStrategy();

    protected EnemigoLv4(String tipo, Bomberman bomb,boolean atm, Player player){
        super(tipo,bomb,atm);
        strategy=new EnemyShortPathStrategy();
        this.player=player;
    }

    public EnemigoLv4(Player player) {
        super();
        strategy=new EnemyShortPathStrategy();
        this.player=player;
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
        }else{
            move = strategy.getMovement(this, player);
            switch (move) {
                case 0 -> direccion = "up";
                case 1 -> direccion = "down";
                case 2 -> direccion = "left";
                case 3 -> direccion = "right";
            }
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
        } else {
            dontExplote();
        }
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum % 4) + 1;
            spriteCounter = 0;
        }
    }
}
