import java.awt.*;

/**
 * Aidan Clotworthy, 7/21/2016.
 */
class PongSlider {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 100;
    private static final int VMODIFIER = 12;
    private static int vY = 0;
    private final int xPos;
    private int yPos;

    void setyPos(int yPos) {
        this.yPos = yPos;
    }

    PongSlider(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    static int getVMODIFIER() {
        return VMODIFIER;
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

    void updateGraphics(Graphics2D slider) {
        slider.setColor(Color.BLACK);
        slider.fillRect(this.xPos, this.yPos, WIDTH, HEIGHT);
    }
}
