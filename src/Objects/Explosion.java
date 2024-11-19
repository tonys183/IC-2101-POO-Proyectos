package Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Explosion extends SuperObject{
    private long time;
    private int imagen;
    private int dir;
    private long ID;
    private boolean playerDamaged = false;

    public Explosion(int x, int y, int imagen, int dir, long ID) {
        this.dir = dir;
        this.ID=ID;
        this.imagen = imagen;
        this.solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width=42;
        solidArea.height=42;
        this.collision = true;
        this.name = "Explosion";
        this.x = x;
        this.y = y;
        this.time = System.currentTimeMillis();
        setDefaultValues();
    }

    public long getID() {
        return ID;
    }

    public void setDefaultValues(){
        try {
            switch (imagen) {
                case 0:
                    this.image1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpStart/exp1.png"));
                    this.image2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpStart/exp2.png"));
                    this.image3 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpStart/exp3.png"));
                    this.image4 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpStart/exp4.png"));
                    this.image5 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpStart/exp5.png"));
                    this.image6 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpStart/exp6.png"));
                    this.image7 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpStart/exp7.png"));
                    this.image8 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpStart/exp8.png"));
                    break;
                case 1:
                    this.image1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpMiddle/exp1.png"));
                    this.image2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpMiddle/exp2.png"));
                    this.image3 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpMiddle/exp3.png"));
                    this.image4 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpMiddle/exp4.png"));
                    this.image5 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpMiddle/exp5.png"));
                    this.image6 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpMiddle/exp6.png"));
                    this.image7 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpMiddle/exp7.png"));
                    this.image8 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpMiddle/exp8.png"));
                    break;
                case 2:
                    this.image1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpEnd/exp1.png"));
                    this.image2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpEnd/exp2.png"));
                    this.image3 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpEnd/exp3.png"));
                    this.image4 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpEnd/exp4.png"));
                    this.image5 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpEnd/exp5.png"));
                    this.image6 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpEnd/exp6.png"));
                    this.image7 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpEnd/exp7.png"));
                    this.image8 = ImageIO.read(getClass().getResourceAsStream("/Sprites/ExpEnd/exp8.png"));
                    break;
            }
            this.image1 = rotateImage(this.image1);
            this.image2 = rotateImage(this.image2);
            this.image3 = rotateImage(this.image3);
            this.image4 = rotateImage(this.image4);
            this.image5 = rotateImage(this.image5);
            this.image6 = rotateImage(this.image6);
            this.image7 = rotateImage(this.image7);
            this.image8 = rotateImage(this.image8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage rotateImage(BufferedImage img) {
        double rot = 0;
        switch (dir) {
            case 0:
                rot = Math.toRadians(-180);;
                break;
            case 1:
                rot = Math.toRadians(90);
                break;
            case 2:
                rot = 0;
                break;
            case 3:
                rot = Math.toRadians(-90);
                break;
            default:
                break;
        }
        AffineTransform tx = AffineTransform.getRotateInstance(rot, img.getWidth() / 2, img.getHeight() / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(img, null);
    }

    public boolean isFinished() {
        return System.currentTimeMillis() - time >= 800;
    }
    
    @Override
    protected BufferedImage getCurrentImage() {
        long elapsed = System.currentTimeMillis() - time;

        if (elapsed < 100) {
            return image1;
        } else if (elapsed < 200) {
            return image2;
        } else if (elapsed < 300) {
            return image3;
        } else if (elapsed < 400) {
            return image4;
        } else if (elapsed < 500) {
            return image5;
        } else if (elapsed < 600) {
            return image6;
        } else if (elapsed < 700) {
            return image7;
        } else {
            return image8;
        }
    }

    public boolean isPlayerDamaged() { return playerDamaged; }

    public void setPlayerDamaged(boolean damaged) { this.playerDamaged = damaged; }
}
