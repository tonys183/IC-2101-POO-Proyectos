package Objects.Cupones;

import Entidades.Player;

public class cBombaRayada implements CuponStrategy {

    @Override
    public void apply(Player player) {
        player.setBOMBR(true);
    }
    @Override
    public void unapply(Player  player){
        player.setBOMBR(false);
    }
}

