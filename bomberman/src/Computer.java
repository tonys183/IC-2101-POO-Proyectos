import java.util.*;
public class Computer extends Player {
    private Set<Coordenada> visitados;
    private Stack<Coordenada> stack;
    private Coordenada lastHit;
    private int[][] dirs;

    public Computer(int n) {
        super(n);
        lastHit = null;
        visitados = new HashSet<>();
        stack = new Stack<>();
        dirs = new int[][]{{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                stack.push(new Coordenada(i, j));
        Collections.shuffle(stack);
    }
    private void buscarAxis(Coordenada coordenada) {
        boolean ejeX = coordenada.getX() == lastHit.getX();
        for (int i = ejeX ? 0:1; i < 4; i += 2) {
            int newX = coordenada.getX() + dirs[i][0];
            int newY = coordenada.getY() + dirs[i][1];
            Coordenada newAtaque = new Coordenada(newX, newY);
            if (esValido(newX, newY) && !visitados.contains(newAtaque)) {
                stack.push(newAtaque);
            }
        }
    }

    private void buscarVecinos(Coordenada coord) {
        if (lastHit == null || (lastHit.getX() != coord.getX() && lastHit.getY() != coord.getY())) {
            for (int[] dir : dirs) {
                int newX = coord.getX() + dir[0];
                int newY = coord.getY() + dir[1];
                Coordenada newAtaque = new Coordenada(newX, newY);
                if (esValido(newX, newY) && !visitados.contains(newAtaque)) {
                    stack.push(newAtaque);
                }
            }
        }else buscarAxis(coord);
        lastHit = coord;
    }

    private boolean esValido(int x, int y) {
        return x >= 0 && x < tablero.getSize() && y >= 0 && y < tablero.getSize();}

    public boolean atacarBarcoCompu(Tablero tableroEnemigo) {
        boolean res = false;
        while (!stack.isEmpty()) {
            Coordenada coord = stack.pop();
            if (!visitados.contains(coord)) {
                visitados.add(coord);
                res = atacarBarco(tableroEnemigo, coord.getX(), coord.getY());
                if(res) {
                    if(tableroEnemigo.getCoordenadas().get((coord.getX()*tablero.getSize())+coord.getY()).getBarco().getHundido())
                        lastHit = null;
                    buscarVecinos(coord);
                }
                return res;
            }
        }
        return res;
    }
    public boolean setearBarcos(Barco ship) {
        Random rand = new Random();
        int idx = rand.nextInt(stack.size());
        Coordenada coord = stack.get(idx);
        char l = (idx % 2 == 1) ? 'H' : 'V';
        int arregloX = coord.getX();
        int arregloY = coord.getY();
        if (l == 'H' && coord.getY() + ship.getSize() > tablero.getSize()) {
            arregloY = tablero.getSize() - ship.getSize();
        }
        if (l == 'V' && coord.getX() + ship.getSize() > tablero.getSize()) {
            arregloX = tablero.getSize() - ship.getSize();
        }
        boolean t=tablero.assignShip(ship, arregloX, arregloY, l);
        if(!t) return setearBarcos(ship);
        ship.setOrientation(l);
        return t;
    }
}
