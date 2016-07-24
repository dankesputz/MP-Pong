import java.awt.*;

/**
 * Aidan Clotworthy, 7/21/2016.
 */
class PongSlider {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 100;
    private final int xPos;
    private int yPos;
    private int vY;

    PongSlider(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    static int getWIDTH() {
        return WIDTH;
    }

    static int getHEIGHT() {
        return HEIGHT;
    }

    int getxPos() {
        return xPos;
    }

    int getyPos() {
        return yPos;
    }

    void setvY(int vY) {
        this.vY = vY;
    }

    void updateGraphics(long change, Graphics2D slider) {
        this.yPos += this.vY * change;

        slider.setColor(Color.BLACK);
        slider.fillRect(this.xPos, this.yPos, WIDTH, HEIGHT);
    }
}
