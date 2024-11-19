package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private static Bomberman bomberman;
    public boolean up, down, left, right, bomb, exp;
    public static void setBomberman(Bomberman bomb) {
        bomberman = bomb;
    }
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (bomberman.getGamestate() == 0 || bomberman.getGamestate() == 1) {
            if (code == KeyEvent.VK_W) {
                up = true;
            } else if (code == KeyEvent.VK_S) {
                down = true;
            } else if (code == KeyEvent.VK_A) {
                left = true;
            } else if (code == KeyEvent.VK_D) {
                right = true;
            } else if (code == KeyEvent.VK_SHIFT) {
                bomb = true;
            } else if (code == KeyEvent.VK_SPACE) {
                exp = true;
            } else if (code == KeyEvent.VK_ENTER) {
                bomberman.changeGameState((bomberman.getGamestate() + 1) % 2);
                bomberman.getSound().playX(10);
                bomberman.getSound().resume(1);
                bomberman.getSound().resume(2);
            }
        } else {
            bomberman.getSound().stopX(5);
            if (code == KeyEvent.VK_A) {
                bomberman.getSound().playX(5);
                bomberman.getUi().setCommandNumber((bomberman.getUi().getCommandNumber() - 1) % 2);
            } else if (code == KeyEvent.VK_D) {
                bomberman.getSound().playX(5);
                bomberman.getUi().setCommandNumber((bomberman.getUi().getCommandNumber() + 1) % 2);
            }
            else if (code == KeyEvent.VK_ENTER) {
                bomberman.getSound().playX(6);
                if(bomberman.getUi().getCommandNumber()==0){
                    bomberman.startNewWorld();
                }
                else System.exit(0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            up = false;
        }
        else if (code == KeyEvent.VK_S) {
            down = false;
        }
        else if (code == KeyEvent.VK_A) {
            left = false;
        }
        else if (code == KeyEvent.VK_D) {
            right = false;
        }
        else if (code == KeyEvent.VK_SHIFT) {
            bomb = false;
        }
        else if (code == KeyEvent.VK_SPACE) {
            exp = false;
        }
    }
}