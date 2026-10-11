import java.util.*;
import javax.swing.*;

public class Tablero {
    private ArrayList<Coordenada> coordenadas = new ArrayList();
    private ArrayList<Barco> barcos;
    private int size;

    public Tablero(int size) {
        this.size = size;
        this.barcos = new ArrayList<>();
        setCoordenadas();
    }

    public ArrayList<Barco> getBarcos() {
        return barcos;
    }
    public String mostrarBarcos(){
        String str="";
        if(barcos.isEmpty()) return str;
        int indx=1;
        for(int i=0; i<barcos.size()-1; i++){
            String ship=barcos.get(i).getTipo();
            if(ship=="Custom"){
                str+=ship+" ["+indx+"], ";
                indx++;
            }
            else str+= ship+", ";
        }
        if(barcos.get(barcos.size()-1).getTipo()=="Custom") str+= barcos.get(barcos.size()-1).getTipo()+" ["+indx+"]";
        else str+= barcos.get(barcos.size()-1).getTipo();
        return str;
    }
    public int getSize() { return size; }
    public ArrayList<Coordenada> getCoordenadas() { return coordenadas; }

    public boolean contains(Barco barco){
        for (Barco b : barcos){
            if(b.equals(barco)) return true;
        }
        return false;
    }

    public void setCoordenadas() {
        for (int i = 0; i <this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                coordenadas.add(new Coordenada(i,j));
            }
        }
    }
    private boolean right(Barco ship,int x, int y){
        ArrayList<Coordenada> coords= new ArrayList<>();
        for(int i=0; i <ship.size; i++){
            Coordenada coordenada=coordenadas.get((x*size)+(y+i));
            if(coordenada.getBarco()!=null) return false;
            coords.add(coordenada);
        }
        for(Coordenada c : coords) {
            ship.agregarCoordenada(c);
            c.setBarco(ship);
        }
        return true;
    }
    private boolean down(Barco ship,int x, int y){
        ArrayList<Coordenada> coords= new ArrayList<>();
        for(int i=0; i <ship.size; i++){
            Coordenada coordenada=coordenadas.get(((x+i)*size)+y);
            if(coordenada.getBarco()!=null) return false;
            coords.add(coordenada);
        }
        for(Coordenada c : coords) {
            ship.agregarCoordenada(c);
            c.setBarco(ship);
        }
        return true;
    }
    public boolean assignShip(Barco ship, int x, int y, char P){
        if(!contains(ship)) barcos.add(ship);
        if(P == 'H') return right(ship, x, y);
        else return down(ship, x, y);
    }

    public boolean resultAtaque(int x, int y){
        if(y>=size) return false;
        try{
            Coordenada coordenada=coordenadas.get((x*size)+y);
            Barco barco = coordenada.getBarco();
            if(coordenada.getGolpeado() || coordenada.getMiss()){
                return true;
            }
            if(barco!=null){
                barco.hit();
                coordenada.setGolpeado(true);
                if(barco.getHundido()){
                    barcos.remove(barco);
                    JOptionPane.showMessageDialog(null,barco.getTipo()+" hundido","Acierto",JOptionPane.INFORMATION_MESSAGE );

                }
                return true;
            }
            else{
                coordenada.setMiss(true);
                return false;
            }
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("FALLÓ");
            return false;
        }
    }

    public void myTablero(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Coordenada coordenada = coordenadas.get((i * size) + j);
                if (coordenada.getMiss()) {
                    System.out.print("⭕");
                }
                else if (coordenada.getBarco()!=null && coordenada.getBarco().getHundido()) {
                    System.out.print("☠️");
                }
                else if (coordenada.getGolpeado()) {
                        System.out.print("💥");
                }
                else if (coordenada.getBarco() != null) {
                    System.out.print("🚢");
                }
                else {
                    System.out.print("💧");
                }
            }
            System.out.println();
        }
    }

    public void compTablero(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Coordenada coordenada = coordenadas.get((i * size) + j);
                if (coordenada.getMiss()) {
                    System.out.print("⭕");
                }
                else if (coordenada.getBarco()!=null && coordenada.getBarco().getHundido()) {
                    System.out.print("☠️");
                }
                else if (coordenada.getGolpeado()) {
                    System.out.print("💥");
                } else {
                    System.out.print("💧");
                }
            }
            System.out.println();
        }
    }
}
