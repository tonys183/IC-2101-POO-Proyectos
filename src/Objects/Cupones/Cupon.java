package Objects.Cupones;

import Entidades.Player;
import Objects.SuperObject;
import java.awt.*;

public class Cupon extends SuperObject {
    private boolean AF;
    private int level;
    private CuponStrategy cuponStrategy;
    private String tipo;

    public Cupon(String tipo, CuponStrategy cuponStrategy){
        this.tipo = tipo;
        this.cuponStrategy = cuponStrategy;
        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width=42;
        solidArea.height=42;
        switch (this.tipo){
            case "Sol"->{
                level=1;
                AF=true;
            }
            case "BombaD"->{
                level=2;
                AF=true;
            }
            case "Detonador"->{
                level=3;
                AF=false;
            }
            case "Patin"->{
                level=4;
                AF=true;
            }
            case "BombaR"->{
                level=11;
                AF=false;
            }
            case "MuroR"->{
                level=14;
                AF=false;
            }
            case "Pregunta"->{
                level=19;
                AF=false;
            }
            case "HELL"->{
                level=23;
                AF=false;
            }
            default -> throw new IllegalArgumentException("Tipo no valido");
        }
    }
    public void useCupon(Player player){
        cuponStrategy.apply(player);
    }

    public void deactivateCupon(Player player){

        //System.out.println("Desactive "+ tipo);
        cuponStrategy.unapply(player);
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isAF() {
        return AF;
    }
}
