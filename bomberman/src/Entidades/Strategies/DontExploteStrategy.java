package Entidades.Strategies;

import Entidades.Villanos.Villanos;

public class DontExploteStrategy implements BombChecker{
    private long lastMove;
    public DontExploteStrategy(){
        lastMove = -1;
    }
    public boolean shouldUseThis(Villanos villanos){
        if(lastMove !=-1 && System.currentTimeMillis() - lastMove > 2000){
            lastMove=-1;
        }
        return lastMove == -1 && checkForBomb(villanos);
    }

    public boolean shouldIReUseThis(){
        return lastMove !=-1 && System.currentTimeMillis() - lastMove < 2000;
    }
    public int thenMove(Villanos villanos){
        lastMove=System.currentTimeMillis();
        return moveIfDie(villanos);
    }
}
