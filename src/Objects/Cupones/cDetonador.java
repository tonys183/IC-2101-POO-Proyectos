package Objects.Cupones;

import Entidades.Player;

public class cDetonador implements CuponStrategy {

    @Override
    public void apply(Player player) {
        player.flipDetonator(true);
    }
    @Override
    public void unapply(Player player) {
        player.flipDetonator(false);
    }

}
