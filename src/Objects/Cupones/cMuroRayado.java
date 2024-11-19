package Objects.Cupones;

import Entidades.Player;

public class cMuroRayado implements CuponStrategy {

    @Override
    public void apply(Player player) {
        player.flipATM(true);
    }
    @Override
    public void unapply(Player player) {
        player.flipATM(false);
    }
}
