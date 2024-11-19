package Entidades.Strategies;

import Entidades.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomStrategy {
    private long lastMove;
    private int move;
    private Random random = new Random();
    private ArrayList<Integer> moves;
    RandomStrategy() {
        this.moves=new ArrayList<>();
    }
    private void setMoves(){
        moves.add(0);
        moves.add(1);
        moves.add(3);
        moves.add(2);
        Collections.shuffle(moves);
    }
    public int RandMove(Actor actor){
        int timeRand= random.nextInt(8000);
        if (System.currentTimeMillis() - lastMove > (2000+timeRand)) {
            setMoves();
            move = moves.get(random.nextInt(moves.size()));
            lastMove = System.currentTimeMillis();
        }
        if(actor.getCollisionOn()){
            switch (move) {
                case 0:
                    move=3;
                    break;
                case 1:
                    move = 2;
                    break;
                case 2:
                    move = 0;
                    break;
                case 3:
                    move = 1;
                    break;
            }
        }
        return move;
    }
}
