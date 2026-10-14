package Entidades;

import main.Bomberman;
import main.Dupla;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class VillainManager {
    private Set<Dupla> posMalas;
    private Set<Dupla> villains;
    private Bomberman bomberman;

    public VillainManager(Bomberman bomberman) {
        this.bomberman = bomberman;
        this.posMalas = new HashSet<>();
        this.villains = new HashSet<>();
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                posMalas.add(new Dupla(i, j));
            }
        }
        addTilesPos();
    }

    public Set<Dupla> getVillains() {
        return villains;
    }

    public void addTilesPos() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 17; j++) {
                Dupla dupla = new Dupla(j, i);
                if (!posMalas.contains(dupla)) {
                    if (bomberman.getTileM().getMapTileNum()[j][i] == 0) {
                        villains.add(dupla);
                    } else posMalas.add(dupla);
                }
            }
        }
    }
    public void addPosicion (Dupla nuevaPosicion){
        posMalas.add(nuevaPosicion);
        villains.remove(nuevaPosicion);
    }
    public void reset(){
        posMalas.clear();
        villains.clear();
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                posMalas.add(new Dupla(i, j));
            }
        }
        addTilesPos();
    }
}
