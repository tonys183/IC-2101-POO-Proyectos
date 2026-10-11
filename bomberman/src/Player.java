import java.util.ArrayList;

public class Player {
    protected Tablero tablero;
    public Player() {}
    public Player(int n) {this.tablero = new Tablero(n);}
    public boolean atacarBarco(Tablero tableroEnemigo, int x, int y) {return tableroEnemigo.resultAtaque(x,y);}
    public Tablero getTablero() {return tablero;}

}