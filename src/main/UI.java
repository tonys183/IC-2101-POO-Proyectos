package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {
    private Bomberman bomberman;
    private Font arial_40;
    private double playTime = 200;
    private BufferedImage imgLifeScore, imgScore, imgContinue, imgSelectorOpt, imgNo, imgYes, imgPlayerDead, imgMarcoOver, imgPause, imgAloneScore;
    private BufferedImage num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, dosPuntos;
    private int commandNumber=0;

    public UI(Bomberman bomberman) {
        this.bomberman = bomberman;
        arial_40 = new Font("Arial", Font.BOLD, 30);
        getImages();
    }

    public void getImages(){
        try{
            imgLifeScore = ImageIO.read(getClass().getResource("/Sprites/UI/life&time.png"));
            imgScore = ImageIO.read(getClass().getResource("/Sprites/UI/score.png"));
            imgContinue = ImageIO.read(getClass().getResource("/Sprites/UI/GameOver/titleContinue copia.png"));
            imgYes = ImageIO.read(getClass().getResource("/Sprites/UI/GameOver/optionYes copia.png"));
            imgNo = ImageIO.read(getClass().getResource("/Sprites/UI/GameOver/optionNo copia.png"));
            imgSelectorOpt = ImageIO.read(getClass().getResource("/Sprites/UI/GameOver/selectorOption copia.png"));
            imgPlayerDead = ImageIO.read(getClass().getResource("/Sprites/UI/GameOver/GameOverPlayer copia.png"));
            imgMarcoOver = ImageIO.read(getClass().getResource("/Sprites/UI/GameOver/marcoOver.png"));
            imgPause = ImageIO.read(getClass().getResource("/Sprites/UI/pause.png"));
            imgAloneScore = ImageIO.read(getClass().getResource("/Sprites/UI/aloneScore.png"));

            num0 = ImageIO.read(getClass().getResource("/Sprites/UI/Nums/0.png"));
            num1 = ImageIO.read(getClass().getResource("/Sprites/UI/Nums/1.png"));
            num2 = ImageIO.read(getClass().getResource("/Sprites/UI/Nums/2.png"));
            num3 = ImageIO.read(getClass().getResource("/Sprites/UI/Nums/3.png"));
            num4 = ImageIO.read(getClass().getResource("/Sprites/UI/Nums/4.png"));
            num5 = ImageIO.read(getClass().getResource("/Sprites/UI/Nums/5.png"));
            num6 = ImageIO.read(getClass().getResource("/Sprites/UI/Nums/6.png"));
            num7 = ImageIO.read(getClass().getResource("/Sprites/UI/Nums/7.png"));
            num8 = ImageIO.read(getClass().getResource("/Sprites/UI/Nums/8.png"));
            num9 = ImageIO.read(getClass().getResource("/Sprites/UI/Nums/9.png"));
            dosPuntos = ImageIO.read(getClass().getResource("/Sprites/UI/Nums/dosPuntos.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPlayTime(double playTime) { this.playTime = playTime; }

    public double getPlayTime(){ return this.playTime; }

    public void draw(Graphics2D g2) {
        int uiHeight = 200;
        int startX = (bomberman.getWidth() - (bomberman.getSizeY() * 48)) / 2;
        int startY = (bomberman.getHeight() - (bomberman.getSizeX() * 48)) / 2;
        int uiStartY = startY - uiHeight;
        int imageY = uiStartY + (uiHeight) / 2;

        if(bomberman.getGamestate()==1) drawPausedScreen(g2,startX,imageY);
        else if(bomberman.getGamestate()==2) GameOverScreen(g2);
        else {
            if(playTime!=213 ) playTime -= (double) 1 / 60;
            if(playTime<=0 && bomberman.getLevelActu()%5!=0){
                bomberman.muerteSubita();
                playTime=213 ;
            }

            int totalSeconds = (int) Math.max(0, playTime);
            long score = bomberman.getTotalScore();
            int numVida = bomberman.getPlayer().getVidas();
            String scoreString = String.valueOf(score);

            drawTemplates(g2, startX, imageY);
            drawLife(g2, numVida, startX, imageY);
            drawTime(g2, totalSeconds, startX, imageY);
            drawScore(g2, startX, imageY, imgLifeScore.getWidth() * 3, scoreString);
        }
    }

    public void drawPausedScreen(Graphics2D g2, int startX, int imageY){
        int imageWidth = imgPause.getWidth();
        int imageHeight = imgPause.getHeight();

        g2.drawImage(imgPause,startX+imgLifeScore.getWidth()*2-50, imageY, imageWidth, imageHeight, null);
        bomberman.getSound().pauseX(1);
        bomberman.getSound().pauseX(2);
    }

    public void drawTemplates(Graphics2D g2, int startX, int imageY) {
        int imageWidth = imgLifeScore.getWidth() * 3;
        int imageHeight = imgLifeScore.getHeight() * 3;

        g2.drawImage(imgLifeScore, startX-50, imageY, imageWidth, imageHeight, null);
        g2.drawImage(imgScore, startX+imageWidth+40, imageY, imageWidth, imageHeight, null);
    }

    public void drawLife(Graphics2D g2, int numVida, int startX, int imageY) {
        BufferedImage imgVidas = null;
        switch (numVida) {
            case 0: imgVidas = num0; break;
            case 1: imgVidas = num1; break;
            case 2: imgVidas = num2; break;
            case 3: imgVidas = num3; break;
            case 4: imgVidas = num4; break;
            case 5: imgVidas = num5; break;
            case 6: imgVidas = num6; break;
            case 7: imgVidas = num7; break;
            case 8: imgVidas = num8; break;
            case 9: imgVidas = num9; break;
        }
        g2.drawImage(imgVidas, startX+42, imageY+23, 18, 26, null);
    }

    public void drawTime(Graphics2D g2, int totalSeconds, int startX, int imageY) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        String formattedTime = String.format("%d:%02d", minutes, seconds);
        int timeX = startX + 250;
        int timeY = imageY + 15;

        for (char c : formattedTime.toCharArray()) {
            BufferedImage imgChar = null;
            switch (c) {
                case '0': imgChar = num0; break;
                case '1': imgChar = num1; break;
                case '2': imgChar = num2; break;
                case '3': imgChar = num3; break;
                case '4': imgChar = num4; break;
                case '5': imgChar = num5; break;
                case '6': imgChar = num6; break;
                case '7': imgChar = num7; break;
                case '8': imgChar = num8; break;
                case '9': imgChar = num9; break;
                case ':': imgChar = dosPuntos; break;
            }
            if (imgChar != null) {
                g2.drawImage(imgChar, timeX, timeY, 22, 31, null);
                timeX += 20;
            }
        }
    }

    public void drawScore(Graphics2D g2, int startX, int imageY, int imageWidth, String scoreString) {
        int scoreX = startX + imageWidth+230;
        int scoreY = imageY + 15;
        for (char c : scoreString.toCharArray()) {
            BufferedImage imgChar = null;
            switch (c) {
                case '0': imgChar = num0; break;
                case '1': imgChar = num1; break;
                case '2': imgChar = num2; break;
                case '3': imgChar = num3; break;
                case '4': imgChar = num4; break;
                case '5': imgChar = num5; break;
                case '6': imgChar = num6; break;
                case '7': imgChar = num7; break;
                case '8': imgChar = num8; break;
                case '9': imgChar = num9; break;
            }
            if (imgChar != null) {
                g2.drawImage(imgChar, scoreX, scoreY, 22, 31, null);
                scoreX += 20;
            }
        }
    }

    public void GameOverScreen(Graphics2D g2) {
        try {
            int screenWidth = bomberman.getWidth();
            int screenHeight = bomberman.getHeight();

            g2.setColor(Color.black);
            g2.fillRect(0, 0, screenWidth, screenHeight);

            int marcoWidth = 197 * 5;
            int marcoHeight = 176 * 5;
            int marcoX = (screenWidth - marcoWidth) / 2;
            int marcoY = (screenHeight - marcoHeight) / 2;
            g2.drawImage(imgMarcoOver, marcoX+20, marcoY, marcoWidth, marcoHeight, null);

            int centerX = screenWidth / 2;
            int continueWidth = 650;
            int continueHeight = 80;
            int continueX = centerX - (continueWidth / 2);
            int continueY = screenHeight / 4;
            g2.drawImage(imgContinue, continueX+20, continueY, continueWidth, continueHeight, null);

            int deadPlayerWidth = 40 * 3;
            int deadPlayerHeight = 26 * 3;
            int deadPlayerX = centerX - (deadPlayerWidth / 2);
            int deadPlayerY = continueY + continueHeight + 20;
            g2.drawImage(imgPlayerDead, deadPlayerX, deadPlayerY, deadPlayerWidth, deadPlayerHeight, null);

            int yesWidth = 34 * 3;
            int yesHeight = 10 * 3;
            int noWidth = 22 * 3;
            int noHeight = 10 * 3;
            int optionsY = deadPlayerY + deadPlayerHeight + 40;

            int spacing = 80;
            int yesX = centerX - (yesWidth + noWidth + spacing) / 2;
            g2.drawImage(imgYes, yesX, optionsY, yesWidth, yesHeight, null);

            int noX = yesX + yesWidth + spacing;
            g2.drawImage(imgNo, noX, optionsY, noWidth, noHeight, null);

            int selectorSpacing = 40;
            int selectorX = (commandNumber == 0) ? yesX - selectorSpacing : noX - selectorSpacing;
            int selectorY = optionsY + (yesHeight / 2 - (12 * 3) / 2);
            g2.drawImage(imgSelectorOpt, selectorX, selectorY, 8 * 3, 12 * 3, null);


            int scoreY = optionsY + yesHeight + 60;
            int scoreX = (screenWidth - (imgLifeScore.getWidth() * 3)) / 2;

            String scoreString = String.valueOf(bomberman.getTotalScore());

            g2.drawImage(imgAloneScore, deadPlayerX-40, scoreY, 39*3, 12*3, null);
            drawScore(g2, scoreX+30, scoreY-10, 0, scoreString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCommandNumber(){ return commandNumber; }

    public void setCommandNumber(int i){ commandNumber=i; }
}
