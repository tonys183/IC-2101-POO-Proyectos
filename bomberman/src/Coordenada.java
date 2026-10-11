import java.util.Objects;
import javax.swing.*;

public class Coordenada {
    private int x;
    private int y;
    private Barco barco;
    private boolean golpeado;
    private boolean miss;

    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
        this.golpeado = false;
        this.barco = null;
        this.miss = false;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setGolpeado(boolean hit) {
        this.golpeado = hit;
    }
    public boolean getGolpeado() {
        return this.golpeado;
    }
    public void setBarco(Barco barco) {
        this.barco = barco;
    }
    public Barco getBarco() {
        return barco;
    }
    public void setMiss(boolean miss){
        this.miss = miss;
    }
    public boolean getMiss(){
        return this.miss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordenada that = (Coordenada) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}