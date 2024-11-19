package Objects.Cupones;
import main.Dupla;
import java.util.Random;
public class CuponFactory {
    private static Random random = new Random();
    private static boolean patin=false;
    private static String[] cupones = {"Sol", "BombaD", "Detonador", "Patin", "BombaR", "MuroR", "Pregunta", "HELL"};
    public static Dupla<Cupon, Integer> createCupon(int level) {
        int restriccion;
        if (level <= 4) restriccion = level;
        else if (level<11) restriccion = 4;
        else if (level < 15) restriccion = 5;
        else if (level < 19) restriccion = 6;
        else if (level < 23) restriccion = 7;
        else restriccion = 8;
        String tipo = cupones[random.nextInt(restriccion)];
        if (level == 1 || level == 2 || level == 3 || level == 4) {
            tipo = cupones[level - 1];
        } else if (level == 11) {
            tipo = cupones[4];
        } else if (level == 14) {
            tipo = cupones[5];
        } else if (level == 19 ) {
            tipo = cupones[6];
        } else if (level == 23) {
            tipo = cupones[7];
        }
        if(tipo.equals("Patin") && patin) tipo = cupones[random.nextInt(restriccion)];
        //System.out.println(tipo);
        switch (tipo) {
            case "Sol" -> {
                return new Dupla<>(new Cupon("Sol", new cSol()), 4);
            }
            case "BombaD" -> {
                return new Dupla<>(new Cupon("BombaD", new cBombaDorada()), 5);
            }
            case "Detonador" -> {
                return new Dupla<>(new Cupon("Detonador", new cDetonador()), 6);
            }
            case "Patin" -> {
                patin=true;
                return new Dupla<>(new Cupon("Patin", new cPatin()), 7);
            }
            case "BombaR" -> {
                return new Dupla<>(new Cupon("BombaR", new cBombaRayada()), 8);
            }
            case "MuroR" -> {
                return new Dupla<>(new Cupon("MuroR", new cMuroRayado()), 9);
            }
            case "Pregunta" -> {
                return new Dupla<>(new Cupon("Pregunta", new cPregunta()), 10);
            }
            case "HELL" -> {
                return new Dupla<>(new Cupon("HELL", new cHombreEnLamas()), 11);
            }
            default -> throw new IllegalArgumentException("Tipo de cupón inválido: " + tipo);
        }
    }
}
