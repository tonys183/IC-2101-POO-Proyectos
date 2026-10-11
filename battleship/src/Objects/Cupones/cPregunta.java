package Objects.Cupones;

import Entidades.Player;

public class cPregunta implements CuponStrategy {
    @Override
    public void apply(Player player) {
        player.flipHELL(true);
    }
    @Override
    public void unapply(Player player) {
        player.flipHELL(false);
    }
}
