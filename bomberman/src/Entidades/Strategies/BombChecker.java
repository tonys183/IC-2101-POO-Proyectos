package Entidades.Strategies;

import Entidades.Villanos.Villanos;
import Objects.Bomba;

import java.awt.*;

public interface BombChecker {

    default boolean checkForBomb(Villanos villanos) {
        Bomba bomba = villanos.getBomba();
        if (bomba == null) return false;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int startX = (screenSize.width - (17 * 48)) / 2;
        int startY = (screenSize.height - (15 * 48)) / 2;
        int bx = ((bomba.getX() - startX) / 48);
        int by = ((bomba.getY() - startY) / 48);

        double rbx = startX + bx * 48;
        double rby = startY + by * 48;

        if (villanos.getX() >= rbx && villanos.getX() <= rbx + 48) {
            return villanos.getY() <= rby+48*3 || villanos.getY() <= rby-48*3;
        } else if (villanos.getY() >= rby && villanos.getY() <= rby + 48) {
            return villanos.getX() <= rbx+48*3 || villanos.getX() <= rbx-48*3;
        }
        return false;
    }

    default int moveIfDie(Villanos villanos) {
        Bomba bomba = villanos.getBomba();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int startX = (screenSize.width - (17 * 48)) / 2;
        int startY = (screenSize.height - (15 * 48)) / 2;
        int bx = ((bomba.getX() - startX) / 48);
        int by = ((bomba.getY() - startY) / 48);
        double rbx = startX + bx * 48;
        double rby = startY + by * 48;

        String dir = villanos.getDireccion();
        if (villanos.getX() >= rbx && villanos.getX() <= rbx + 48) {
                if(villanos.getY() >= rby && dir.equals("up")) {
                    return 1;
                }
                return 0;

        } else if (villanos.getY() >= rby && villanos.getY() <= rby + 48) {
            if(villanos.getX() >= rbx && dir.equals("left")) {
                return 3;
                }
                return 2;
        }
        return -1;
    }
}
