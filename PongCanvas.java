import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

/**
 * Aidan Clotworthy, 7/21/2016.
 */
public class PongCanvas extends Canvas implements KeyListener {
    private static final int TARGET_FRAMERATE = 60;
    private static final int RESOLUTION_WIDTH = 1280;
    private static final int RESOLUTION_HEIGHT = 720;
    private static BufferStrategy strategy;
    private static PongBall ball;
    private static PongSlider p1, p2;
    private static PongCanvas canvas;
    private static int p1_wins, p2_wins;
    private static boolean upActive, downActive, wActive, sActive = false;

    private PongCanvas(JPanel parent) {
        super();
        parent.add(this);
        this.createBufferStrategy(2);
        strategy = this.getBufferStrategy();
        addKeyListener(this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MP Pong");
        JPanel p = (JPanel) frame.getContentPane();
        p.setPreferredSize(new Dimension(RESOLUTION_WIDTH, RESOLUTION_HEIGHT));
        p.setLayout(null);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setFocusable(true);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        canvas = new PongCanvas(p);
        canvas.setBounds(0, 0, RESOLUTION_WIDTH, RESOLUTION_HEIGHT);

        p1 = new PongSlider(0, canvas.getHeight() / 2);
        p2 = new PongSlider(canvas.getWidth() - 20, canvas.getHeight() / 2);
        ball = new PongBall(canvas.getWidth() / 2, (canvas.getHeight() / 2) + 20);

        long last = System.currentTimeMillis();

        while (true) {
            long change = System.currentTimeMillis() - last;
            last = System.currentTimeMillis();

            if (upActive) {
                if (p2.getyPos() - PongSlider.getVMODIFIER() > 0) {
                    p2.setyPos(p2.getyPos() - (PongSlider.getVMODIFIER()));
                }
            }
            if (downActive) {
                if (p2.getyPos() + PongSlider.getVMODIFIER() + PongSlider.getHEIGHT() < canvas.getHeight()) {
                    p2.setyPos(p2.getyPos() + (PongSlider.getVMODIFIER()));
                }
            }

            if (wActive) {
                if (p1.getyPos() - PongSlider.getVMODIFIER() > 0) {
                    p1.setyPos(p1.getyPos() - (PongSlider.getVMODIFIER()));
                }
            }
            if (sActive) {
                if (p1.getyPos() + PongSlider.getVMODIFIER() + PongSlider.getHEIGHT() < canvas.getHeight()) {
                    p1.setyPos(p1.getyPos() + (PongSlider.getVMODIFIER()));
                }
            }

            canvas.update(change);

            try {
                Thread.sleep((1000 / TARGET_FRAMERATE) - (System.currentTimeMillis() - last));
            } catch (Exception ignored) {
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP)
            upActive = false;
        if (keyCode == KeyEvent.VK_DOWN)
            downActive = false;
        if (keyCode == KeyEvent.VK_W)
            wActive = false;
        if (keyCode == KeyEvent.VK_S)
            sActive = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_UP)
            upActive = true;

        if (keyCode == KeyEvent.VK_DOWN)
            downActive = true;

        if (keyCode == KeyEvent.VK_W)
            wActive = true;

        if (keyCode == KeyEvent.VK_S)
            sActive = true;
    }

    private void update(long change) {
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, RESOLUTION_WIDTH, RESOLUTION_HEIGHT);

        int roundResult = ball.updateGraphics(change, g, canvas, p1, p2);
        p1.updateGraphics(g);
        p2.updateGraphics(g);

        g.dispose();

        strategy.show();

        if (roundResult == 1 || roundResult == 2)
            processRound(roundResult);
    }

    private void processRound(int result) {
        int scoreLimit = 10;

        JFrame gameStatus = new JFrame();
        gameStatus.setSize(300, 125);

        switch (result) {
            case 0:
                JOptionPane.showMessageDialog(gameStatus,
                        "Click OK to start the next round");
            case 1:
                p1_wins++;
                if (p1_wins == scoreLimit) {
                    JOptionPane.showMessageDialog(gameStatus,
                            "Player 1 wins!");
                    System.exit(0);
                }
            case 2:
                p2_wins++;
                if (p2_wins == scoreLimit) {
                    JOptionPane.showMessageDialog(gameStatus,
                            "Player 2 wins!");
                    System.exit(0);
                }
        }
    }
}
