package main;

import Entidades.Actor;
import Entidades.Player;
import Entidades.Villanos.Villanos;
import Objects.Bomba;

import java.util.ArrayList;

public class CollisionChecker {
    Bomberman bomberman;

    public CollisionChecker(Bomberman bomberman) {
        this.bomberman = bomberman;
    }

    public void checkTile(Actor actor) {
        int actorLeftX = (int) (actor.getX() + actor.getSolidArea().x);
        int actorRightX = (int) (actor.getX() + actor.getSolidArea().x + actor.getSolidArea().width);
        int actorTopY = (int) (actor.getY() + actor.getSolidArea().y);
        int actorBottomY = (int) (actor.getY() + actor.getSolidArea().y + actor.getSolidArea().height);

        int tileSize = bomberman.getCellSize();
        int startX = (bomberman.getWidth() - (bomberman.getSizeY() * tileSize)) / 2;
        int startY = (bomberman.getHeight() - (bomberman.getSizeX() * tileSize)) / 2;

        int actorLeftCol = (actorLeftX - startX) / tileSize;
        int actorRightCol = (actorRightX - startX) / tileSize;
        int actorTopRow = (actorTopY - startY) / tileSize;
        int actorBottomRow = (actorBottomY - startY) / tileSize;

        try {
            int celda1, celda2;
            switch (actor.getDireccion()) {
                case "up":
                    actorTopRow = (int) ((actorTopY - actor.getSpeed()) - startY) / tileSize;
                    celda1 = bomberman.getTileM().getMapTileNum()[actorLeftCol][actorTopRow];
                    celda2 = bomberman.getTileM().getMapTileNum()[actorRightCol][actorTopRow];
                    if ((celda1 == 3 || celda2 == 3) && bomberman.isNextLevel()) {
                        bomberman.startNextLevel();
                        return;
                    }
                    if (celda1 >= 0 && celda1 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda1].isCollision() ||
                            celda2 >= 0 && celda2 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda2].isCollision()) {
                        actor.setCollisionOn(true);
                    }
                    break;
                case "down":
                    actorBottomRow = (int) ((actorBottomY + actor.getSpeed()) - startY) / tileSize;
                    celda1 = bomberman.getTileM().getMapTileNum()[actorLeftCol][actorBottomRow];
                    celda2 = bomberman.getTileM().getMapTileNum()[actorRightCol][actorBottomRow];
                    if ((celda1 == 3 || celda2 == 3) && bomberman.isNextLevel()) {
                        bomberman.startNextLevel();
                        return;
                    }
                    if (celda1 >= 0 && celda1 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda1].isCollision() ||
                            celda2 >= 0 && celda2 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda2].isCollision()) {
                        actor.setCollisionOn(true);
                    }
                    break;
                case "left":
                    actorLeftCol = (int) ((actorLeftX - actor.getSpeed()) - startX) / tileSize;
                    celda1 = bomberman.getTileM().getMapTileNum()[actorLeftCol][actorTopRow];
                    celda2 = bomberman.getTileM().getMapTileNum()[actorLeftCol][actorBottomRow];
                    if ((celda1 == 3 || celda2 == 3) && bomberman.isNextLevel()) {
                        bomberman.startNextLevel();
                        return;
                    }
                    if (celda1 >= 0 && celda1 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda1].isCollision() ||
                            celda2 >= 0 && celda2 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda2].isCollision()) {
                        actor.setCollisionOn(true);
                    }
                    break;
                case "right":
                    actorRightCol = (int) ((actorRightX + 10 - actor.getSpeed()) - startX) / tileSize;
                    celda1 = bomberman.getTileM().getMapTileNum()[actorRightCol][actorTopRow];
                    celda2 = bomberman.getTileM().getMapTileNum()[actorRightCol][actorBottomRow];
                    if ((celda1 == 3 || celda2 == 3) && bomberman.isNextLevel()) {
                        bomberman.startNextLevel();
                        return;
                    }
                    if (celda1 >= 0 && celda1 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda1].isCollision() ||
                            celda2 >= 0 && celda2 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda2].isCollision()) {
                        actor.setCollisionOn(true);
                    }
                    break;
            }
        } catch (IndexOutOfBoundsException e) {

        }
    }

    public void checkTileForATM(Actor actor) {
        int actorLeftX = (int) (actor.getX() + actor.getSolidArea().x);
        int actorRightX = (int) (actor.getX() + actor.getSolidArea().x + actor.getSolidArea().width);
        int actorTopY = (int) (actor.getY() + actor.getSolidArea().y);
        int actorBottomY = (int) (actor.getY() + actor.getSolidArea().y + actor.getSolidArea().height);

        int tileSize = bomberman.getCellSize();
        int startX = (bomberman.getWidth() - (bomberman.getSizeY() * tileSize)) / 2;
        int startY = (bomberman.getHeight() - (bomberman.getSizeX() * tileSize)) / 2;

        int actorLeftCol = (actorLeftX - startX) / tileSize;
        int actorRightCol = (actorRightX - startX) / tileSize;
        int actorTopRow = (actorTopY - startY) / tileSize;
        int actorBottomRow = (actorBottomY - startY) / tileSize;

        try {
            int celda1, celda2;
            switch (actor.getDireccion()) {
                case "up":
                    actorTopRow = (int) ((actorTopY - actor.getSpeed()) - startY) / tileSize;
                    celda1 = bomberman.getTileM().getMapTileNum()[actorLeftCol][actorTopRow];
                    celda2 = bomberman.getTileM().getMapTileNum()[actorRightCol][actorTopRow];
                    if (celda1 == 1 && celda1 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda1].isCollision() ||
                            celda2 == 1 && celda2 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda2].isCollision()) {
                        actor.setCollisionOn(true);
                    }
                    break;
                case "down":
                    actorBottomRow = (int) ((actorBottomY + actor.getSpeed()) - startY) / tileSize;
                    celda1 = bomberman.getTileM().getMapTileNum()[actorLeftCol][actorBottomRow];
                    celda2 = bomberman.getTileM().getMapTileNum()[actorRightCol][actorBottomRow];
                    if (celda1 == 1 && celda1 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda1].isCollision() ||
                            celda2 == 1 && celda2 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda2].isCollision()) {
                        actor.setCollisionOn(true);
                    }
                    break;
                case "left":
                    actorLeftCol = (int) ((actorLeftX - actor.getSpeed()) - startX) / tileSize;
                    celda1 = bomberman.getTileM().getMapTileNum()[actorLeftCol][actorTopRow];
                    celda2 = bomberman.getTileM().getMapTileNum()[actorLeftCol][actorBottomRow];
                    if (celda1 == 1 && celda1 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda1].isCollision() ||
                            celda2 == 1 && celda2 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda2].isCollision()) {
                        actor.setCollisionOn(true);
                    }
                    break;
                case "right":
                    actorRightCol = (int) ((actorRightX + 10 - actor.getSpeed()) - startX) / tileSize;
                    celda1 = bomberman.getTileM().getMapTileNum()[actorRightCol][actorTopRow];
                    celda2 = bomberman.getTileM().getMapTileNum()[actorRightCol][actorBottomRow];
                    if (celda1 == 1 && celda1 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda1].isCollision() ||
                            celda2 == 1 && celda2 < bomberman.getTileM().getTile().length && bomberman.getTileM().getTile()[celda2].isCollision()) {
                        actor.setCollisionOn(true);
                    }
                    break;
            }
        } catch (IndexOutOfBoundsException e) {

        }
    }

    public int checkObject(Actor actor, Boolean player) {
        int index = 999;
        for (int i = 0; i < bomberman.getObjects().length; i++) {
            if (bomberman.getObjects()[i] != null) {
                boolean bombaCollision = true;
                if (bomberman.getObjects()[i] instanceof Bomba && player) {
                    Bomba bomba = (Bomba) bomberman.getObjects()[i];
                    bomba.playerOverlap(actor);
                    bombaCollision = bomba.colis((Player) actor);
                }
                if (player && checkforCupon(actor)){
                    bomberman.getSound().stopX(9);
                    bomberman.getSound().playX(9);
                    return 1;
                }
                actor.getSolidArea().x = (int) (actor.getX() + actor.getSolidArea().x);
                actor.getSolidArea().y = (int) (actor.getY() + actor.getSolidArea().y);
                bomberman.getObjects()[i].getSolidArea().x = bomberman.getObjects()[i].getX() + bomberman.getObjects()[i].getSolidArea().x;
                bomberman.getObjects()[i].getSolidArea().y = bomberman.getObjects()[i].getY() + bomberman.getObjects()[i].getSolidArea().y;
                boolean colision = (bomberman.getObjects()[i] instanceof Bomba) ? bombaCollision : bomberman.getObjects()[i].isCollision();
                switch (actor.getDireccion()) {
                    case "up":
                        actor.getSolidArea().y -= actor.getSpeed();
                        if (actor.getSolidArea().intersects(bomberman.getObjects()[i].getSolidArea())) {
                            if (colision) {
                                actor.setCollisionOn(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        actor.getSolidArea().y += actor.getSpeed();
                        if (actor.getSolidArea().intersects(bomberman.getObjects()[i].getSolidArea())) {
                            if (colision) {
                                actor.setCollisionOn(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        actor.getSolidArea().x += actor.getSpeed();
                        if (actor.getSolidArea().intersects(bomberman.getObjects()[i].getSolidArea())) {
                            if (colision) {
                                actor.setCollisionOn(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        actor.getSolidArea().x -= actor.getSpeed();
                        if (actor.getSolidArea().intersects(bomberman.getObjects()[i].getSolidArea())) {
                            if (colision) {
                                actor.setCollisionOn(true);
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }

                actor.getSolidArea().x = actor.getSolidAreaDefX();
                actor.getSolidArea().y = actor.getSolidAreaDefY();
                bomberman.getObjects()[i].getSolidArea().x = bomberman.getObjects()[i].getSolidAreaDefX();
                bomberman.getObjects()[i].getSolidArea().y = bomberman.getObjects()[i].getSolidAreaDefY();

            }
        }
        return index;
    }

    private boolean checkforCupon(Actor actor) {
        int tileSize = bomberman.getCellSize();
        int startX = (bomberman.getWidth() - (bomberman.getSizeY() * tileSize)) / 2;
        int startY = (bomberman.getHeight() - (bomberman.getSizeX() * tileSize)) / 2;

        int actorLeftX = (int) (actor.getX() + actor.getSolidArea().x);
        int actorRightX = (int) (actor.getX() + actor.getSolidArea().x + actor.getSolidArea().width);
        int actorTopY = (int) (actor.getY() + actor.getSolidArea().y);
        int actorBottomY = (int) (actor.getY() + actor.getSolidArea().y + actor.getSolidArea().height);

        int actorLeftCol = (actorLeftX - startX) / tileSize;
        int actorRightCol = (actorRightX - startX) / tileSize;
        int actorTopRow = (actorTopY - startY) / tileSize;
        int actorBottomRow = (actorBottomY - startY) / tileSize;

        try {
            int celda1, celda2;

            switch (actor.getDireccion()) {
                case "up" -> {
                    actorTopRow = (int) ((actorTopY - actor.getSpeed()) - startY) / tileSize;
                    celda1 = bomberman.getTileM().getMapTileNum()[actorLeftCol][actorTopRow];
                    celda2 = bomberman.getTileM().getMapTileNum()[actorRightCol][actorTopRow];
                    if ((celda1 >= 4 && celda1 <= 11) || (celda2 >= 4 && celda2 <= 11)) {
                        if ((celda1 >= 4 && celda1 <= 11))
                            bomberman.getTileM().getMapTileNum()[actorLeftCol][actorTopRow] = 0;
                        else bomberman.getTileM().getMapTileNum()[actorRightCol][actorTopRow] = 0;
                        return true;
                    }
                }
                case "down" -> {
                    actorBottomRow = (int) ((actorBottomY + actor.getSpeed()) - startY) / tileSize;
                    celda1 = bomberman.getTileM().getMapTileNum()[actorLeftCol][actorBottomRow];
                    celda2 = bomberman.getTileM().getMapTileNum()[actorRightCol][actorBottomRow];
                    if ((celda1 >= 4 && celda1 <= 11) || (celda2 >= 4 && celda2 <= 11)) {
                        if ((celda1 >= 4 && celda1 <= 11))
                            bomberman.getTileM().getMapTileNum()[actorLeftCol][actorBottomRow] = 0;
                        else bomberman.getTileM().getMapTileNum()[actorRightCol][actorBottomRow] = 0;

                        return true;
                    }
                }
                case "left" -> {
                    actorLeftCol = (int) ((actorLeftX - actor.getSpeed()) - startX) / tileSize;
                    celda1 = bomberman.getTileM().getMapTileNum()[actorLeftCol][actorTopRow];
                    celda2 = bomberman.getTileM().getMapTileNum()[actorLeftCol][actorBottomRow];
                    if ((celda1 >= 4 && celda1 <= 11) || (celda2 >= 4 && celda2 <= 11)) {
                        if ((celda1 >= 4 && celda1 <= 11))
                            bomberman.getTileM().getMapTileNum()[actorLeftCol][actorTopRow] = 0;
                        else bomberman.getTileM().getMapTileNum()[actorLeftCol][actorBottomRow] = 0;
                        return true;
                    }
                }
                case "right" -> {
                    actorRightCol = (int) ((actorRightX + 10 - actor.getSpeed()) - startX) / tileSize;
                    celda1 = bomberman.getTileM().getMapTileNum()[actorRightCol][actorTopRow];
                    celda2 = bomberman.getTileM().getMapTileNum()[actorRightCol][actorBottomRow];
                    if ((celda1 >= 4 && celda1 <= 11) || (celda2 >= 4 && celda2 <= 11)) {
                        if ((celda1 >= 4 && celda1 <= 11))
                            bomberman.getTileM().getMapTileNum()[actorRightCol][actorTopRow] = 0;
                        else bomberman.getTileM().getMapTileNum()[actorRightCol][actorBottomRow] = 0;
                        return true;
                    }
                }
                default -> {
                    return false;
                }
            }
            return false;

        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
    public int checkActor(Actor actor, ArrayList<Villanos> target) {
        int index = 999;
        for(int i=0; i< target.size(); i++){
            if(target.get(i) !=null){
                if(!target.get(i).getAlive()) continue;
                actor.getSolidArea().x = (int)(actor.getX() + actor.getSolidArea().x);
                actor.getSolidArea().y = (int)(actor.getY() + actor.getSolidArea().y);

                target.get(i).getSolidArea().x = (int) (target.get(i).getX() + target.get(i).getSolidArea().x);
                target.get(i).getSolidArea().y = (int) (target.get(i).getY() + target.get(i).getSolidArea().y);

                Player player = (Player) actor;
                switch (actor.getDireccion()){
                    case "up":
                        actor.getSolidArea().y -= actor.getSpeed();
                        if(actor.getSolidArea().intersects(target.get(i).getSolidArea()) && !player.isInmortal()){
                            actor.setCollisionOn(true);
                            index = i;
                        }
                        break;
                    case "down":
                        actor.getSolidArea().y += actor.getSpeed();
                        if(actor.getSolidArea().intersects(target.get(i).getSolidArea()) && !player.isInmortal()){
                            actor.setCollisionOn(true);
                            index = i;
                        }
                        break;
                    case "right":
                        actor.getSolidArea().x += actor.getSpeed();
                        if(actor.getSolidArea().intersects(target.get(i).getSolidArea()) && !player.isInmortal()){
                            actor.setCollisionOn(true);
                                index = i;
                        }
                        break;
                    case "left":
                        actor.getSolidArea().x -= actor.getSpeed();
                        if(actor.getSolidArea().intersects(target.get(i).getSolidArea()) && !player.isInmortal()){
                            actor.setCollisionOn(true);
                            index = i;
                        }
                        break;
                }
                actor.getSolidArea().x = actor.getSolidAreaDefX();
                actor.getSolidArea().y = actor.getSolidAreaDefY();
                target.get(i).getSolidArea().x = target.get(i).getSolidAreaDefX();
                target.get(i).getSolidArea().y = target.get(i).getSolidAreaDefY();
            }
        }
        return index;
    }

    public void reset(Bomberman bomberman){
        this.bomberman = bomberman;
    }
}