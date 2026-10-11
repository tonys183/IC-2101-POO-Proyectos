package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Menu extends JPanel implements ActionListener, KeyListener {
    private static JFrame frame;
    private static Bomberman game;
    private static Menu instancia = null;
    private Sound sound = new Sound();

    private Image backgroundImage, titleImage, btnNormalGame, btnQuit, selectorImage, IconPlayerMenu;
    private int selectedOption = 0;

    private Menu() {
        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();
        backgroundImage = new ImageIcon(getClass().getResource("/Sprites/UI/Menu/Background.png")).getImage();
        titleImage = new ImageIcon(getClass().getResource("/Sprites/UI/Menu/titleGame.png")).getImage();
        btnNormalGame = new ImageIcon(getClass().getResource("/Sprites/UI/Menu/btnNormal.png")).getImage();
        btnQuit = new ImageIcon(getClass().getResource("/Sprites/UI/Menu/quitButton.png")).getImage();
        selectorImage = new ImageIcon(getClass().getResource("/Sprites/UI/Menu/menuSelector.png")).getImage();
        IconPlayerMenu = new ImageIcon(getClass().getResource("/Sprites/UI/Menu/IconPlayerMenu.png")).getImage();

        frame = new JFrame("Bomberman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(258 * 3, 225 * 4);
        frame.setLocationRelativeTo(null);
        setLayout(null);

        frame.add(this);
        frame.setVisible(true);
        sound.playX(0);
        sound.loopX(0);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        sound.stopX(5);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                sound.playX(5);
                moveSelector(-1);
                break;
            case KeyEvent.VK_S:
                sound.playX(5);
                moveSelector(1);
                break;
            case KeyEvent.VK_ENTER:
                if (selectedOption == 0) {
                    sound.playX(6);
                    sound.stopX(0);
                    empezarGame();
                } else if (selectedOption == 1) {
                    sound.playX(6);
                    System.exit(0);
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        requestFocusInWindow();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        int titleX = (getWidth() - titleImage.getWidth(null)*2) / 2;
        int titleY = 100;
        g.drawImage(titleImage, titleX, titleY, 187*2, 122*2, this);

        g.drawImage(IconPlayerMenu, titleX + 150*2, titleY*2+30, 54*2, 67*2, null);


        int buttonY = titleY + titleImage.getHeight(null)*3;
        int buttonX = (getWidth() - btnNormalGame.getWidth(null) * 3) / 2;
        g.drawImage(btnNormalGame, buttonX-20, buttonY, 104 * 3, 13 * 3, this);

        int quitButtonY = buttonY + 40 + 13 * 3;
        g.drawImage(btnQuit, buttonX+70, quitButtonY, 41 * 3, 14 * 3, this);

        int selectorX = selectedOption == 0 ? buttonX-65 : buttonX+25;
        if (selectedOption == 0) {
            int selectorY = buttonY;
            g.drawImage(selectorImage, selectorX, selectorY, 16*2,16*2, this);
        } else if (selectedOption == 1) {
            int selectorY = quitButtonY;
            g.drawImage(selectorImage, selectorX, selectorY, 16*2,16*2,this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if ("Play original game".equals(action)) {
            empezarGame();
        }
        else if("Quit".equals(action)){
            System.exit(0);
        }
    }

    public void moveSelector(int direction) {
        if (direction == 1) {
            selectedOption = (selectedOption + 1) % 2;
        } else if (direction == -1) {
            selectedOption = (selectedOption - 1 + 2) % 2;
        }
        repaint();
    }

    public static void empezarGame() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.getContentPane().removeAll();
                frame.repaint();
                game = Bomberman.getInstancia();
                game.requestFocusInWindow();
                frame.getContentPane().add(game);
                frame.revalidate();
                frame.repaint();
                return null;
            }
        };
        worker.execute();
    }

    public static Menu getInstancia() {
        if (instancia == null) {
            instancia = new Menu();
        }
        return instancia;
    }
}