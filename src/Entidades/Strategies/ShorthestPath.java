package Entidades.Strategies;

import java.util.*;

public interface ShorthestPath {
    default int shortestMoves(int sx, int sy, int fx, int fy, int[][] mapa, boolean ATM) {
        int shortest = Integer.MAX_VALUE;
        int shortestMove = -1;

        for (int neighbor : vecinos(sx, sy, mapa,ATM)) {
            int newX = sx, newY = sy;
            switch (neighbor) {
                case 0 -> newY = sy - 1; // Arriba
                case 1 -> newY = sy + 1; // Abajo
                case 2 -> newX = sx - 1; // Izquierda
                case 3 -> newX = sx + 1; // Derecha
            }
            int heuristic = getHeuristic(newX, newY, fx, fy);
            if (heuristic < shortest) {
                shortest = heuristic;
                shortestMove = neighbor;
            }
        }
        return shortestMove;
    }
    private int getHeuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private List<Integer> vecinos(int sx, int sy, int[][] mapa, boolean ATM) {
        List<Integer> neighbors = new ArrayList<>();
        int[][] dirs = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        int move = -1;
        for (int[] direction : dirs) {
            move++;
            int newX = sx + direction[0];
            int newY = sy + direction[1];
            if (newX >= 0 && newX < mapa.length && newY >= 0 && newY < mapa[0].length) {
                boolean obstaculo= ATM ? mapa[newX][newY] == 1 : mapa[newX][newY] != 0;
                if(!obstaculo) neighbors.add(move);
            }
        }
        return neighbors;
    }
}
