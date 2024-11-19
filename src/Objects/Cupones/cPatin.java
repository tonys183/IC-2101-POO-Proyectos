package Objects.Cupones;

import Entidades.Player;

public class cPatin implements CuponStrategy {

    @Override
    public void apply(Player player) {
        player.setSpeed((float) (2));
    }
    @Override
    public void unapply(Player player) {player.setSpeed((float) (1.5));
    }
}
