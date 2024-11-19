package main;
import Entidades.Actor;
import Entidades.Player;
import Entidades.Villanos.Villanos;
import Objects.*;
import Objects.Cupones.Cupon;

import java.util.ArrayList;

public class AssetSetter {
    private Bomberman bomberman;

    public AssetSetter(Bomberman bomberman) {
        this.bomberman = bomberman;
    }

    public void setBomba(int x, int y) {
        for (int i = 0; i < 30; i++) {
            if (i != 1 && bomberman.getObjects()[i] == null) {
                bomberman.getSound().stopX(11);
                bomberman.getSound().playX(11);
                bomberman.getObjects()[i] = new Bomba();
                bomberman.getObjects()[i].setX(x);
                bomberman.getObjects()[i].setY(y);
                setBombaToVillain((Bomba) bomberman.getObjects()[i]);
                break;
            }
        }
    }

    private void setBombaToVillain(Bomba bomba) {
        ArrayList<Actor> actors = bomberman.getActors();
        for (Actor villanos: actors) ((Villanos) villanos).setBomba(bomba);
    }

    public void setCupon(int x, int y, Cupon cupon){
        bomberman.getObjects()[1] = cupon;
        bomberman.getObjects()[1].setX(x);
        bomberman.getObjects()[1].setY(y);
    }
}
