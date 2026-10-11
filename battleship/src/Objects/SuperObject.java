package Objects;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    protected BufferedImage image1, image2, image3, image4, image5, image6, image7, image8;
    protected String name;
    protected boolean collision = false;
    protected boolean alive = true;
    protected int x, y;
    protected Rectangle solidArea;
    protected int solidAreaDefX = 0, solidAreaDefY = 0;

    public void draw(Graphics2D g2, int width, int height) {
        BufferedImage currentImage = getCurrentImage();
        if (currentImage != null && solidArea != null) {
            g2.drawImage(currentImage, x, y, width, height, null);

            /* Hitbox
            g2.setColor(Color.RED);
            g2.drawRect((int)x + solidArea.x, (int)y + solidArea.y, solidArea.width, solidArea.height);
             */
        }
    }

    public Rectangle getSolidArea() { return solidArea; }

    public int getX() { return x; }

    public int getY() { return y; }

    public int getSolidAreaDefX() { return solidAreaDefX; }

    public int getSolidAreaDefY() { return solidAreaDefY; }

    public boolean isCollision() { return collision; }

    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }

    public void setAlive(boolean alive) { this.alive = alive; }

    protected BufferedImage getCurrentImage() { return image1; }
}
