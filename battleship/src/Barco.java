import java.util.*;

abstract public class Barco {
    protected String tipo;
    protected int size;
    protected ArrayList<Coordenada> coordenadasBarc = new ArrayList();
    protected boolean hundido;
    protected int ID;
    protected boolean h;
    protected Barco() {}
    protected Barco(String tipo, int size, int ID){
        this.tipo = tipo;
        this.size = size;
        this.hundido = false;
        this.ID = ID;
    }

    public ArrayList<Coordenada> getCoordenadasBarc() { return coordenadasBarc; }
    public String getTipo() { return tipo; }
    public boolean getHundido(){ return hundido; }
    public int getID() { return ID; }
    public int getSize() {return size;}

    public void agregarCoordenada(Coordenada coordenada){
        if(coordenadasBarc.size() < size) coordenadasBarc.add(coordenada);
    }
    public void setOrientation(char y){ this.h = y =='H'; }
    public boolean equals(Barco b){
        return ID== b.ID;
    }
    public void hit(){
        if(!hundido){
            size--;
            if(size==0){
                hundido = true;
            }
        }
    }
}
