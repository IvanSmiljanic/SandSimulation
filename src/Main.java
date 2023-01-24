import javax.swing.*;
import java.awt.*;
import java.security.KeyStore;

public class Main
{
    public static void main(String[] args)
    {
        JFrame win = new JFrame(Config.TITLE);
        SandCanvas canvas = new SandCanvas();
        Simulation sim = new Simulation();

        win.setVisible(true);
        win.setSize(Config.SIZE[0], Config.SIZE[1]);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        win.add(canvas);
    }
}