package Entidades.Strategies;
import Entidades.*;
import Entidades.Villanos.*;
import java.awt.*;
import java.util.ArrayList;
public class EnemyShortPathStrategy implements ShorthestPath{
    private int move;
    private ArrayList<Integer> moves;
    private RandomStrategy randomStrategy = new RandomStrategy();

    EnemyShortPathStrategy(){}
    public int getMovement(Actor actor, Player player) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int startX = (screenSize.width - (17 * 48)) / 2;
        int startY = ((screenSize.height - (15 * 48)) / 2) - 48;
        int sx = (int) Math.round((actor.getX() - startX) / 48.0);
        int sy = (int) Math.round((actor.getY() - startY) / 48.0);
        int px = (int) Math.round((player.getX() - startX) / 48.0);
        int py = (int) Math.round((player.getY() - startY) / 48.0);
        int shortMove = shortestMoves(sx, sy, px, py,actor.getBomb().getTileM().getMapTileNum(), ((Villanos) actor).getATM());
        if (shortMove == -1) {
            move = randomStrategy.RandMove(actor);
        } else {
            move = shortMove;
        } return move;
    }
    public void movementCorrection(Actor actor, Player player) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int startX = (screenSize.width - (17 * 48)) / 2;
        int startY = ((screenSize.height - (15 * 48)) / 2) - 48;
        int sx = (int) Math.round((actor.getX() - startX) / 48.0);
        int sy = (int) Math.round((actor.getY() - startY) / 48.0);
        int px = (int) Math.round((player.getX() - startX) / 48.0);
        int py = (int) Math.round((player.getY() - startY) / 48.0);
        int shortMove = shortestMoves(sx, sy, px, py, actor.getBomb().getTileM().getMapTileNum(),((Villanos) actor).getATM());
        if (shortMove == 0 || shortMove == 1) {
            if (actor.getX() < player.getX()) {
                    actor.moveRight();
            } else  {
                    actor.moveLeft();
            }
        } else if (shortMove == 2 || shortMove == 3) {
            if (actor.getY() < player.getY()) {
                    actor.moveBackward();
            } else {
                    actor.moveFoward();
            }
        }
    }
}
