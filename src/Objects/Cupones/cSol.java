package Objects.Cupones;

import Entidades.Player;

public class cSol implements CuponStrategy {

    @Override
    public void apply(Player player) {
        player.getBombManager().setRango( player.getBombManager().getRango()+2);
    }
    @Override
    public void unapply(Player player) {
        player.getBombManager().setRango( player.getBombManager().getRango()-2);
    }
}