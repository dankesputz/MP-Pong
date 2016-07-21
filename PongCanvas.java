import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Aidan Clotworthy, 7/21/2016.
 */
public class PongCanvas extends Canvas{
    public static final int TARGET_FRAMERATE = 60;
    private BufferStrategy strategy;

    private PongCanvas(JPanel parent){
        super();
        parent.add(this);
        this.createBufferStrategy(2);
        this.strategy = this.getBufferStrategy();
    }

    private void update(long change){
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1280, 720);
        g.dispose();

        this.strategy.show();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MP Pong");
        JPanel p = (JPanel) frame.getContentPane();
        p.setPreferredSize(new Dimension(1280, 720));
        p.setLayout(null);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        PongCanvas canvas = new PongCanvas(p);
        canvas.setBounds(0, 0, 1280, 720);

        long last = System.currentTimeMillis();

        boolean isRunning = true;
        while(isRunning){
            long change = System.currentTimeMillis() - last;
            last = System.currentTimeMillis();

            canvas.update(change);

            try{
                Thread.sleep((1000 / TARGET_FRAMERATE) - (System.currentTimeMillis() - last));
            }catch (Exception e){}

        }
    }
}
