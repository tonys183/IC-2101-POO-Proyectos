package Entidades.Villanos;
import Entidades.*;
import Events.EventManager;
import main.*;

import java.util.*;

public class VillanoFactory {
    private static Random random = new Random();
    private static String[] villanos = {"Globo", "Cel", "Haki","Espon", "Fant", "Mon", "MonG"};

    private static Villanos crearVillanoAleatorio(Player player, Bomberman bomberman, int level) {
        int restriccion;
        if(level==1 || level==2 || level==3) restriccion=level;
        else if (level<6) restriccion=3;
        else if(level<8)restriccion=4;
        else if(level<11) restriccion=5;
        else if(level<14)restriccion=6;
        else restriccion=7;
        String tipo = villanos[random.nextInt(restriccion)];
        switch (tipo) {
            case "Cel" -> {
                return new Cel(bomberman);
            }
            case "Globo" -> {
                return new Globo(bomberman);
            }
            case "Espon" -> {
                return new Espon(bomberman,player);
            }
            case "Haki" -> {
                return new Haki(bomberman,player);
            }
            case "Fant" -> {
                return new Fant(bomberman,player);
            }
            case "Mon" -> {
                return new Mon(bomberman,player);
            }
            case "MonG" -> {
                return new MonG(bomberman,player);
            }
        } return null;
    }

    public static void crearVillanosParaNivel(ArrayList<Actor> villanos, int cantidad, Player player, Bomberman bomberman, int level, EventManager eventManager) {
        for (int i = 0; i < cantidad; i++) {
            Villanos villanos1 = crearVillanoAleatorio(player,bomberman,level);
            eventManager.addActor(villanos1);
            villanos.add(villanos1);
        }
    }
}