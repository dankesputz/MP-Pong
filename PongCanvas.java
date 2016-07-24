import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

/**
 * Aidan Clotworthy, 7/21/2016.
 */
public class PongCanvas extends Canvas{
    private static final int TARGET_FRAMERATE = 60;
    private static BufferStrategy strategy;
    private static PongBall ball;
    private static PongSlider p1, p2;
    private static PongCanvas canvas;
    private static int p1_wins, p2_wins;

    private PongCanvas(JPanel parent){
        super();
        parent.add(this);
        this.createBufferStrategy(2);
        strategy = this.getBufferStrategy();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MP Pong");
        JPanel p = (JPanel) frame.getContentPane();
        p.setPreferredSize(new Dimension(1280, 720));
        p.setLayout(null);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setFocusable(true);
        p.setFocusable(false);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        canvas = new PongCanvas(p);
        canvas.setBounds(0, 0, 1280, 720);

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN)
                    p2.setvY(0);
                else if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S)
                    p1.setvY(0);
            }

            @Override
            //TODO: prevent sliders from running off the window vertically
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_UP && !(p2.getyPos() == 0))
                    p2.setvY(-2);
                else
                    p2.setvY(0);

                if (keyCode == KeyEvent.VK_DOWN && !(PongSlider.getHEIGHT() == canvas.getHeight()))
                    p2.setvY(2);
                else
                    p2.setvY(0);

                if (keyCode == KeyEvent.VK_W && !(p1.getyPos() == 0))
                    p1.setvY(-2);
                else
                    p1.setvY(0);

                if (keyCode == KeyEvent.VK_S && !(p1.getyPos() + PongSlider.getHEIGHT() == canvas.getHeight()))
                    p1.setvY(2);
                else
                    p1.setvY(0);
            }
        });

        p1 = new PongSlider(0, canvas.getHeight() / 2);
        p2 = new PongSlider(canvas.getWidth() - 20, canvas.getHeight() / 2);
        ball = new PongBall(canvas.getWidth() / 2, (canvas.getHeight() / 2) + 20);

        long last = System.currentTimeMillis();

        //noinspection InfiniteLoopStatement
        while (true) {
            long change = System.currentTimeMillis() - last;
            last = System.currentTimeMillis();

            canvas.update(change);

            try{
                Thread.sleep((1000 / TARGET_FRAMERATE) - (System.currentTimeMillis() - last));
            } catch (Exception ignored) {
            }
        }
    }

    private void update(long change) {
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1280, 720);

        int roundResult = ball.updateGraphics(change, g, canvas, p1, p2);
        p1.updateGraphics(change, g);
        p2.updateGraphics(change, g);

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
