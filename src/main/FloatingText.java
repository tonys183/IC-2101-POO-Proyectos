package main;

import java.awt.*;

public class FloatingText {
    private String text;
    private float x, y;
    private long startTime;
    private long duration;

    public FloatingText(String text, float x, float y, long duration) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.duration = duration;
        this.startTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - startTime > duration;
    }

    public void render(Graphics2D g2) {
        g2.setFont(new Font("Press Start 2P", Font.BOLD, 24));
        g2.setColor(Color.WHITE);
        g2.drawString(text, (int) x - 1, (int) (y+15) - 1);
        g2.drawString(text, (int) x + 1, (int) (y+15) - 1);
        g2.drawString(text, (int) x - 1, (int) (y+15) + 1);
        g2.drawString(text, (int) x + 1, (int) (y+15) + 1);

        g2.setColor(Color.decode("#F73908"));
        g2.drawString(text, (int) x, (int) (y+15));
    }
}