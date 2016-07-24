import java.awt.*;

/**
 * Aidan Clotworthy, 7/21/2016.
 */
class PongBall {
    private static final int DIAMETER = 25;
    private int xPos, yPos;
    private long xV, yV;

    PongBall(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xV = -1;
        this.yV = 1;
    }

    private void reset(PongCanvas canvas) {
        this.xPos = canvas.getWidth() / 2;
        this.yPos = (canvas.getHeight() / 2) + 20;
        this.xV = -1;
        this.yV = 1;
    }


    int updateGraphics(long change, Graphics2D ball, PongCanvas canvas, PongSlider p1, PongSlider p2) {
        int nextTop = (int)(this.yPos + this.yV);
        int nextBottom = (int)(this.yPos + DIAMETER + this.yV);
        int nextLeft = (int)(this.xPos + this.xV);
        int nextRight = (int)(this.xPos + DIAMETER + this.xV);

        int p1_right = p1.getxPos() + PongSlider.getWIDTH();
        int p1_top = p1.getyPos();
        int p1_bottom = p1.getyPos() + PongSlider.getHEIGHT();

        int p2_left = p2.getxPos();
        int p2_top = p2.getyPos();
        int p2_bottom = p2.getyPos() + PongSlider.getHEIGHT();

        if (nextTop < 0 || nextBottom > canvas.getHeight())
            this.yV *= -1;
        if (nextLeft < p1_right) {
            if (nextTop > p1_bottom || nextBottom < p1_top) {
                reset(canvas);
                return 2;
            } else {
                this.xV *= -1;
            }
        }
        if (nextRight > p2_left) {
            if (nextTop > p2_bottom || nextBottom < p2_top) {
                reset(canvas);
                return 1;
            } else {
                this.xV *= -1;
            }
        }

        this.yPos += (int)(this.yV * 0.5 * change);
        this.xPos += (int)(this.xV * 0.5 * change);

        ball.setColor(Color.BLACK);
        ball.fillOval(this.xPos, this.yPos, DIAMETER, DIAMETER);
        return 0;
    }
}