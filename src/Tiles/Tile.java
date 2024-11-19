package Tiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tile {
    private BufferedImage image;
    private BufferedImage[] desintegracionFrames;
    private boolean collision = false;
    private long animationStartTime;
    private int animationDuration = 2000;
    private boolean animating = false;
    private int frameCounter;
    private int currentFrame;

    public Tile() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Sprites/Tile/Brick.png"));
            desintegracionFrames = new BufferedImage[7];
            desintegracionFrames[0] = ImageIO.read(getClass().getResourceAsStream("/Sprites/BrickDestroy/brickdes1.png"));
            desintegracionFrames[1] = ImageIO.read(getClass().getResourceAsStream("/Sprites/BrickDestroy/brickdes2.png"));
            desintegracionFrames[2] = ImageIO.read(getClass().getResourceAsStream("/Sprites/BrickDestroy/brickdes3.png"));
            desintegracionFrames[3] = ImageIO.read(getClass().getResourceAsStream("/Sprites/BrickDestroy/brickdes4.png"));
            desintegracionFrames[4] = ImageIO.read(getClass().getResourceAsStream("/Sprites/BrickDestroy/brickdes5.png"));
            desintegracionFrames[5] = ImageIO.read(getClass().getResourceAsStream("/Sprites/BrickDestroy/brickdes6.png"));
            desintegracionFrames[6] = ImageIO.read(getClass().getResourceAsStream("/Sprites/BrickDestroy/brickdes7.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startDesintegrationAnimation() {
        animating = true;
        frameCounter = 0;
        currentFrame = 0;
        animationStartTime = System.currentTimeMillis();
    }

    public void update() {
        if (animating) {
            long elapsedTime = System.currentTimeMillis() - animationStartTime;
            if (elapsedTime >= animationDuration) {
                image = null;
                animating = false;
            } else {
                frameCounter++;
                if (frameCounter % 10 == 0) {
                    if (currentFrame < desintegracionFrames.length - 1) {
                        currentFrame++;
                    }
                    frameCounter = 0;
                }
                image = desintegracionFrames[currentFrame];
            }
        }
    }

    public boolean isAnimating() { return animating; }

    public void setImage (BufferedImage image) { this.image = image; }

    public BufferedImage getImage() { return image; }

    public boolean isCollision() { return collision; }

    public void setCollision (boolean collision) { this.collision = collision; }
}
