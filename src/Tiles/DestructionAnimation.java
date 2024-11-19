package Tiles;

import java.awt.*;

public class DestructionAnimation {
    private int x, y;
    private Image[] frames;
    private int currentFrame;
    private int frameDelay;
    private int frameCounter;

    public DestructionAnimation(int x, int y, Image[] frames) {
        this.x = x;
        this.y = y;
        this.frames = frames;
        this.currentFrame = 0;
        this.frameDelay = 6;
        this.frameCounter = 0;
    }

    public void update() {
        frameCounter++;
        if (frameCounter >= frameDelay) {
            frameCounter = 0;
            currentFrame++;
            if (currentFrame >= frames.length) {
                currentFrame = frames.length - 1;
            }
        }
    }

    public void draw(Graphics2D g2) { g2.drawImage(frames[currentFrame], x-3, y-5, 48, 48, null); }

    public boolean isFinished() { return currentFrame == frames.length - 1; }

    public int getX() { return x; }

    public int getY() { return y; }
}