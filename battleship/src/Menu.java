import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Menu extends JPanel implements ActionListener {
    private static JFrame frame;
    private static int ID=0;
    private static int realSize=10;
    private static Player jugadorReal;
    private static Computer compu;
    private static Battleship game;
    private Image imgTitle;
    private static Menu instancia = null;

    private Menu() {
        frame = new JFrame("Game - Battleship");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 480);
        frame.setLocationRelativeTo(null);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.decode("#000000"));

        JButton Play = new JButton("Play original game");
        JButton Exit = new JButton("Exit");
        JButton Playcustom = new JButton("Play custom game");

        styleButtons(Play);
        styleButtons(Exit);
        styleButtons(Playcustom);

        Image imgTitle = new ImageIcon(getClass().getResource("img/title.jpg")).getImage();
        Image img = imgTitle.getScaledInstance(350, 150, Image.SCALE_SMOOTH);
        JLabel picLabel = new JLabel(new ImageIcon(img));
        picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(picLabel);

        add(Box.createVerticalGlue());
        Play.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Play);
        add(Box.createRigidArea(new Dimension(0, 20)));

        Playcustom.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Playcustom);
        add(Box.createRigidArea(new Dimension(0, 20)));

        Exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Exit);
        add(Box.createVerticalGlue());

        Play.addActionListener(this);
        Exit.addActionListener(this);
        Playcustom.addActionListener(this);

        frame.add(this);
        frame.setVisible(true);
    }

    public void styleButtons(JButton button) {
        try {
            Border customBorder = BorderFactory.createLineBorder(Color.decode("#738f93"), 1); // Borde rojo de grosor 3
            button.setBorder(customBorder);
            BufferedImage image = ImageIO.read(getClass().getResource("/img/tex_Button.jpg"));

            BufferedImage transparentImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = transparentImage.createGraphics();

            float opacity =0.8f;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();

            Dimension buttonSize = new Dimension(210, 60);
            Image scaledImage = transparentImage.getScaledInstance(buttonSize.width, buttonSize.height+100, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));

            button.setPreferredSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setForeground(Color.decode("#b3dbe5"));
            button.setFocusPainted(false);

            button.setContentAreaFilled(false);
            button.setOpaque(false);
            button.setHorizontalTextPosition(JButton.CENTER);
            button.setVerticalTextPosition(JButton.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Play original game":
                empezarGame();
                break;
            case "Play custom game":
                empezarCustom();
                break;
            case "Exit":
                System.exit(0);
                break;
        }
    }

    public static void empezarGame() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.getContentPane().removeAll();
                frame.repaint();

                game = Battleship.getInstancia();
                game.setSize(10);
                frame.getContentPane().add(game);
                frame.revalidate();
                frame.repaint();

                jugadorReal = new JugadorReal(10);
                compu = new Computer(10);
                game.setPlayer(jugadorReal, compu);

                ArrayList<Barco> barcosPlayer = crearBarcos();
                ArrayList<Barco> barcosCompu = crearBarcosCompu();

                for (int i = 0; i < 5; i++) pedirCoords(barcosPlayer.get(i), barcosCompu.get(i), jugadorReal, compu);
                return null;
            }

            @Override
            protected void done() {
                start(jugadorReal, compu, game);
            }
        };
        worker.execute();
    }

    public static void start(Player jugador, Computer compu, Battleship game) {
        Arbitro arbi = new Arbitro(jugador, compu, game);
        arbi.arbitrar();
        Console.sleep(100);
    }

    public static void pedirCoords(Barco ship, Barco shipIA, Player jugadorReal, Computer compu) {
        int x = -1, y = -1;
        int c;
        char l = ' ';
        boolean invalido = true;
        boolean asignado = false;

        do {
            try {
                JTextField inputField = new JTextField();
                Object[] message = {
                        "Asignando: " + ship.getTipo() + ", tamaño: " + ship.getSize(),
                        "Escriba las coordenadas en el punto inicial (x,y):",
                        inputField
                };
                Object[] options = {"Confirmar", "Cancelar", "Salir"};

                JOptionPane optionPane = new JOptionPane(
                        message,
                        JOptionPane.QUESTION_MESSAGE,
                        JOptionPane.DEFAULT_OPTION,
                        null,
                        options,
                        options[0]
                );
                JDialog dialog = optionPane.createDialog("Colocación de Barcos");
                dialog.addWindowFocusListener(new WindowAdapter() {
                    @Override
                    public void windowGainedFocus(WindowEvent e) {
                        inputField.requestFocusInWindow();
                    }
                });
                dialog.setVisible(true);
                int option = JOptionPane.CLOSED_OPTION;
                Object selectedValue = optionPane.getValue();
                if (selectedValue != null) option = Arrays.asList(options).indexOf(selectedValue);
                String input = null;
                if (option == 0) input = inputField.getText();
                else if (option == 1) continue;
                else if (option == 2) System.exit(0);
                else System.exit(0);


                if (input == null || input.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Entrada vacía, vuelva a intentar.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                String[] coordenadas = input.split(",");
                if (coordenadas.length != 2) throw new IllegalArgumentException("Formato incorrecto, debe ser (x,y)");
                x = Integer.parseInt(coordenadas[0].trim());
                y = Integer.parseInt(coordenadas[1].trim());

                if (x < 0 || x >= jugadorReal.getTablero().getSize() || y < 0 || y >= jugadorReal.getTablero().getSize()) {
                    JOptionPane.showMessageDialog(null, "Las coordenadas están fuera del rango del tablero.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                String orientacion = JOptionPane.showInputDialog(null, "Escribe la orientación (H para Horizontal, V para Vertical):", "Seleccionar Orientación", JOptionPane.QUESTION_MESSAGE);
                if (orientacion == null || orientacion.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Debes ingresar una orientación válida (H o V).", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                l = orientacion.toUpperCase().charAt(0);
                if (l != 'H' && l != 'V') {
                    JOptionPane.showMessageDialog(null, "Solo se permiten H (Horizontal) o V (Vertical).", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                ship.setOrientation(l);

                if ((l == 'H' && (y + ship.getSize() - 1 >= jugadorReal.getTablero().getSize())) ||
                        (l == 'V' && (x + ship.getSize() - 1 >= jugadorReal.getTablero().getSize()))) {
                    JOptionPane.showMessageDialog(null, "El barco se sale del tablero con esas coordenadas y orientación.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                asignado = jugadorReal.getTablero().assignShip(ship, x, y, l);
                if (!asignado) {
                    JOptionPane.showMessageDialog(null, "Ya hay un barco en esa coordenada.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    invalido = false;
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Las coordenadas deben ser números enteros.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (invalido || !asignado);
        compu.setearBarcos(shipIA);
    }

    public static ArrayList<Barco> crearBarcos() {
        return new ArrayList<>(Arrays.asList(
                new Portaviones(++ID),
                new Acorazado(++ID),
                new Destructor(++ID),
                new Submarino(++ID),
                new Lancha(++ID)
        ));
    }

    public static ArrayList<Barco> crearBarcosCompu() {
        return new ArrayList<>(Arrays.asList(
                new Portaviones(++ID),
                new Acorazado(++ID),
                new Destructor(++ID),
                new Submarino(++ID),
                new Lancha(++ID)
        ));
    }

    public static void empezarCustom() {
    do {
        try {
            JTextField inputField = new JTextField();
            Object[] message = {
                    "Digite el tamaño del tablero:",
                    inputField
            };
            Object[] options = {"Confirmar", "Cancelar", "Salir"};

            JOptionPane optionPane = new JOptionPane(
                    message,
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    options,
                    options[0]
            );

            JDialog dialog = optionPane.createDialog("Crear Tablero Custom");
            dialog.addWindowFocusListener(new WindowAdapter() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
                    inputField.requestFocusInWindow();
                }
            });
            dialog.setVisible(true);

            int option = JOptionPane.CLOSED_OPTION;
            Object selectedValue = optionPane.getValue();
            if (selectedValue != null) option = Arrays.asList(options).indexOf(selectedValue);
            String size = null;
            if (option == 0) size = inputField.getText();
            else if (option == 1) return;
            else if (option == 2) System.exit(0);
            else return;

            realSize = Integer.parseInt(size);
            if (realSize <= 10) JOptionPane.showMessageDialog(null, "El tamaño del tablero debe ser mayor a 10.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } while (realSize <= 10);
        game=Battleship.getInstancia();
        game.setSize(realSize);
        pedirCustom();
}

public static void pedirCustom() {
    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.getContentPane().removeAll();
            frame.repaint();

            frame.getContentPane().add(game);
            frame.revalidate();
            frame.repaint();

            jugadorReal = new JugadorReal(realSize);
            compu = new Computer(realSize);
            game.setPlayer(jugadorReal, compu);

            ArrayList<Barco> barcosPlayer = new ArrayList<>(Arrays.asList(
                    new Portaviones(++ID),
                    new Acorazado(++ID),
                    new Destructor(++ID),
                    new Submarino(++ID),
                    new Lancha(++ID)
            ));
            ArrayList<Barco> barcosCompu = new ArrayList<>(Arrays.asList(
                    new Portaviones(++ID),
                    new Acorazado(++ID),
                    new Destructor(++ID),
                    new Submarino(++ID),
                    new Lancha(++ID)
            ));

            for (int i = 0; i < 5; i++) pedirCoords(barcosPlayer.get(i), barcosCompu.get(i), jugadorReal, compu);
            int maxi = ((realSize)/2);
            int cant=maxi-5;
            if(cant==0) return null;
            int indx=1;
            int cSize=0;
            Set<Integer> repetidos = new HashSet<>(Arrays.asList(5, 4, 3, 2));
            do {
                try {
                    JTextField inputField = new JTextField();
                    Object[] message = {
                            "Tamaño MAX del barco: " + maxi  + "\nDigite el tamaño del Barco Custom número ["+ indx + "]: ",
                            inputField
                    };
                    Object[] options = {"Crear", "Cancelar", "Salir"};

                    JOptionPane optionPane = new JOptionPane(
                            message,
                            JOptionPane.QUESTION_MESSAGE,
                            JOptionPane.DEFAULT_OPTION,
                            null,
                            options,
                            options[0]
                    );

                    JDialog dialog = optionPane.createDialog("Crear Barco Custom");
                    dialog.addWindowFocusListener(new WindowAdapter() {
                        @Override
                        public void windowGainedFocus(WindowEvent e) {
                            inputField.requestFocusInWindow();
                        }
                    });
                    dialog.setVisible(true);

                    int option = JOptionPane.CLOSED_OPTION;
                    Object selectedValue = optionPane.getValue();
                    if (selectedValue != null) option = Arrays.asList(options).indexOf(selectedValue);
                    String input = null;
                    if (option == 0) input = inputField.getText();
                    else if (option == 1) continue;
                    else if (option == 2) return null;
                    else return null;

                    cSize = Integer.parseInt(input);
                    if (cSize > maxi) {
                        JOptionPane.showMessageDialog(null, "El tamaño del barco debe ser menor o igual que " + maxi + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(repetidos.contains(cSize)) JOptionPane.showMessageDialog(null, "Ya existe un barco de tamaño " + cSize + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    else {
                        repetidos.add(cSize);
                        Barco novo = new CustomShip(cSize,++ID);
                        Barco novoCompu= new CustomShip(cSize,++ID);
                        pedirCoords(novo, novoCompu, jugadorReal, compu);
                        cant--;
                        indx++;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } while (cant>0);
            return null;
        }

        @Override
        protected void done() {
            start(jugadorReal, compu, game);
        }
    };
    worker.execute();
    }
    public static Menu getInstancia(){
        if(instancia == null){
            instancia = new Menu();
        }
        return instancia;
    }

}