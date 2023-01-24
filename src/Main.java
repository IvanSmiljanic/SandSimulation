import javax.swing.*;
import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        JFrame win = new JFrame("Sand Simulation");
        SandCanvas canvas = new SandCanvas();

        win.setVisible(true);
        win.setSize(1920, 1080);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        win.add(canvas);
    }
}