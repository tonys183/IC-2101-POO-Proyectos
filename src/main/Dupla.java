package main;
import java.util.Objects;

public class Dupla<A, B> {
    private A primero;
    private B segundo;

    public Dupla(A primero, B segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }

    public A getPrimero() {
        return primero;
    }

    public B getSegundo() {
        return segundo;
    }

    public void setPrimero(A primero) {
        this.primero = primero;
    }

    public void setSegundo(B segundo) {
        this.segundo = segundo;
    }

    @Override
    public String toString() {
        return "(" + primero + ", " + segundo + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dupla that = (Dupla) o;
        return primero == that.primero && segundo == that.segundo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(primero, segundo);
    }
}

