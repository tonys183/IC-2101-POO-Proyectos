package Entidades.Strategies;

import Entidades.*;
import Objects.Bomba;

import java.awt.*;
import java.util.Random;

public class HighlyRandomStrategy {
    private int move;
    private RandomStrategy randomStrategy = new RandomStrategy();
    public HighlyRandomStrategy() {}

    public int getMovement(Actor actor, Player player) {
        int tableroWidth = actor.getBomb().getSizeY() * actor.getBomb().getCellSize();
        int tableroHeight = actor.getBomb().getSizeX() * actor.getBomb().getCellSize();
        int startX = (actor.getBomb().getWidth() - tableroWidth) / 2;
        int startY = (actor.getBomb().getHeight() - tableroHeight) / 2;
        int sx = (int) ((actor.getX() - startX) / 48);
        int sy = (int) ((actor.getY() - startY) / 48);
        int px = (int) ((player.getX() - startX) / 48);
        int py = (int) ((player.getY() - startY) / 48);
        if (sx == px || sy == py) {
            if (sx == px) {
                move = (sy < py) ? 1 : 0;
            }
            if (sy == py) {
                move = (sx < px) ? 3 : 2;
            }
        } else move = randomStrategy.RandMove(actor);
        return move;
    }

    public int useRandom(Actor actor) {
        return randomStrategy.RandMove(actor);
    }
}
