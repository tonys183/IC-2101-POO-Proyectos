import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class Battleship extends JPanel {
    private int size;
    private int cellSize;
    private Player player1;
    private Computer player2;
    private boolean turno;
    private JButton[][] buttons;
    private boolean buttonsCreated = false;
    private static JLabel labelPlayer1;
    private static JLabel labelPlayer2;
    private static JLabel labelBoatsPlayer1;
    private static JLabel labelBoatsPlayer2;
    private static JTextArea labelCountBoatsPlayer1;
    private static JTextArea labelCountBoatsPlayer2;


    // Imágenes
    private Image oceanBackground;
    private Image PortavionesV, AcorazadoV, DestructorV, SubmarinoV, LanchaV, CustomV;
    private Image PortavionesH,AcorazadoH,DestructorH,SubmarinoH,LanchaH,CustomH;
    private Image Fire;
    private Image Miss;
    private Image shipBackground;
    private Image templateShip;

    private static Battleship instancia=null;

    private Battleship() {
        setLayout(null);
        //Labels Turnos
        labelPlayer1 = new JLabel("Turno: Jugador 1", SwingConstants.CENTER);
        labelPlayer2 = new JLabel("Turno: Jugador 2", SwingConstants.CENTER);
        labelPlayer1.setForeground(new Color(255, 255, 255, 0));
        labelPlayer2.setForeground(new Color(255, 255, 255, 0));
        labelPlayer1.setFont(new Font("Arial", Font.BOLD, 16));
        labelPlayer2.setFont(new Font("Arial", Font.BOLD, 16));
        add(labelPlayer1, BorderLayout.NORTH);
        add(labelPlayer2, BorderLayout.SOUTH);

        //Labels Title Nums Barcos
        labelBoatsPlayer1 = new JLabel("Barcos del Jugador 1");
        labelBoatsPlayer2 = new JLabel("Barcos del Jugador 2");
        labelBoatsPlayer1.setForeground(new Color(255, 255, 255));
        labelBoatsPlayer2.setForeground(new Color(255, 255, 255));
        labelBoatsPlayer1.setFont(new Font("Arial", Font.BOLD, 20));
        labelBoatsPlayer2.setFont(new Font("Arial", Font.BOLD, 20));
        add(labelBoatsPlayer1);
        add(labelBoatsPlayer2);

        //Labels Num Barcos
        labelCountBoatsPlayer1 = new JTextArea("0");
        labelCountBoatsPlayer2 = new JTextArea("0");
        labelCountBoatsPlayer1.setLineWrap(true);
        labelCountBoatsPlayer1.setWrapStyleWord(true);
        labelCountBoatsPlayer1.setEditable(false);
        labelCountBoatsPlayer1.setOpaque(false);

        labelCountBoatsPlayer2.setLineWrap(true);
        labelCountBoatsPlayer2.setWrapStyleWord(true);
        labelCountBoatsPlayer2.setEditable(false);
        labelCountBoatsPlayer2.setOpaque(false);

        labelCountBoatsPlayer1.setForeground(new Color(255, 255, 255));
        labelCountBoatsPlayer2.setForeground(new Color(255, 255, 255));
        labelCountBoatsPlayer1.setFont(new Font("Arial", Font.ITALIC, 17));
        labelCountBoatsPlayer2.setFont(new Font("Arial", Font.ITALIC, 17));
        add(labelCountBoatsPlayer1);
        add(labelCountBoatsPlayer2);

        oceanBackground = new ImageIcon(getClass().getResource("img/tex_Water.jpg")).getImage();
        shipBackground = new ImageIcon(getClass().getResource("img/tex_Ship.png")).getImage();
        PortavionesV = new ImageIcon(getClass().getResource("Barcos/Portaviones/porta.png")).getImage();
        AcorazadoV = new ImageIcon(getClass().getResource("Barcos/Acorazado/AcorazadoV.png")).getImage();
        DestructorV = new ImageIcon(getClass().getResource("Barcos/Destructor/DestructorV.png")).getImage();
        SubmarinoV = new ImageIcon(getClass().getResource("Barcos/Submarino/SubmarinoV.png")).getImage();
        LanchaV = new ImageIcon(getClass().getResource("Barcos/Lancha/LanchaV.png")).getImage();
        CustomV = new ImageIcon(getClass().getResource("Barcos/Custom/CustomV.png")).getImage();
        PortavionesH = new ImageIcon(getClass().getResource("Barcos/Portaviones/PortavionesH.png")).getImage();
        AcorazadoH = new ImageIcon(getClass().getResource("Barcos/Acorazado/AcorazadoH.png")).getImage();
        DestructorH = new ImageIcon(getClass().getResource("Barcos/Destructor/DestructorH.png")).getImage();
        SubmarinoH = new ImageIcon(getClass().getResource("Barcos/Submarino/SubmarinoH.png")).getImage();
        LanchaH = new ImageIcon(getClass().getResource("Barcos/Lancha/LanchaH.png")).getImage();
        CustomH = new ImageIcon(getClass().getResource("Barcos/Custom/CustomH.png")).getImage();
        Fire = new ImageIcon(getClass().getResource("img/fireGIF.gif")).getImage();
        Miss = new ImageIcon(getClass().getResource("img/Miss.png")).getImage();
        templateShip = new ImageIcon(getClass().getResource("img/templateShip.png")).getImage();

        Timer timer = new Timer(1000 / 120, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }
    public static Battleship getInstancia(){
        if(instancia==null) return new Battleship();
        return instancia;
    }

    public void setTurno(boolean t) {
        this.turno = t;
        if (turno) {
            labelPlayer1.setForeground(new Color(255, 255, 255));
            labelPlayer2.setForeground(new Color(255, 255, 255, 0));
        } else {
            labelPlayer1.setForeground(new Color(255, 255, 255, 0));
            labelPlayer2.setForeground(new Color(255, 255, 255));
        }
    }
    public void actualizarBarcos(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                labelCountBoatsPlayer1.setText(player1.getTablero().mostrarBarcos());
                labelCountBoatsPlayer2.setText(player2.getTablero().mostrarBarcos());
            }
        });
    }
    public boolean getTurno() { return turno; }
    public void setSize(int size) { this.size = size; }

    public void setPlayer(Player player1, Computer player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        actualizarBarcos();
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        g.drawImage(oceanBackground, 0, 0, panelWidth, panelHeight, null);

        //Calcular variables para acomodar la interfaz
        cellSize = Math.min(panelWidth / (size + 2), panelHeight / (2 * size + 2));
        cellSize--;
        int centroX = (panelWidth - size * cellSize) / 2;
        int centroY = (panelHeight / 4) - (size * cellSize) / 2;
        int centroYDown = (3 * panelHeight / 4) - (size * cellSize) / 2;
        int templateShipWidth = (int)(panelWidth * 0.12);
        int templateShipHeight = (int)(panelHeight * 0.08);

        labelBoatsPlayer1.setBounds((int)(panelWidth * 0.15)-10, (int)(panelHeight * 0.1)+80, 300, 30);
        labelBoatsPlayer2.setBounds((int)(panelWidth * 0.85)-200, (int)(panelHeight * 0.1)+80, 300, 30);
        labelCountBoatsPlayer1.setBounds((int)(panelWidth * 0.15)-45, (int)(panelHeight * 0.1)+110, 300, 60);
        labelCountBoatsPlayer2.setBounds((int)(panelWidth * 0.85)-235, (int)(panelHeight * 0.1)+110, 300, 60);
        labelPlayer1.setBounds((panelWidth - 150) / 2, (panelHeight - 38) / 2, 150, 30);
        labelPlayer2.setBounds((panelWidth - 150) / 2, (panelHeight - 38) / 2, 150, 30);

        g.drawImage(templateShip, (int)(panelWidth * 0.2) - templateShipWidth / 2, (int)(panelHeight * 0.1), templateShipWidth, templateShipHeight, null);
        g.drawImage(templateShip, (int)(panelWidth * 0.8) - templateShipWidth / 2, (int)(panelHeight * 0.1), templateShipWidth, templateShipHeight, null);

        drawBoards(g, centroX, centroY, player2.getTablero(), true);
        drawBoards(g, centroX, centroYDown, player1.getTablero(), false);

        if (!buttonsCreated) {
            createButtons(centroX, centroY, player2.getTablero());
            buttonsCreated = true;
        }
    }

    public void drawBoards(Graphics g, int centroX, int centroY, Tablero tablero, boolean ocultarBarcos) {
        for (int i = 0; i < size; i++) g.drawString(String.valueOf(i), centroX + (i * cellSize) + (cellSize / 2) - 5, centroY - 5);
        for (int i = 0; i < size; i++) g.drawString(String.valueOf(i), centroX - 20, centroY + (i * cellSize) + (cellSize / 2) + 5);
        drawBgBoard(g, centroX, centroY);
        drawBoard(g, centroX, centroY, tablero, ocultarBarcos);
        drawHitsBoard(g, centroX, centroY, tablero);
    }

    public void createButtons(int centroX, int centroY, Tablero tablero) {
        buttons = new JButton[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = centroX + j * cellSize;
                int y = centroY + i * cellSize;

                JButton button = new JButton();
                button.setBounds(x, y, cellSize, cellSize);
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                int finalI = i;
                int finalJ = j;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!tablero.getCoordenadas().get((finalI*size)+finalJ).getMiss() && !tablero.getCoordenadas().get((finalI*size)+finalJ).getGolpeado()) {
                            tablero.resultAtaque(finalI, finalJ);
                            setTurno(false);
                            enablePlayerButtons(false);
                        } else  JOptionPane.showMessageDialog(null,"Ya jugó ahí, elija otra coordenadao","ERROR",JOptionPane.ERROR_MESSAGE );

                    }
                });
                add(button);
                buttons[i][j] = button;
            }
        }
    }

    public void enablePlayerButtons(boolean enable) {
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                button.setEnabled(enable);
            }
        }
    }

    public void drawBgBoard(Graphics g, int centroX, int centroY) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = centroX + j * cellSize;
                int y = centroY + i * cellSize;
                g.drawImage(shipBackground, x + 2, y + 2, cellSize - 2, cellSize - 2, null);
            }
        }
    }

    public void drawBoard(Graphics g, int centroX, int centroY, Tablero tablero, boolean ocultarBarcos) {
        Set<Integer> set = new HashSet<>();
        boolean t=ocultarBarcos;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = centroX + j * cellSize;
                int y = centroY + i * cellSize;
                Coordenada coordenada = tablero.getCoordenadas().get((i * size) + j);
                ocultarBarcos = (coordenada.getBarco() != null && coordenada.getBarco().getHundido()) ? false:t;
                if (coordenada.getBarco() != null && !(ocultarBarcos)) {
                    Barco barco = coordenada.getBarco();
                    boolean h = barco.h;
                    String tipoBarco = barco.getTipo();
                    int barcoSize;
                    Image barcoImg;
                    switch (tipoBarco) {
                        case "Portaviones":
                            barcoImg = h ? PortavionesH : PortavionesV;
                            barcoSize = 5;
                            break;
                        case "Acorazado":
                            barcoImg = h ? AcorazadoH : AcorazadoV;
                            barcoSize = 4;
                            break;
                        case "Destructor":
                            barcoImg = h ? DestructorH : DestructorV;
                            barcoSize = 3;
                            break;
                        case "Submarino":
                            barcoImg = h ? SubmarinoH : SubmarinoV;
                            barcoSize = 3;
                            break;
                        case "Lancha":
                            barcoImg = h ? LanchaH : LanchaV;
                            barcoSize = 2;
                            break;
                        case "Custom":
                            barcoImg = h ? CustomH : CustomV;
                            CustomShip c = (CustomShip) barco;
                            barcoSize = c.getPeso();
                            break;
                        default:
                            barcoImg = null;
                            barcoSize = 0;
                    }
                    if (barcoImg != null && !set.contains(barco.getID())) {
                        set.add(barco.getID());
                        int barcoWidth = h ? cellSize * barcoSize : cellSize;
                        int barcoHeight = h ? cellSize : cellSize * barcoSize;

                        g.drawImage(barcoImg, x, y, barcoWidth, barcoHeight, null);
                    }
                }
            }
        }
    }

    public void drawHitsBoard(Graphics g, int centroX, int centroY, Tablero tablero) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = centroX + j * cellSize;
                int y = centroY + i * cellSize;

                g.setColor(Color.decode("#02D2E8"));
                g.drawRect(x, y, cellSize, cellSize);

                Coordenada coordenada = tablero.getCoordenadas().get((i * size) + j);

                if (coordenada.getGolpeado()) {
                    g.drawImage(Fire, x, y, cellSize, cellSize, null);
                } else if (coordenada.getMiss()) {
                    g.drawImage(Miss, x, y, cellSize, cellSize, null);
                }
            }
        }
    }

}
