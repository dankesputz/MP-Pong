import java.awt.*;

/**
 * Created by clotw on 7/21/2016.
 */
public class PongBall {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private int xPos;
    private int yPos;

    public PongBall(int xPos, int yPos, Panel p) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void updateGraphics() {
    }

    public boolean hasCollided() {
        return false;
    }

    public void fill(){

    }



}