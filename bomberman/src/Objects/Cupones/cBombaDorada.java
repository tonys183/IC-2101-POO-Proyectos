package Objects.Cupones;

import Entidades.Player;

public class cBombaDorada implements CuponStrategy {
    @Override
    public void apply(Player  player){
        player.getBombManager().setCantBombas(player.getBombManager().getCantBombas()+1);
        //System.out.println(player.getBombManager().getCantBombas());
    }
    @Override
    public void unapply(Player  player){
        player.getBombManager().setCantBombas( player.getBombManager().getCantBombas()-1);
    }
}
