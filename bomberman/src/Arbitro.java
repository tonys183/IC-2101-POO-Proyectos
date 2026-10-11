import javax.swing.*;
import java.sql.SQLOutput;
import java.util.Scanner;


public class Arbitro {
    private Player player1;
    private Computer player2;
    private Battleship tableroUI;
    private boolean turnoPlayer1;

    public Arbitro(Player player1, Computer player2, Battleship tableroUI) {
        this.player1 = player1;
        this.player2 = player2;
        this.tableroUI = tableroUI;
        this.turnoPlayer1 = true;
    }

    public void arbitrar() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                while(!verificarWinner()){
                    if(turnoPlayer1) {
                        tableroUI.setTurno(true);
                        tableroUI.enablePlayerButtons(true);
                        while (tableroUI.getTurno()) Thread.sleep(2000);
                        tableroUI.enablePlayerButtons(false);
                        turnoPlayer1 = false;
                    } else {
                        Console.sleep(1500);
                        ataqueCompu();
                        turnoPlayer1 = true;
                    }
                }
                return null;
            }
        };
        worker.execute();
    }

    public boolean verificarWinner(){
        if(player1.getTablero().getBarcos().isEmpty()){
            mostrarGanador("Ganador: Player 2");
            return true;
        } else if (player2.getTablero().getBarcos().isEmpty()) {
            mostrarGanador("Ganador: Player 1");
            return true;
        }
        return false;
    }

    public void ataqueCompu() {
        player2.atacarBarcoCompu(player1.getTablero());
    }
    public void mostrarGanador(String mensaje) {
        JOptionPane.showMessageDialog(tableroUI, mensaje);
        System.exit(0);
    }
}