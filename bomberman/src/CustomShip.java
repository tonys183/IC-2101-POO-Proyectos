public class CustomShip extends Barco{
    private int peso;

    CustomShip(int size, int ID){
        super("Custom",size,ID );
        this.peso = size;
    }
    public int getPeso(){return peso; }
}
